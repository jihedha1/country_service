pipeline {
    // 1. Définition de l'environnement d'exécution
    agent any // Le pipeline peut s'exécuter sur n'importe quel agent Jenkins disponible.

    // 2. Définition des outils requis
    tools {
        // 'mymaven' doit correspondre au nom que vous avez donné à votre configuration Maven dans Jenkins
        maven 'mymaven'
        // 'JDK21' doit correspondre au nom de votre configuration JDK
        jdk 'JDK21'
    }

    // 3. Définition des étapes du pipeline
    stages {
        // Étape 1 : Récupération du code source
        stage('Checkout code') {
            steps {
                // Commande pour cloner le dépôt Git
                git branch: 'main', url: 'https://github.com/jihedha1/country-service.git'
            }
        }

        // Étape 2 : Compilation, tests et packaging
        stage('Compile, test code and package' ) {
            steps {
                // Exécute les commandes Maven pour nettoyer, compiler, tester et packager
                sh 'mvn clean install'
            }
            // Actions post-exécution de l'étape
            post {
                // Si l'étape réussit, archiver les rapports de test JUnit
                success {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }

        // Étape 3 : Analyse de la qualité du code
        stage('SonarQube Analysis') {
            steps {
                // Utilise le contexte de SonarQube configuré dans Jenkins
                withSonarQubeEnv('MySonarQubeServer') { // 'MySonarQubeServer' est le nom du serveur SonarQube dans Jenkins
                    // Lance l'analyse SonarQube avec Maven
                    sh "mvn sonar:sonar -Dsonar.projectKey=country-service"
                }
            }
        }
    }
}
