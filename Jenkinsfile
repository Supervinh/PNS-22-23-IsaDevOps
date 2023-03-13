// pipeline {
//     agent any
//     stages {
//         stage ('Initialize') {
//             environment {
//                 DOCKER_ID = credentials('Docker')
//             }
//             steps {
//                 sh '''
//                     mkdir -p ${M2_HOME}/
//                     cp settings.xml ${M2_HOME}/
//                     echo ${M2_HOME}
//                     java -version
//                     mvn -version
//                     docker -v
//                     docker compose version
//                     echo ${DOCKER_ID} | docker login -u jeannestheo --password-stdin
//                     echo env.BRANCH_NAME
//                     echo ${env}
//                     echo ${environment}
//                 '''
//             }
//         }
//         stage('Tests unitaires') {
//             steps {
//                 dir('backend'){
//                     sh 'mvn package'
//                 }
//                 dir('cli'){
//                      sh 'mvn package'
//                 }
//             }
//         }
//         stage('Deploy') {
//             environment {
//                 SONAR_ID = credentials('Sonar')
//             }
//             steps {
//             echo 'Deploy on artifactory(8002:8081) and send to SonarQube (8001:9000)..'
//                 dir('backend'){
//                      sh 'mvn deploy sonar:sonar -Dsonar.login=${SONAR_ID} -Dmaven.test.skip'
//                 }
//                 dir('cli'){
//                     sh 'mvn deploy sonar:sonar -Dsonar.login=${SONAR_ID} -Dmaven.test.skip'
//                 }
//             echo 'Deploy on docker hub..'
//             sh '''
//             docker push jeannestheo/mfc-spring-server
//             docker push jeannestheo/mfc-spring-cli
//             docker push jeannestheo/mfc-bank-service
//             '''
//
//             }
//         }
//     }
//     post {
//        always {
//         sh '''
//             echo "Cleaning up"
//             rm ${M2_HOME}/settings.xml
//             docker compose down
//             docker logout
//         '''
//         }
//     }
// }
node{
    try{
        stage('Initialize'){
            git branch: "${env.BRANCH_NAME}", credentialsId: 'GlobalGitIds', url: 'https://github.com/pns-isa-devops/isa-devops-22-23-team-b-23.git'
            sh '''
                echo ${M2_HOME}
                java -version
                mvn -version
                docker -v
                docker compose version
            '''
            withCredentials([string(credentialsId: 'Docker', variable: 'DOCKER_ID')]) {
                sh 'echo $DOCKER_ID | docker login -u jeannestheo --password-stdin'
            }
        }
        stage('Tests unitaires'){
            dir('backend'){
                sh 'mvn package'
            }
            dir('cli'){
                sh 'mvn package'
            }
        }
        stage('Deploy'){
            echo 'Deploy on artifactory(8002:8081) and send to SonarQube (8001:9000)..'
            withCredentials([string(credentialsId: 'Sonar', variable: 'SONAR_ID')]) {
                dir('backend'){
                    sh 'mvn deploy sonar:sonar -Dsonar.login=${SONAR_ID} -Dmaven.test.skip'
                }
                dir('cli'){
                    sh 'mvn deploy sonar:sonar -Dsonar.login=${SONAR_ID} -Dmaven.test.skip'
                }
            }
        }
    }finally{
        stage('Cleaning up'){
            sh '''
                rm ${M2_HOME}/settings.xml
                docker compose down
                docker logout
            '''
        }

    }
}