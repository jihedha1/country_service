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
