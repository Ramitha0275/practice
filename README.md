 mvn clean package    
 docker build -t ramithas/practice:v1 .   
 docker push ramithas/practice:v1    
 kubectl apply -f deployment.yaml  
 kubectl get svc practice-svc-v1 
 kubectl logs -l app=practice-v1     
  git init   
   git add .   
    git commit -m "Final V1 with Dockerfile and Deployment.yaml"   
    git remote add origin https://github.com/Ramitha0275/practice.git
    git branch -M main
    git push -u origin main
    kubectl cluster-info   
    
pipeline {
    agent any
    
    environment {
        DOCKER_USER = "ramithas"
        APP_NAME = "practice" 
    }
    
    stages {
        stage('Clone') {
            steps {
                git branch: 'main', 
                    credentialsId: 'github-token-creds',
                    url: 'https://github.com/Ramitha0275/practice.git'
            }
        }
        
        stage('Maven Build') {
            steps {
                // RUN DIRECTLY - Do not use -f or dir()
                bat 'mvn clean package'
            }
        }
        
       stage('Docker Build & Push') {
    steps {
        // We add the --platform flag here to fix the "no platform" error
        bat "docker build --no-cache --platform linux/amd64 -t ${DOCKER_USER}/${APP_NAME}:v1 ."
        
        withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', 
                         usernameVariable: 'USER', 
                         passwordVariable: 'PASS')]) {
            bat "docker login -u %USER% -p \"%PASS%\""
            bat "docker push ${DOCKER_USER}/${APP_NAME}:v1"
        }
    }
}
        
        stage('K8s Deploy') {
            steps {
                // Ensure deployment.yaml is also in the root
                bat 'kubectl --kubeconfig="C:/Users/ramit/.kube/config" apply -f deployment.yaml --validate=false'
            }
        }
    }
    
    post {
        success { echo "Done! Accessible at http://localhost:30007" }
        failure { echo "Check if pom.xml was pushed to the root of GitHub." }
    }
}

kubectl rollout restart deployment practice-v1
 kubectl get pods
 kubectl logs -l app=practice-v1
 kubectl port-forward deployment/practice-v1 8080:8080
 
