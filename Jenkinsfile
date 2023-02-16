pipeline {
    agent any
    tools {
        maven 'Maven 3.8.7'
        jdk 'JDK 17'
    }

    stages {
        stage('Build') {
            steps {
                cd 'backend'
                sh 'mvn clean package'
//                 echo 'Building..'
            }
        }
//         stage('Test') {
//             steps {
//                 echo 'Testing..'
//             }
//         }
//         stage('Deploy') {
//             steps {
//                 echo 'Deploying..'
//             }
//         }
    }
 }