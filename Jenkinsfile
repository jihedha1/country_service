pipeline {
    agent any
    tools {
        maven 'mymaven'
        jdk 'JDK21'
    }
    stages {
        // L'étape 'Checkout code' a été supprimée.
        // Jenkins a déjà le code à ce point.

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
    }
}
