
pipeline {

    agent any

    environment {
        IMAGE_NAME = 'mouadhradhouani/java-sql-demo'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Unit Tests') {
            steps {
                sh 'mvn -B clean verify'
            }

            post {
                always {
                    junit(
                        testResults: 'target/surefire-reports/TEST-*.xml',
                        allowEmptyResults: true
                    )
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                    docker build -t "$IMAGE_NAME:$BUILD_NUMBER" .
                '''
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_TOKEN'
                    )
                ]) {
                    sh '''
                        set +x

                        echo "$DOCKER_TOKEN" | docker login \
                            -u "$DOCKER_USER" --password-stdin

                        docker push "$IMAGE_NAME:$BUILD_NUMBER"

                        docker logout
                    '''
                }

stage('Deploy to Docker Swarm') {
    steps {
        sh '''
            docker service update \
              --image "$IMAGE_NAME:$BUILD_NUMBER" \
              java-sql-demo
        '''
    }
}


            }
        }
    }
}
