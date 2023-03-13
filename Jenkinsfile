node{
    try{
        stage('Initialize'){
//         if(env.BRANCH_NAME == 'master'){
//             echo 'Master branch'
            checkout([$class: 'GitSCM', branches: [[name: "*${env.BRANCH_NAME}"]],
                      extensions: [[$class: 'LocalBranch']],
                      userRemoteConfigs: [[credentialsId: 'GlobalGitIds', url: 'https://github.com/pns-isa-devops/isa-devops-22-23-team-b-23.git']]])


//          echo "${CHANGE_AUTHOR}"
//          checkout([$class: 'GitSCM', branches: [[name: "FETCH_HEAD"]],
//                   extensions: [[$class: 'LocalBranch']],
//                   userRemoteConfigs: [[refspec: "+refs/pull/${CHANGE_ID}/head:refs/remotes/origin/PR-${CHANGE_ID}",credentialsId: 'GlobalGitIds', url: "https://github.com/pns-isa-devops/isa-devops-22-23-team-b-23.git"]]])

//             git branch: "${env.BRANCH_NAME}", credentialsId: 'GlobalGitIds', url: 'https://github.com/pns-isa-devops/isa-devops-22-23-team-b-23.git'
            sh '''
                mkdir -p ${M2_HOME}/
                cp settings.xml ${M2_HOME}/
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