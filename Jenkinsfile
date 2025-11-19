pipeline {
    agent any

    tools {
        maven 'M2_HOME'
        jdk 'JDK21'
    }

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

        stage('Build Docker Image') {
                    steps {
                        script {
                            // Construire l'image Docker avec Ansible
                            sh 'ansible-playbook -i localhost, build-docker.yml'
                        }
                    }
                }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    // Pousser l'image Docker vers Docker Hub
                    sh 'ansible-playbook -i localhost, push-docker.yml'
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Déployer l'image Docker sur Kubernetes
                    sh 'ansible-playbook -i localhost, deploy-k8s.yml'
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully!"
        }
        failure {
            echo "Pipeline failed, please check the logs."
        }
    }
}
