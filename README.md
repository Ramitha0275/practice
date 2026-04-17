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
    
