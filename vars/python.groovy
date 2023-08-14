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

      stage {
        stage('Code Quality') {
          steps {
            sh 'sonar-scanner -Dsonar.projectKey=${component}'
          }
        }
        stage('Run Unit Test cases') {
          steps {
            sh 'echo Run Unit Test cases'
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
}