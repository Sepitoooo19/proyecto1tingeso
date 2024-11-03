pipeline{

    agent any
    tools{
        maven "maven"
    }

    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Sepitoooo19/proyecto1tingeso']])
                dir("RESPALDO-BACKEND"){
                    bat "mvn clean package"
                }

            }
        }
        stage("Test"){
            steps{
                dir("RESPALDO-BACKEND"){
                    bat "mvn test"
                }
            }
        }

        stage("Build and Push Docker Image"){
            steps{
                dir("RESPALDO-BACKEND"){
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