pipeline {
    agent any

    tools {
        maven 'M2_HOME'
        jdk 'JDK21'
    }

    // Le bloc 'stages' commence ici et contient TOUTES les étapes
    stages {

        stage('Compile, test code and package') {
            steps {
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

        stage('Publish to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
                    configFileProvider([configFile(fileId: 'nexus-settings', variable: 'MAVEN_SETTINGS')]) {
                        sh 'mvn deploy -s $MAVEN_SETTINGS'
                    }
                }
            }
        }

        // L'étape 'Deploy Application' est maintenant À L'INTÉRIEUR du bloc 'stages'
        stage('Deploy Application') {
            steps {
                echo 'Deploying the application...'
                sh '''
                    # 1. Créer le répertoire de déploiement s'il n'existe pas
                    mkdir -p /var/www/my-app

                    # 2. Tuer l'ancien processus de l'application s'il existe
                    pkill -f 'app.jar' || true

                    # 3. Copier le nouvel artefact vers le répertoire de déploiement
                    cp target/FirstMicroService-0.0.1-SNAPSHOT.jar /var/www/my-app/app.jar

                    # 4. Démarrer la nouvelle version de l'application en arrière-plan
                    nohup java -jar /var/www/my-app/app.jar > /var/www/my-app/app.log 2>&1 &
                '''
            }
        }

    } // L'accolade fermante du bloc 'stages' est ici

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
