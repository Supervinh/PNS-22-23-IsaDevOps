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
                          echo ""
                          ./build-all.sh
                          docker attach cli
                          script store.txt
                          exit
                          docker compose down
                          '''
                        }//TODO attach cli & run scripts (any automatic verifications ?) & docker compose down
                }
       stage('Deploy') {
            environment {
                SONAR_ID = credentials('Sonar')
            }
            steps {
            echo 'Should deploy on artifactory(8002:8081) and SonarQube (8001:9000)..'
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