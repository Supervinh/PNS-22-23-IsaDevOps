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
                        ls -lah
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
                    sh 'mvn install -U -e -s /home/jenkins/.m2/settings.xml'
                }
                dir('cli'){
                     sh 'mvn install -U -e -s /home/jenkins/.m2/settings.xml'
                }
            }
        }
        stage('Deploy') {
            steps {
            echo 'Should deploy on artifactory(8002)..'
                dir('backend'){
                     sh 'mvn deploy -U -e -s /home/jenkins/.m2/settings.xml'
                }
                dir('cli'){
                    sh 'mvn deploy -U -e -s /home/jenkins/.m2/settings.xml'
                }
            }
        }
    }
 }