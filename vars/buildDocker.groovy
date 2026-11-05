def call(String imageName) {
    echo "Building the Docker image"

    sh "docker build -t ${imageName} ."
}
