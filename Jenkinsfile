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

        stage('Build and Push Docker Image') {
            steps {
                // Construit l'image avec un tag unique basé sur le numéro de build
                sh "docker build -t jihedhallem/my-country-service:${env.BUILD_NUMBER} ."

                // S'authentifie à Docker Hub en utilisant les credentials stockés dans Jenkins
                withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                    sh "docker login -u jihedhallem -p ${dockerhubpwd}"
                }

                // Pousse l'image vers Docker Hub
                sh "docker push jihedhallem/my-country-service:${env.BUILD_NUMBER}"
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // S'authentifie au cluster Kubernetes en utilisant le kubeconfig stocké dans Jenkins
                    kubeconfig(credentialsId: 'kubeconfig-file', serverUrl: '') {
                        // IMPORTANT : Avant d'appliquer, il faut mettre à jour le tag de l'image dans le fichier de déploiement
                        sh "sed -i 's|image: .*|image: jihedhallem/my-country-service:${env.BUILD_NUMBER}|g' deployment.yaml"

                        // Applique les configurations au cluster
                        sh 'kubectl apply -f deployment.yaml'
                        sh 'kubectl apply -f service.yaml'
                    }
                }
            }
        }

    }
}
