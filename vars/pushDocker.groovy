def call(String localImage, String remoteRepo, String credentialsId) {

    withCredentials([
        usernamePassword(
            credentialsId: credentialsId,
            usernameVariable: 'DOCKERHUB_USER',
            passwordVariable: 'DOCKERHUB_PASS'
        )
    ]) {
        withEnv([
            "LOCAL_IMAGE=${localImage}",
            "REMOTE_REPO=${remoteRepo}"
        ]) {
            sh '''
                set +x

                printf '%s' "$DOCKERHUB_PASS" | docker login \
                    --username "$DOCKERHUB_USER" \
                    --password-stdin

                docker tag "$LOCAL_IMAGE" \
                    "$DOCKERHUB_USER/$REMOTE_REPO:latest"

                docker push "$DOCKERHUB_USER/$REMOTE_REPO:latest"
            '''
        }
    }
}
