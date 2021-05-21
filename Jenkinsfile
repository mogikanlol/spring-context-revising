pipeline {
    agent any

    stages {

        stage("build") {

            steps {
                echo 'building the application...'
            }
        }
        
        stage("test") {

            steps {
                echo 'testing the application...'
                script {
                    echo 'hello from script block'
                }
            }
        }
        
        stage("deploy") {

            steps {
                echo 'deploying the application...'
            }
        }
    }
}
