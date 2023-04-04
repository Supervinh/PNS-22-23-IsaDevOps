node {
    try {
        def behaviour;
        def pr_behaviour = "";
        if ("${env.BRANCH_NAME}" =~ /.*feature\/\/*/) {
            behaviour = "feature"
        } else if ("${env.BRANCH_NAME}" =~ /.*dev*/) {
            behaviour = "dev"
        } else if ("${env.BRANCH_NAME}" =~ /.*PR-*/) {
            behaviour = "PR"
            if("${env.CHANGE_BRANCH}" =~ /.*feature\/\/*/){
                pr_behaviour = "feature"
            } else if("${env.CHANGE_BRANCH}" =~ /.*dev*/){
                pr_behaviour = "dev"
            } else {
                withCredentials([string(credentialsId: 'DiscordHook', variable: 'DISCORD_ID')]) {
                    discordSend description: "${CHANGE_AUTHOR} probaly misnamed ${env.BRANCH_NAME}", footer: '', link: env.CHANGE_URL, result: FAILURE,
                    title: "Unexpected Behaviour on ${env.BRANCH_NAME}", webhookURL: "$DISCORD_ID"
                }
                throw new Exception("What are you pushing exactly ?")
            }
        } else if ("${env.BRANCH_NAME}" ==~ "main") {
            behaviour = "main"
        } else {
            withCredentials([string(credentialsId: 'DiscordHook', variable: 'DISCORD_ID')]) {
                            discordSend description: "@ladar Something went wrong with a Jenkins job", footer: '', link: env.BUILD_URL, result: FAILURE,
                            title: "Something went wrong", webhookURL: "$DISCORD_ID"
            }
            throw new Exception("What are you pushing exactly ?") //Discord Message
        }
        stage('Initialize') {
            if (behaviour == 'PR') {
                echo 'Pull request'
                checkout([$class: 'GitSCM', branches: [[name: "FETCH_HEAD"]],
                            extensions: [[$class: 'LocalBranch']],
                            userRemoteConfigs: [[refspec: "+refs/pull/${CHANGE_ID}/head:refs/remotes/origin/PR-${CHANGE_ID}",credentialsId: 'GlobalGitIds', url: "https://github.com/pns-isa-devops/isa-devops-22-23-team-b-23.git"]]])
            } else {
                echo 'Standard branch'
                checkout scmGit(branches: [[name: "${env.BRANCH_NAME}"]],
                        extensions: [], gitTool: 'Git',
                        userRemoteConfigs: [[credentialsId: 'GlobalGitIds', url: 'https://github.com/pns-isa-devops/isa-devops-22-23-team-b-23.git']])
            }
            sh '''
                mkdir -p ${HOME}/.m2
                cp settings.xml ${HOME}/.m2/
                java -version
                mvn -version
                docker -v
                docker compose version
            '''
        }
        if(behaviour == 'feature' || pr_behaviour == 'feature'){
            stage('Tests unitaires'){
                dir('backend'){
                    sh 'mvn package'
                }
                dir('cli'){
                    sh 'mvn package'
                }
            }
        }
        if(behaviour == 'dev'|| pr_behaviour == 'feature' || pr_behaviour == 'dev'){
            stage('Tests unitaires'){
                dir('backend'){
                    sh 'mvn verify -Dtest=!* -DfailIfNoTests=false '
                }
                dir('cli'){
                    sh 'mvn verify -Dtest=!* -DfailIfNoTests=false'
                }
            }
        }
        def res = -1
        if(pr_behaviour == 'dev'){
            stage('Tests End-2-End'){
                dir("endToEnd"){
                    res = sh returnStatus: true, script: './endToEnd.sh'
                }
                if(res !=0 && behaviour=="main"){
                    withCredentials([string(credentialsId: 'DiscordHook', variable: 'DISCORD_ID')]) {
                        discordSend description: "@everyone End to End tests failed", footer: 'Comment c\'est arrivé ça ?', link: env.CHANGE_URL, result: FAILURE,
                        title: "End-2-End tests failed", webhookURL: "$DISCORD_ID"
                    }
                    throw new Exception("End-2-End tests failed")
                }
                if(pr_behaviour=="dev" && res != 0){
                    throw new Exception("End-2-End tests failed")
                }
            }
        }
        if(behaviour == 'main'){
                stage('Retrieve from artifactory & build'){
                    withCredentials([string(credentialsId: 'Artifactory', variable: 'ARTIFACTORY_ID')]) {
                       def versions = sh(script:"./build-all.sh --server --cli --none -u $ARTIFACTORY_ID", returnStdout: true)
                       sh ''' echo ${versions} '''
                       versions = versions.trim().split('\n').findAll{ it.startsWith("TAG:") }
                       def cli = versions.find{it.contains("cli")}
                       if(cli != null)
                           cli = cli.substring(cli.indexOf("->")+2).trim()
                       else
                           cli = ""
                       def server = versions.find{it.contains("server")}
                       if(server != null)
                           server = server.substring(server.indexOf("->")+2).trim()
                       else
                           server=""
                    }
                }
                stage('Publish on DockerHub'){
                    withCredentials([string(credentialsId: 'Docker', variable: 'DOCKER_ID')]) {
                        sh 'echo $DOCKER_ID | docker login -u jeannestheo --password-stdin'
                    }
                    sh """
                        docker push jeannestheo/mfc-spring-cli:${cli}
                        docker push jeannestheo/mfc-spring-server:${server}
                        docker push jeannestheo/mfc-bank-service
                        docker push jeannestheo/mfc-parking-service
                        """
                }
        }
        if(behaviour != 'main'){
            stage('Deploy'){
                        echo 'Deploy on artifactory(8002:8081) and send to SonarQube (8001:9000)..'
                        withCredentials([string(credentialsId: 'Sonar', variable: 'SONAR_ID')]) {
                            dir('backend'){
                                sh 'mvn deploy sonar:sonar -Dsonar.login=${SONAR_ID} -DskipTests -DskipITs'
                            }
                            dir('cli'){
                                sh 'mvn deploy sonar:sonar -Dsonar.login=${SONAR_ID} -DskipTests -DskipITs'
                            }
                        }
                    }
        }
        if(behaviour == 'PR'){
            stage('Notify'){
                withCredentials([string(credentialsId: 'DiscordHook', variable: 'DISCORD_ID')]) {
                    discordSend description: """
                    @everyone, new pull request on ${CHANGE_BRANCH}, you can review it by clicking on the title

                    Otherwise, here is the link : ${CHANGE_URL}
                    And the Jenkins job (only with the vpn) : ${BUILD_URL} """, footer: '', link: env.CHANGE_URL, result: currentBuild.currentResult,
                            title: "New Pull Request on ${CHANGE_BRANCH}", webhookURL: "$DISCORD_ID"
                }
            }
        }
    }finally{
        stage('Cleaning up'){
            sh '''
            rm ${HOME}/.m2/settings.xml
            docker compose down --remove-orphans
            docker logout
            '''
        }
    }
}