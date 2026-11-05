def call(String repo, String branch) {
    echo "Cloning the code"

    git url: repo, branch: branch

    echo "Cloning success"
}
