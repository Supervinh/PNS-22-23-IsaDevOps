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
        stage('Deploy') {
            environment {
                SONAR_ID = credentials('Sonar')
            }
            steps {
            echo 'Deploy on artifactory(8002:8081) and SonarQube (8001:9000)..'
                dir('backend'){
                     sh 'mvn deploy sonar:sonar -Dsonar.login=${SONAR_ID}'
                }
                dir('cli'){
                    sh 'mvn deploy sonar:sonar -Dsonar.login=${SONAR_ID}'
                }
            }
        }
    }
    post {
       always {
        sh '''
            echo "Cleaning up"
            rm ${M2_HOME}/settings.xml
            docker compose down
        '''
        }
    }
}