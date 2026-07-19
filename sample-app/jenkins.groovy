pipeline {
    agent any

    environment {
        IMAGE_NAME = "anilbamnote/sample-app"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Anilbamnote/Immverse-ai.git'
            }
        }

        stage('Build') {
            steps {
                dir('sample-app') {
                    sh 'docker build -t $IMAGE_NAME:$BUILD_NUMBER .'
                }
            }
        }

        stage('Test') {
            steps {
                sh 'docker images'
            }
        }

        stage('Push Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'USER',
                    passwordVariable: 'PASS'
                )]) {

                    sh '''
                    echo $PASS | docker login -u $USER --password-stdin
                    docker push $IMAGE_NAME:$BUILD_NUMBER
                    docker tag $IMAGE_NAME:$BUILD_NUMBER $IMAGE_NAME:latest
                    docker push $IMAGE_NAME:latest
                    '''
                }
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                docker rm -f sample-app || true

                docker run -d \
                  --name sample-app \
                  -p 80:3000 \
                  anilbamnote/sample-app:latest
                '''
            }
        }
    }
}

// pipeline {
//     agent any

//     environment {
//         IMAGE_NAME = "anilbamnote/sample-app"
//     }

//     stages {

//         stage('Checkout') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/Anilbamnote/Immverse-ai.git'
//             }
//         }

//         stage('Build') {
//             steps {
//                 sh 'docker build -t $IMAGE_NAME:$BUILD_NUMBER .'
//             }
//         }

//         stage('Test') {
//             steps {
//                 sh 'docker images'
//             }
//         }

//         stage('Push Image') {
//             steps {
//                 withCredentials([usernamePassword(
//                     credentialsId: 'dockerhub-creds',
//                     usernameVariable: 'USER',
//                     passwordVariable: 'PASS'
//                 )]) {

//                     sh '''
//                     echo $PASS | docker login -u $USER --password-stdin
//                     docker push $IMAGE_NAME:$BUILD_NUMBER
//                     docker tag $IMAGE_NAME:$BUILD_NUMBER $IMAGE_NAME:latest
//                     docker push $IMAGE_NAME:latest
//                     '''
//                 }
//             }
//         }

//         stage('Deploy') {
//             steps {
//                 sh '''
//                 docker rm -f sample-app || true

//                 docker run -d \
//                 --name sample-app \
//                 -p 80:3000 \
//                 anilbamnote/sample-app:latest
//                 '''
//             }
//         }
//     }
// }