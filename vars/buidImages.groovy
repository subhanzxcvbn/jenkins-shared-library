def call(String imageName, String path) {

    echo "Building ${imageName}"

    dir(path) {
        sh "docker build -t ${imageName} ."
    }
}
