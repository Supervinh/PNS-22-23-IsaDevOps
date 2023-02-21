pipeline {
    agent any
    stages {
        stage ('Initialize') {
                steps {
                    sh '''
                        echo "PATH = ${PATH}"
                        echo "M2_HOME = ${M2_HOME}"
                        java -version
                        mvn -version
                        docker -v
                        docker compose -v
                        cp settings.xml ${M2_HOME}
                        ls -lah ${M2_HOME}
                    '''
                }
            }
        stage('Build') {
            steps {
                sh './build-all.sh'
                }
            }
        stage('Test') {
            steps {
            echo 'Should send on SonarQube (8005)..'
                dir('backend'){
                    sh 'mvn install'
                }
                dir('cli'){
                    sh 'mvn install'
                }
            }
        }
        stage('Deploy') {
            steps {
            echo 'Should deploy on artifactory(8002)..'
                dir('backend'){
                    sh 'mvn deploy'
                }
                dir('cli'){
                    sh 'mvn deploy'
                }
            }
        }
    }
 }