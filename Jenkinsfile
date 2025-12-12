pipeline {
    agent any // Le pipeline peut tourner sur n'importe quel agent disponible

    tools {
        maven 'M2_HOME' // Assure que Maven est disponible
        jdk 'JDK21'     // Assure que le JDK 21 est disponible
    }

    // Gestion des secrets via les credentials Jenkins
    environment {
        K8S_API_TOKEN = credentials('k8s-deployer-token')
    }

    stages {
        stage('1. Checkout Code') {
            steps {
                // Récupère le code depuis le dépôt Git
                git url: 'https://github.com/jihedha1/country_service.git', branch: 'main'
            }
        }

        stage('2. Build & Test with Maven' ) {
            steps {
                // Compile, teste et package l'application
                sh 'mvn clean install'
            }
            post {
                success {
                    // Archive les rapports de test
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }
        //stage('3.SonarQube Analysis') {
        //            steps {
        //               withSonarQubeEnv('MySonarQubeServer') {
        //                    sh "mvn sonar:sonar -Dsonar.projectKey=country-service"
        //                }
        //            }
       //}

        stage('4. Deploy with Ansible') {
            steps {
                sh 'ansible-playbook -i localhost, playbookCICD.yaml'
            }
        }
    }

    post {
        // Actions à exécuter à la fin du pipeline
        always {
            echo 'Pipeline finished.'
            // Nettoyer l'espace de travail
            cleanWs()
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Please check the logs.'
        }
    }
}
