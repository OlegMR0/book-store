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

### Returns all available(not deleted) books

```http
GET /books
```

### Returns a book as per the identifier

```http
GET /books/{id}
```
| Parameter | Description                       |
| :-------- | :-------------------------------- |
| `id`      | **Required**. Id of book to be searched |



### Find books by specific parameters

```http
GET /books/search
```
| Parameter | Description                       |
| :-------- | :-------------------------------- |
| `title`   | Title of the book to be searched. The flexible parameter, returns all similar options.|
| `author`   | Author of the book to be searched. Might be plural.|
| `Isbn`   | International Standard Book Number of the book to be searched. Might be plural.|
| `categories`   | Category id of the book to be searched. Might be plural.|
| `priceFrom`   | Minimal price of the book to be searched.|
| `priceTo`   | Minimal price of the book to be searched.|

> [!IMPORTANT]
> The next endpoints in this chapter can only be accessed with admin role!

### Create a new book with the provided body

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
> [!TIP]
> You can easily just skip unnecessary params.
</details>

### Update an existing book with the provided id and body

```http
PUT /books/{id}
```
| Parameter | Description                       |
| :-------- | :-------------------------------- |
| `id`      | **Required**. Id of book to be searched |

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
</details>
```
### Delete an existing book with the provided id.


```http
DELETE /books/{id}
```
| Parameter | Description                       |
| :-------- | :-------------------------------- |
| `id`      | **Required**. Id of book to be searched |





| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |



