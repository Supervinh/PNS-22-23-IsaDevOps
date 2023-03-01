pipeline {
    agent any
    stages {
        stage ('Initialize') {
                steps {
                    sh '''
                        mkdir -p ${M2_HOME}/
                        cp settings.xml ${M2_HOME}/
                        echo ${M2_HOME}
                        java -version
                        mvn -version
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
        stage('Tests unitaires') {
            steps {
                dir('backend'){
                    sh 'mvn package'
                }
                dir('cli'){
                     sh 'mvn package'
                }
            }
        }
        stage('Sonar'){
            environment {
                SONAR_ID = credentials('Sonar')
            }
                dir('backend'){
                    sh 'mvn sonar:sonar -Dsonar.login=${SONAR_ID}'
                }
                dir('cli'){
                    sh 'mvn sonar:sonar -Dsonar.login=${SONAR_ID}'
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
        post {
                always {
                     sh '''
                        docker-compose down
                        rm ${M2_HOME}/settings.xml
                     '''
                }
    }
 }