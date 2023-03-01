pipeline {
    agent any
    stages {
        stage ('Initialize') {
                steps {
                    sh '''
                        java -version
                        mvn -version
                        echo ${M2_HOME}
                        mkdir -p ${M2_HOME}/
                        cp settings.xml ${M2_HOME}/
                        docker -v
                        docker compose version
                    '''
                }
            }
        stage('Build') {
            steps {
                sh '''
                    ./build-all.sh
                '''
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