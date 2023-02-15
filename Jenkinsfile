pipeline {
    agent any

   checkout scmGit(branches: [[name: '*/master']],
   extensions: [],
   userRemoteConfigs: [[credentialsId: 'GlobalGitIds', url: 'https://github.com/pns-isa-devops/isa-devops-22-23-team-b-23.git']])

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying..'
            }
        }
    }
}