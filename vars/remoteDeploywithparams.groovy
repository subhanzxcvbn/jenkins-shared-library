
def call(String imageTag) {
    sshagent(['remote-deployment']) {
        withEnv(["IMAGE_TAG=${imageTag}"]) {
            sh '''
                ssh -o StrictHostKeyChecking=no ubuntu@13.220.83.125 \
                    "cd ~/apps/grocery-app && \
                    IMAGE_TAG=$IMAGE_TAG docker compose pull frontend backend && \
                    IMAGE_TAG=$IMAGE_TAG docker compose up -d frontend backend"
            '''
        }
    }
}
