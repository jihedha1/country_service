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
                    # Utiliser sudo pour toutes les commandes nécessitant des privilèges
                    sudo mkdir -p /var/www/my-app
                    sudo pkill -f 'app.jar' || true
                    sudo cp target/FirstMicroService-0.0.1-SNAPSHOT.jar /var/www/my-app/app.jar

                    # Changer le propriétaire du fichier pour l'utilisateur jenkins (bonne pratique)
                    sudo chown jenkins:jenkins /var/www/my-app/app.jar

                    # Démarrer l'application
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
