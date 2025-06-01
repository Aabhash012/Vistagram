# 📸 Vistagram

Vistagram is a social media backend API built using **Java Spring Boot**. It allows users to upload and view posts, like and share posts, and explore a timeline feed (sorted by reverse chronological order) — simulating the core experience of platforms like Instagram.

---

## 🚀 Features

### 🔐 User Management
- manage user profiles (e.g. Update username)
- Search users by username

### 📸 Post Management
- Upload a post with:
    - Image (via multipart upload)
    - Caption
    - Optional POI (Point of Interest) data like name and location
- View a specific post by ID
- Fetch all posts by a specific user
- Search posts by caption or location

### 📰 Timeline
- View a **global timeline** of posts sorted by newest first
- Efficient pagination support

### ❤️ Likes
- Like or unlike a post
- Get total like count per post
- Check if a post is liked by a user
- View all posts liked by a user

### 🔄 Shares
- Share/unshare a post
- Get total share count per post
- View all posts shared by a user

### 🧪 Data Seeding (Dev Utility)
- A Python script is used to generate a synthetic dataset, producing realistic captions, usernames, timestamps, and image URLs.
- Once the seed dataset is generated, the output is copied into the Spring Boot project at: src/main/resources/seed-data.json
- On application startup, the DataSeeder component reads the seed-data.json file.
- Posts are created only if the database is empty to avoid duplication.
- For each post in the dataset, if the username doesn’t already exist, a new user is created and associated with the post.

---

## 🛠️ Tech Stack

| Layer         | Technology |
|--------------|------|
| Language      | Java |
| Framework     | Spring Boot |
| Persistence   | Spring Data JPA, Hibernate|
| Database      | PostgreSQL |
| Build Tool    | Maven |
| Image Upload  | Local Storage (can be extended to S3, etc.) |
| API Style     | REST |
| Object Mapping| ModelMapper |

---
### 🔧 Steps to Run the Project:
1. **Clone the Repository**

   ```bash
   git clone https://github.com/Aabhash012/Vistagram.git
   cd vistagram
2. **Configure the Database**
   Update application.yml with your DB credentials:
3. **Build the Project**
    ```bash
    mvn clean install
    ```    
4. **Run the Application**
    ```bash
    mvn spring-boot:run
    ``` 
5. **Access the API**

   Base URL: http://localhost:8080/api/v1/

    Example endpoint: GET /api/v1/post/{postId}


6. **Test with Postman**

    Use @RequestParam for multipart uploads.

   Pagination is supported using ?page=0&size=10


---
✅ Test Coverage

Vistagram includes unit test cases for core service and controller layers to ensure functionality and stability.
Tests are written using JUnit and Mockito, covering scenarios like:

The tests help validate business logic and provide confidence during future refactoring or feature additions.

---

## ✅ Future Improvements

- 🔐 **JWT-based Authentication** using Spring Security
- 🖼️ **Image Storage on AWS S3** instead of local file system
- 🧠 **Recommendation Engine**: Show posts based on user activity
- 💬 **Comments System**: Add support for post comments
- 📸 **Story Feature** similar to Instagram
- 🔍 **Advanced Search** with filters (date, location, tags)

---

## 🧪 API Testing

Use Postman or cURL to test the endpoints. Multipart upload for images is supported using `@RequestParam`.

---




