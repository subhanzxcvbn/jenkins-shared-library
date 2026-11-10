def call(String imageTag, String previousTag) {
    sshagent(['remote-deployment']) {
        withEnv([
            "IMAGE_TAG=${imageTag}",
            "PREVIOUS_TAG=${previousTag}"
        ]) {
            sh '''
                ssh -o StrictHostKeyChecking=no ubuntu@13.220.83.125 "
                    cd ~/apps/grocery-app

                    echo 'Deploying new version...'

                    IMAGE_TAG=$IMAGE_TAG docker compose pull frontend backend &&
                    IMAGE_TAG=$IMAGE_TAG docker compose up -d frontend backend

                    echo 'Waiting for application startup...'
                    sleep 15

                    if curl -fsS http://localhost:3000/invalid-endpoint-health; then
                        echo 'Health check passed. Deployment successful.'
                    else
                        echo 'Health check failed! Starting rollback...'

                        IMAGE_TAG=$PREVIOUS_TAG docker compose pull frontend backend &&
                        IMAGE_TAG=$PREVIOUS_TAG docker compose up -d frontend backend

                        echo 'Rollback completed.'
                        exit 1
                    fi
                "
            '''
        }
    }
}
