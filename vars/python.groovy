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
          sh 'ls -l'
          sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.90.86:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true -Dsonar.java.binaries=./target'
        }
      }
        stage('Run Unit Test cases') {
          steps {
            sh 'echo Run Unit Test cases'
            //sh 'python3.6 -m unittest'
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
