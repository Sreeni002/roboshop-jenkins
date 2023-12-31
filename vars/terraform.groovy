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

    parameters {
      choice(name: 'env', choices: ['dev', 'prod'], description: 'Pic environment')
      choice(name: 'action', choices: ['apply', 'destroy'], description: 'Pic Action')
    }

    stages {

      stage('Terraform INIT') {
        steps {
          sh 'terraform init -backend-config=env-${env}/state.tfvars'
        }
      }
      stage('Terraform Apply') {
        steps {
          sh 'terraform ${action} -auto-approve -var-file=env-${env}/main.tfvars'
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