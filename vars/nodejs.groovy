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
      stags {
        stage('Code Quality') {
          steps {
            sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.90.86:9000 -Dsonar.login=admin -Dsonar.password=admin123'
          }
        }
        stage('Run Unit Test cases') {
          steps {
            sh 'Run Unit Test cases'
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
      }
      post {
        always {
          cleanWs()
        }
      }
    }
  }