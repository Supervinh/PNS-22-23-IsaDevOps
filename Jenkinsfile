pipeline {
    agent any
    tools {
        maven 'maven-3.9.0'
    }

    stages {
        stage('Build') {
            steps {
                sh 'cd backend/'
                sh 'ls'
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