Webshop
Webshop is a full-stack e-commerce application designed to provide users with a seamless online shopping experience. The project integrates a robust backend with an intuitive frontend, facilitating product browsing, user authentication, and order management.​

Features
User Authentication: Secure login and registration functionalities to protect user data.​
Product Management: Display of products with various attributes and variants.​
Order Processing: Efficient handling of user orders from creation to completion.​
Technologies Used
Backend:
Java​
Spring Boot​
Frontend:
Angular (TypeScript, HTML, SCSS)​
Containerization:
Docker​
Project Structure
Backend: Contains Java classes such as AuthenticationDTO.java, LoginResponse.java, OrderDTO.java, ProductDTO.java, and ProductVariantsDTO.java for managing data transfer and authentication processes.​
Frontend: Located in the saraFront directory, built with Angular to provide a dynamic user interface.​
Demo: The demo directory includes demonstration files to showcase the application's capabilities.​
Getting Started
To run the application locally:

Clone the repository:

bash
Copy code
git clone https://github.com/saradenno/Webshop.git
Navigate to the backend directory and build the application:

bash
Copy code
cd Webshop/backend
./mvnw clean install
Navigate to the frontend directory and install dependencies:

bash
Copy code
cd ../saraFront
npm install
Start the backend server:

bash
Copy code
cd ../backend
./mvnw spring-boot:run
Start the frontend server:

bash
Copy code
cd ../saraFront
ng serve
Access the application:

Open your browser and navigate to http://localhost:4200.

