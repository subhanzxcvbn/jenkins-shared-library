def call() {
    sshagent(['remote-deployment']) {
        sh '''
            ssh -o StrictHostKeyChecking=no ubuntu@13.220.83.125 '
                cd ~/apps/grocery-app &&
                docker compose pull &&
                docker compose up -d
            '
        '''
    }
}
