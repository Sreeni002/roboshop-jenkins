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

      stage('Code Compile') {
        steps {
          sh 'echo Code Compile'
        }
      }
      stage {
        stage('Code Quality') {
          steps {
            sh 'echo Code Quality'
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
}