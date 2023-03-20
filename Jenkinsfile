node{
    def repourl = "${REGISTRY_URL}/${PROJECT_ID}/${ARTIFACT_REGISTRY}"
    def mvnHome = tool name: 'maven', type: 'maven'
    def mvnCMD = "${mvnHome}/bin/mvn"
    // def cloud = "/var/jenkins_home/google-cloud-sdk/bin"
    // /home/prerit45678/usr/lib/google-cloud-sdk
    // /home/prerit45678/snap/google-cloud-cli/111/bin/gcloud

    stage('Checkout'){
        checkout([$class: 'GitSCM', 
        branches: [[name: '*/main']],
        extensions: [],
        userRemoteConfigs: [[credentialsId: 'git',
        url: 'https://github.com/PreritMunjal/ProductService.git']]])
    }
    stage('Build and Push Image'){
        //withEnv(['GCLOUD_PATH=/snap/bin/gcloud']){
            withCredentials([file(credentialsId: 'gcp', variable: 'GC_KEY')]){
                sh 'gcloud auth activate-service-account --key-file=${GC_KEY}'
                sh 'gcloud auth configure-docker us-central1-docker.pkg.dev'
                sh "${mvnCMD} clean install jib:build -DREPO_URL=${REGISTRY_URL}/${PROJECT_ID}/${ARTIFACT_REGISTRY}"
            }
        //}
    }
    stage('Deploy on GKE'){
        sh "sed -i 's|IMAGE_URL|${repourl}|g' k8s/deployment.yaml"
        step([
                $class: 'KubernetesEngineBuilder',
                projectId: env.PROJECT_ID,
                clusterName: env.CLUSTER,
                location: env.ZONE,
                manifestPattern: 'k8s/deployment.yaml',
                credentialsId: env.PROJECT_ID,
                verifyDeployments: true])
    }
}