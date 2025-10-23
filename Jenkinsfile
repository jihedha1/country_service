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
                        // Construit l'image Docker. Le "." indique que le Dockerfile est dans le répertoire courant.
                        // Le tag de l'image est le numéro du build Jenkins (ex: my-country-service:1, my-country-service:2, etc.)
                        sh "docker build -t jihedhallem/my-country-service:${env.BUILD_NUMBER} ."

                        // Se connecte à Docker Hub en utilisant les credentials que vous venez de configurer
                        withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                            sh "docker login -u jihedhallem -p ${dockerhubpwd}"
                        }

                        // Pousse l'image vers votre dépôt Docker Hub
                        sh "docker push jihedhallem/my-country-service:${env.BUILD_NUMBER}"
                    }
                }

                stage('Deploy Microservice') {
                    steps {
                        // Cette approche est simple mais brutale : elle arrête et supprime le conteneur précédent s'il existe.
                        // L'option -f force la suppression même si le conteneur est en cours d'exécution.
                        // '|| true' est une astuce pour que la commande n'échoue pas si aucun conteneur n'existe.
                        sh 'docker stop my-app || true && docker rm my-app || true'

                        // Lance un nouveau conteneur avec la nouvelle image
                        // -d : mode détaché (le conteneur tourne en arrière-plan)
                        // -p 8087:8087 : mappe le port 8087 de l'hôte au port 8087 du conteneur
                        // --name my-app : donne un nom fixe au conteneur pour le retrouver facilement
                        sh "docker run -d -p 8087:8087 --name my-app jihedhallem/my-country-service:${env.BUILD_NUMBER}"
                    }
                }
        }
    }
}
