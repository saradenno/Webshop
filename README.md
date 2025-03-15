# 🛍️ Webshop  
A **full-stack e-commerce application** built with **Spring Boot** and **Angular**. This webshop allows users to browse products, manage orders, and experience a seamless online shopping journey.

---

## 🚀 Features  

### 🔹 User Features  
✅ Browse products and view detailed descriptions  
✅ Add items to the shopping cart and adjust quantities  
✅ Secure user authentication (login/register)  
✅ Checkout and place orders  
✅ View order history and details  

### 🔹 Admin Features  
✅ Secure admin authentication (login/logout)  
✅ Manage products (create, edit, delete, update stock)  
✅ Manage categories (add, edit, remove)  
✅ View and manage customer orders  

### 🔹 Tech Stack  
- **Backend:** Spring Boot ☕  
- **Frontend:** Angular ⚡  
- **Database:** PostgreSQL 🐘  
- **Containerization:** Docker 🐳  

---

## 📌 Installation Guide  

### 🔧 Prerequisites  
Make sure you have the following installed:  
✔ Java (JDK 17 recommended)  
✔ Node.js & npm  
✔ Angular CLI  
✔ Docker (optional)  

---

## 🛠 Setup Steps  

### 1️⃣ Clone the Repository  
```bash
git clone https://github.com/saradenno/Webshop.git
cd Webshop

2️⃣ Backend Setup (Spring Boot)
bash
Copy code
cd backend
./mvnw clean install
./mvnw spring-boot:run
3️⃣ Frontend Setup (Angular)
bash
Copy code
cd ../saraFront
npm install
ng serve
4️⃣ Access the Application
Open your browser and visit:
🔗 Frontend: http://localhost:4200
🔗 Backend API: http://localhost:8080

🐳 Docker Setup (Optional)
You can run the entire application using Docker:

bash
Copy code
docker-compose up --build
