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
                        sh "docker build -t jihedhallem/my-country-service:${env.BUILD_NUMBER} ."
                        withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                            sh "docker login -u jihedhallem -p ${dockerhubpwd}"
                        }

                        sh "docker push jihedhallem/my-country-service:${env.BUILD_NUMBER}"
                    }
                }
        }
        stage('Deploy to Kubernetes') {
            steps {
                script {

                    kubeconfig(credentialsId: 'kubeconfig-file', serverUrl: '') {
                        sh 'kubectl apply -f deployment.yaml'
                        sh 'kubectl apply -f service.yaml'
                    }
                }
            }
        }
    }

