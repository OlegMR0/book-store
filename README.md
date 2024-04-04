# Book Store📚 - Your Ultimate Digital Library

RESTful application that allows users or admins to authenticate, interact with books and make orders. 

Service has different activities depending on your role. 
Starting from registration where your encrypted credentials will be securely saved in MySQL database.
The authentication process ensures convenience and safety providing JWT token upon successful login. 
This token serves in an arranged period, that can be initialized manually, as your key to future requests, authorizing your interactions.

Looking further users can explore available books, filtering them by specific parameters, including categories you wish and eventually have an opportunity to collect all liked books to the shopping cart, where customer can see or edit all items. Once you're ready, you can place the order and then check the existing ones with detailed info.

While for the admins offers complete control, so this role enables you to manage books and categories, with the ability to create, edit or delete them in a convenient way. The same applies to orders, which you can accompany during the entire process till completely delivered.
> [!NOTE]
> The admin also has all features available to users.

## 🔮Tech Stack

- Java 17
- Maven
- Spring: Boot, Security, Data JPA
- Docker
- MySQL, Hibernate, Liquibase
- JUnit, Mockito, TestContainers
- Swagger
  
## 🛠Installation

The application is dockerized, so the easiest way to run the project is using docker-compose file.

 - Ensure you have installed and started Docker Engine.
 - Download ZIP or clone the project using git.
 - Open the terminal and navigate to root of the unpacked project folder.
 - Create and specify **.env** file with variables you need. Use **.env.template**.
 - Run the following command in the terminal
   ```
   docker-compose up
   ```

> [!TIP]
> Using Swagger, you need to provide a JWT token, given as a response after successful login, by clicking on the green button ***Authorize***.
![image](https://github.com/OlegMR0/book-store/assets/104794816/0c584ed5-0608-4876-9669-dc27101710fc)


For testing purposes, there is a predefined admin, to log in you need to specify an accurate JWT Key in the .env file.
   ```
   QJQlI8dWpwcRExmLOXjVMOh4eqxTsw2LPhlKeEkh3RI=
   ```
Hence, you can log in within the user below.

>admin@mail.com
>
>securePassword123

## 📋API Reference
> [!NOTE]
> Next endpoints don't require authentication.

- ### Authentication Controller
    - #### Register a new user


      ```http
      POST /auth/register
      ```
      <details><summary>JSON Request sample</summary>
        
         ```json
          
      {
        "email": "customer@mail.com",
        "password": "secret",
        "repeatPassword": "secret",
        "firstName": "Important",
        "lastName": "Customer",
        "shippingAddress": "Springfield"
      }
        ```
      
      </details>

    - #### Authenticate user and generate JWT token
    
      ```http
      POST /auth/login
      ```
      
      <details><summary>JSON Request sample</summary>
        
        ```json
      {
        "email": "admin@mail.com",
        "password": "securePassword123"
      }
        ```
      </details>
&emsp;
___
> [!IMPORTANT]
> Next endpoints require authentication!

- ### Book Controller
  
   - #### Returns all available(not deleted) books
  
      ```http
      GET /books
      ```
      _This endpoint supports paging and sorting._ 
      <details><summary>HTTP Request sample</summary>       
      
      ```http
      GET /books?page=0&size=5&sort=price,desc
      ``` 
      </details>



-
   - #### Returns a book as per the identifier
    
      ```http
      GET /books/{id}
      ```
    | Parameter | Description                       |
    | :-------- | :-------------------------------- |
    | `id`      | **Required**. Id of the book to be searched |
    
    
    
   - #### Find books by specific parameters
      
      ```http
      GET /books/search
      ```
  <details><summary>HTTP Request sample</summary>
    
    ```HTTP
    GET /books/search?title=raven&category=1&author=vasyl
    ```
    </details>
    
    <details><summary>Parameters to specify</summary>

    | Parameter | Description                       |
    | :-------- | :-------------------------------- |
    | `title`   | Title of the book to be searched. The flexible parameter, returns all similar options.|
    | `author`   | Author of the book to be searched.|
    | `isbn`   | International Standard Book Number of the book to be searched.|
    | `category`   | Category id of the book to be searched.|
    | `priceFrom`   | Minimal price of the book to be searched.|
    | `priceTo`   | Minimal price of the book to be searched.|
  
   </details>
    
> [!IMPORTANT]
> The next endpoints in this chapter can only be accessed with **admin** role!

-
  - #### Create a new book with the provided body
    
      ```http
      POST /books
      ```
      <details><summary>JSON Request sample</summary>
        
      ```json
        {
          "title": "Black Raven",
          "author": "Vasyl Shkliar",
          "price": 150,
          "isbn": "9781429964371",
          "description": "string",
          "categoryIds": [
          ],
          "coverImage": "string"
        }
      ```
      </details>

   - #### Update an existing book with the provided id and body
    
       ```http
       PUT /books/{id}
       ```
        | Parameter | Description                       |
        | :-------- | :-------------------------------- |
        | `id`      | **Required**. Id of the book to be updated |
      
      <details><summary>JSON Request sample</summary>
        
      ```json
      {
        "title": "Black Raven",
        "author": "Vasyl Shkliar",
        "price": 150,
        "isbn": "9781429964371",
        "description": "string",
        "categoryIds": [
          
        ],
        "coverImage": "string"
      }
      ```
      </details>
      
   - #### Delete an existing book with the provided id.
    
        ```http
        DELETE /books/{id}
        ```
      | Parameter | Description                       |
      | :-------- | :-------------------------------- |
      | `id`      | **Required**. Id of the book to be deleted |


- ### Category Controller
  
   - #### Returns all available(not deleted) categories
    
      ```http
      GET /categories
      ```
     _This endpoint supports paging and sorting._ 
      <details><summary>HTTP Request sample</summary>       
      
      ```http
      GET /categories?page=0&size=5&sort=name,asc
      ``` 
      </details>
      
      
   - #### Returns a category as per the identifier
    
      ```http
      GET /categories/{id}
      ```
    | Parameter | Description                       |
    | :-------- | :-------------------------------- |
    | `id`      | **Required**. Id of the category to be searched |

   - #### Returns a list of books for a specific category
      
      ```http
      GET /categories/{id}/books
      ```
    | Parameter | Description                       |
    | :-------- | :-------------------------------- |
    | `id`      | **Required**. Id of the book's category to be searched |

> [!IMPORTANT]
> The next endpoints in this chapter can only be accessed with **admin** role!

-
  - #### Create a new category with the provided body
    
    ```http
    POST /categories
    ```
      <details><summary>JSON Request sample</summary>
        
      ```json
        {
          "name": "Crime",
          "description": "some info"
        }
      ```
      </details>
      
   - #### Update an existing category with the provided id and body
    
     ```http
     PUT /categories/{id}
     ```
        | Parameter | Description                       |
        | :-------- | :-------------------------------- |
        | `id`      | **Required**. Id of the category to be updated |
      
      <details><summary>JSON Request sample</summary>
        
      ```json
        {
          "name": "Crime",
          "description": "some info"
        }
      ```
      </details>

   - #### Delete an existing category with the provided id.
    
      ```http
      DELETE /categories/{id}
      ```
      | Parameter | Description                       |
      | :-------- | :-------------------------------- |
      | `id`      | **Required**. Id of the category to be deleted |


- ### Shopping Cart Controller
  
   - #### Get all items in the shopping cart
    
      ```http
      GET /cart
      ```

   - #### Add a new item to the shopping cart
    
      ```http
      POST /cart
      ```
      <details><summary>JSON Request sample</summary>
        
      ```json
        {
          "bookId": 9,
          "quantity": 2
        }
      ```
      </details>

   - #### Update an existing cart item with the provided id and body
    
     ```http
     PUT /cart/cart-items/{id}
     ```
        | Parameter | Description                       |
        | :-------- | :-------------------------------- |
        | `id`      | **Required**. Id of the cart item to be updated |
      
      <details><summary>JSON Request sample</summary>
        
      ```json
        {
          "bookId": 9,
          "quantity": 2
        }
      ```
      </details>

   - #### Delete an existing cart item with the provided id
    
      ```http
      DELETE /cart/cart-items/{id}
      ```
      | Parameter | Description                       |
      | :-------- | :-------------------------------- |
      | `id`      | **Required**. Id of the cart item to be deleted |

- ### Order Controller
  
   - #### Retrieve the user's order history
    
      ```http
      GET /orders
      ```

     _This endpoint supports paging and sorting._ 
      <details><summary>HTTP Request sample</summary>       
      
      ```http
      GET /orders?sort=orderDate,desc
      ``` 
      </details>
      
   - #### Create a new order for the authenticated user based on the items in the shopping cart
    
      ```http
      POST /orders
      ```

   - #### Retrieve the items of a specific order
    
      ```http
      GET /orders/{orderId}/items
      ```
      | Parameter | Description                       |
      | :-------- | :-------------------------------- |
      | `orderId`      | **Required**. Id of the order to be searched |

   - #### Retrieve a specific item from a specific order
    
      ```http
      GET /orders/{orderId}/items/{itemId}
      ```
      | Parameter | Description                       |
      | :-------- | :-------------------------------- |
      | `orderId`      | **Required**. Id of the order to be searched |
      | `itemId`      | **Required**. Id of the order item to be searched |     

> [!IMPORTANT]
> The next endpoints in this chapter can only be accessed with **admin** role!

-
  - #### Update status by order id


    ```http
    PATCH /orders/{id}
    ```
      | Parameter | Description                       |
      | :-------- | :-------------------------------- |
      | `id`      | **Required**. Id of the order to be updated |
      <details><summary>JSON Request sample</summary>
        
      ```json
        {
          "status": "CANCELLED"
        }
      ```
      </details>
    <details><summary>Current possible parameters to specify</summary>
      
    - CREATED
    - COMPLETED
    - SHIPPED
    - CANCELLED
   </details>

   - #### Delete an order by id
    
      ```http
      DELETE /orders/{orderId}
      ```
      | Parameter | Description                       |
      | :-------- | :-------------------------------- |
      | `orderId`      | **Required**. Id of the order to be deleted |
