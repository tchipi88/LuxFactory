>>>>>>>>>>>>>>> How to Install LuxFactory <<<<<<<<<<<<<<<<<<

Pré requis:
1. installer le JDK(version 8+)
2. configurer les variables d'environnment : 
     - JAVA_HOME =  "chemin installation du jdk" (exemple : C:\Program Files\Java\jdk1.8.0_60)
     - PATH = %JAVA_HOME%\bin 
3. installer et démarrer MYSQL
4. spécifier le user/mot_de_passe du user BD dans le fichier src\main\resources\config\application-dev.yml 
5. connexion internet

Installation: 
1. Executer la commande "mvnw" (à partir du repertoire racine de l'application) 
2. Dans le navigateur, entrer l'url : http://localhost:8080 
   - login: admin
   - pwd : admin