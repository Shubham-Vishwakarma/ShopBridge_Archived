# ShopBridge 
ShopBridge Java Spring Boot Project as part of ThinkRidge Challenge

## Postman Output
* Base URL: "/api/inventory"
* All Input formant are JSON
* All Output format is JSON
* Limitation: Only below URL works, using other URL can return wrong result or custom error message

### 1. Add new item to inventory
* Request URL: POST | "/api/invemtory/item/add"

![Screenshot1](shopridge1.png)

### 2. Display all items from inventory
* Request URL: GET | "/api/inventory/items"

![Screenshot2](shopbridge2.png)

### 3. Get a single item using item id
* Request URL: GET | "/api/inventory/item/<valid_id>"

![Screenshot3](shopbridge3.png)

### 4. Custom error response for non-existing item id
* Request URL: GET | "/api/inventory/item/<invalid_id>"

![Screenshot4](shopbridge4.png)


### 5. Update an existing item
* Request URL: PUT | "/api/inventory/item/update"

![Screenshot5](shopbridge5.png)

* Request URL: PUT | "/api/inventory/item/update/5"

![Screenshot7](shopbridge7.png)

### 6. Custom error message for passing invalid details in update
* Request URL: PUT | "/api/inventory/item/update"

![Screenshot6](shopbridge6.png)

### 7. Delete an existing item
* Request URL: "/api/inventory/item/delete/<id>"

![Screenshot8](shopbridge8.png)

### 8. Search for item using name
* Request URL: GET | "/api/inventory/query?name=\<name>"

![Screenshot10](shopbridge10.png)

### 9. Search for item using category
* Request URL: GET | "/api/inventory/query?category=\<category>"

![Screenshot9](shopbridge9.png)

### 10. Search for item using category and maxPrice
* Request URL : GET | "/api/inventory/query?category=\<category>&maxPrice=\<price>"

![Screenshot11](shopbridge11.png)

### 11. Database Output
![Screenshot13](shopbridge13.png)
![Screenshot14](shopbridge14.png)