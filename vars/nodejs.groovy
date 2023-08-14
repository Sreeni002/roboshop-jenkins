def call() {
  pipeline {

    agent {
      node {
        label 'workstation'
      }
    }
    options {
      ansiColor('xterm')
    }
      stages {
        stage('Code Quality') {
          steps {
            //sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.90.86:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true'
          }
        }
        stage('Run Unit Test cases') {
          steps {
            sh 'echo Run Unit Test cases'
            //sh 'npm test'
          }
        }
        stage('CheckMarx SAST Scan') {
          steps {
            sh 'echo CheckMarx SAST Scan'
          }
        }
        stage('CheckMarx SCA Scan') {
          steps {
            sh 'echo CheckMarx SCA Scan'
          }
        }
        stage('Release Application') {
          when {
            expression {
              env.TAG_NAME ==~ ".*"
            }
          }
          steps {
            sh 'env'
            sh 'curl -v -u admin:admin123 --upload-file server.js http://172.31.81.83:8081/repository/cart/server.js'
          }
        }
      }
      post {
        always {
          cleanWs()
        }
      }
    }
  }