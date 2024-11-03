pipeline{

    agent any
    tools{
        maven "maven"
    }

    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Sepitoooo19/proyecto1tingeso']])
                dir("proyecto1tingeso"){
                    bat "mvn clean package"
                }

            }
        }
        stage("Test"){
            steps{
                dir("proyecto1tingeso"){
                    bat "mvn test"
                }
            }
        }

        stage("Buld and Push Docker Image"){
            steps{
                dir("proyecto1tingeso"){
                    script{
                        withDockerRegistry(credentialsId: 'docker-credentials'){
                            bat "docker build -t benjasepulvedaflores/prestabancobackend ."
                            bat "docker push benjasepulvedaflores/prestabancobackend"
                        }
                    }
                }
            }
        }
    }
}