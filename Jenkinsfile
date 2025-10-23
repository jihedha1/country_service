pipeline {
    agent any

    tools {
        maven 'M2_HOME'
        jdk 'JDK21'
    }

    stages {
        stage('Compile, test code and package') {
            steps {
                // Assurez-vous que votre pom.xml est configuré pour produire un .jar ou .war
                sh 'mvn clean install'
            }
            post {
                success {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('MySonarQubeServer') {
                    sh "mvn sonar:sonar -Dsonar.projectKey=country-service"
                }
            }
        }

        // NOUVELLE ÉTAPE AJOUTÉE
        stage('Publish to Nexus') {
            steps {
                // Injecte les credentials Nexus (ID: 'nexus-credentials') dans des variables
                withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {

                    // Met à disposition le fichier de configuration (ID: 'nexus-settings')
                    configFileProvider([configFile(fileId: 'nexus-settings', variable: 'MAVEN_SETTINGS')]) {

                        // Déploie l'artefact en utilisant le fichier settings.xml personnalisé
                        sh 'mvn deploy -s $MAVEN_SETTINGS'
                    }
                }
            }
        }
    }
    stage('Deploy Application') {
                steps {
                    echo 'Deploying the application...'
                    sh '''
                        # 1. Tuer l'ancien processus de l'application s'il existe
                        # Le '|| true' évite une erreur si le processus n'est pas trouvé
                        pkill -f 'FirstMicroService-0.0.1-SNAPSHOT.jar' || true

                        # 2. Copier le nouvel artefact vers le répertoire de déploiement
                        cp target/FirstMicroService-0.0.1-SNAPSHOT.jar /var/www/my-app/app.jar

                        # 3. Démarrer la nouvelle version de l'application en arrière-plan
                        # 'nohup' permet à l'application de continuer à tourner même si le pipeline se termine
                        # '&' lance le processus en arrière-plan
                        # '>/dev/null 2>&1' redirige les logs pour ne pas bloquer le pipeline
                        nohup java -jar /var/www/my-app/app.jar > /dev/null 2>&1 &
                    '''
                }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
