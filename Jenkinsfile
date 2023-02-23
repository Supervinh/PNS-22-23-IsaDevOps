pipeline {
    agent any
    tools {
        maven 'maven-3.6.3'
      }
    stages {
        stage ('Initialize') {
                steps {
                    sh '''
                        echo "PATH = ${PATH}"
                        echo "M2_HOME = ${M2_HOME}"
                        java -version
                        mvn -version
                        mkdir -p ${M2_HOME}/
                        cp settings.xml ${M2_HOME}/
                        ls -lah ${M2_HOME}
                    '''
                }
            }
//             docker -v
//             docker compose version

//         stage('Build') {
//             steps {
//                 sh './build-all.sh'
//                 }
//             }
        stage('Test') {
            steps {
            echo 'Should send on SonarQube (8005)..'
                dir('backend'){
                    sh 'mvn install -U -e'
                }
                dir('cli'){
                    sh 'mvn install -U -e'
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