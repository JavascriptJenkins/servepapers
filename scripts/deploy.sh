
## login with ssh

cd deployments


## first need to clean the foklders
rm -rf "<nameofjar.jar>"
rm -rf data
rm -rf uploads
mkdir data
mkdir uploads

## run the application with sudo so it will have access to read an write files
## sudo adduser username
## su -c "Your command right here" -s /bin/sh username
## su -c "java -jar collegeapp-0.0.1-SNAPSHOT.jar" -s /bin/sh root
## su -c "nohup java -jar collegeapp-0.0.1-SNAPSHOT.jar &" -s /bin/sh ubuntu
## su -c "java -jar collegeapp-0.0.1-SNAPSHOT.jar" -s /bin/sh javauser ----> this one needs to have java access for user
## sudo usermod -aG sudo javauser

## I added javauser with password java

scp /Users/humblemouse/IdeaProjects/servepapers/target/collegeapp-0.0.1-SNAPSHOT.jar root@147.182.220.205:deployments/

su -c "nohup java -jar collegeapp-0.0.1-SNAPSHOT.jar &" -s /bin/sh root

sudo service nginx start

## check for java processes
ps aux | grep java



nginx

### here starts it
su -c "nohup java -jar collegeapp-0.0.1-SNAPSHOT.jar &" -s /bin/sh root
su -c "nohup serve -s build &" -s /bin/sh root

sudo service nginx start
sudo service nginx restart
service nginx status

#/var/www/html – Website content as seen by visitors.
#/etc/nginx – Location of the main Nginx application files.
#/etc/nginx/nginx. ...
#/etc/nginx/sites-available – List of all websites configured through Nginx.
#/etc/nginx/sites-enabled – List of websites actively being served by Nginx.


scripts
chmod +x redeploy.sh


sudo apt update
sudo apt install nodejs npm

sudo systemctl stop nginx


server {

        root /var/www/html;
        # Add index.php to the list if you are using PHP
        index index.html index.htm index.nginx-debian.html;
         server_name services.techvvs.io; # managed by Certbot

        location / {
                proxy_pass http://localhost:3000/;
                # First attempt to serve request as file, then
                # as directory, then fall back to displaying a 404.
                #try_files $uri $uri/ =404;
        }

}


