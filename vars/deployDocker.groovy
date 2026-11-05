def call() {
    echo "Deploying the application"

    sh "docker compose up -d --build"
}
