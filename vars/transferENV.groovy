
def call(String credentialId, String remoteUser, String remoteIP, String remotePath) {

    echo "Transferring ENV file to remote server"

    withCredentials([
        file(
            credentialsId: credentialId,
            variable: 'ENV_FILE'
        ),
        sshUserPrivateKey(
            credentialsId: 'ubuntu-key',
            keyFileVariable: 'SSH_KEY'
        )
    ]) {

        sh """
            scp -i "\$SSH_KEY" \
                -o StrictHostKeyChecking=no \
                "\$ENV_FILE" \
                ${remoteUser}@${remoteIP}:${remotePath}/.env
        """

        sh """
            ssh -i "\$SSH_KEY" \
                -o StrictHostKeyChecking=no \
                ${remoteUser}@${remoteIP} \
                'chmod 600 ${remotePath}/.env'
        """
    }
}
