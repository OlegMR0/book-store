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
  

## API Reference
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




