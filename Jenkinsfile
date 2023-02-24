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
                        mv settings.xml /home/jenkins/
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
//             echo 'Should send on SonarQube (8005)..'
                dir('backend'){
                    sh 'mvn package -U -s /home/jenkins/settings.xml'
                }
                dir('cli'){
                     sh 'mvn package -U -s /home/jenkins/settings.xml'
                }
            }
        }
        stage('Deploy') {
            steps {
            echo 'Should deploy on artifactory(8002)..'
                dir('backend'){
                     sh 'mvn deploy -U -e -s /home/jenkins/settings.xml'
                }
                dir('cli'){
                    sh 'mvn deploy -U -e -s /home/jenkins/settings.xml'
                }
            }
        }
    }
 }