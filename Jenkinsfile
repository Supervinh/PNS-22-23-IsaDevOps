pipeline {
    agent any
    stages {
        stage ('Initialize') {
                steps {
                    sh '''
                        pwd
                        echo "PATH = ${PATH}"
                        echo "M2_HOME = ${M2_HOME}"
                        java -version
                        mvn -version
                        cp settings.xml /home/jenkins/
                    '''
                }
            }
//             docker -v
//             docker compose version

        stage('Build') {
            steps {
                sh 'sudo ./build-all.sh'
                }
            }
        stage('Test') {
            steps {
            echo 'Should send on SonarQube (8005)..'
                dir('backend'){
                    sh 'mvn package'
                }
                dir('cli'){
                     sh 'mvn package'
                }
            }
        }
//         For some reasons, artifactory isn't found by jenkins despite being found by the docker-compose
//         stage('Deploy') {
//             steps {
//             echo 'Should deploy on artifactory(8002)..'
//                 dir('backend'){
//                      sh 'mvn deploy -U -e -s ../settings.xml'
//                 }
//                 dir('cli'){
//                     sh 'mvn deploy -U -e -s ../settings.xml'
//                 }
//             }
//         }
    }
 }