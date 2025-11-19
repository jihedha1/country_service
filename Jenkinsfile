pipeline {
    agent any
    tools {
        maven 'M2_HOME'
        jdk 'JDK21'
    }

    stages {
        //stage('Compile, test code and package') {
        //    steps {
        //        sh 'mvn clean install'
            }
        //    post {
        //        success {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
        //        }
         //   }
        //}

        stage('Deploy using ansible playbook') {
                    steps {
                        script {
                            sh 'ansible-playbook -i localhost, playbookCICD.yaml'
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