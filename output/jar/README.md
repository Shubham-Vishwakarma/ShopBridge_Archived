# ShopBridge
ShopBridge Java Spring Boot Project as part of ThinkRidge Challenge

### Database Setup
```
### Ensure you have MySQL installed

mysql> create database shopbridge; -- Creates the new database
mysql> create user 'shopbridgeadmin'@'localhost' identified by 'Sh0pBridgeP@ssword'; -- Creates the user
mysql> grant all on shopbridge.* to 'shopbridgeadmin'@'localhost'; -- Gives all privileges to the new user on the newly created database
```

### Run Spring Boot Application
```
### Download JAR file shopbridge-1.0.0.jar

### Run from jar file
$ java -jar target/shopbridge-1.0.0.jar

### Open http://localhost:8080/api/inventory/items
```