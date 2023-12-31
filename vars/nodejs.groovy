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

    environment {
      NEXUS = credentials('NEXUS')
    }
      stages {
        stage('Code Quality') {
          steps {
            sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.90.86:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true'
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
            sh 'npm install'
            sh 'echo $TAG_NAME >VERSION'
            sh 'zip -r ${component}-${TAG_NAME}.zip node_modules server.js VERSION ${schema_dir}'
            sh 'curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${component}-${TAG_NAME}.zip http://172.31.81.83:8081/repository/${component}/${component}-${TAG_NAME}.zip'
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