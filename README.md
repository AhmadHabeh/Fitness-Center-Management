# **🏋️ Fitness Center Management System**

## **📋 Overview**
The **Fitness Center Management System** is a backend application built with **Spring Boot** and **PostgreSQL** to manage gym operations efficiently. It allows users to:
- **Register and authenticate securely** 🔐
- **Check in/out of workout sessions** ⏳
- **Track equipment usage and exercises** 🏋️
- **Register for fitness classes** 📅
- **Manage user profiles and fitness goals** 🎯

---

## **📁 Project Structure**

### **Backend (Spring Boot)**
📂 **`src/main/java/com/techelevator/`**
- **`controller/`** *(Handles API requests)*
  - `AuthenticationController.java` - Manages user authentication (login & registration).
  - `UserDetailsController.java` - Handles user profiles and fitness goals.
  - `WorkoutSessionController.java` - Manages user check-in/check-out for sessions.
  - `ExerciseController.java` - Manages exercise data.
  - `EquipmentUsageController.java` - Logs gym equipment usage.
  - `GymClassController.java` - Handles fitness class scheduling and management.
  - `ClassRegistrationController.java` - Manages user registration for classes.

- **`dao/`** *(Data Access Layer - Interfaces & JDBC Implementations)*
  - `UserDao.java` / `JdbcUserDao.java` - Handles user-related database operations.
  - `UserDetailsDao.java` / `JdbcUserDetailsDao.java` - Manages user profiles and fitness goals.
  - `WorkoutSessionDao.java` / `JdbcWorkoutSessionDao.java` - Handles user workout sessions.
  - `ExerciseDao.java` / `JdbcExerciseDao.java` - Manages exercise data.
  - `EquipmentUsageDao.java` / `JdbcEquipmentUsageDao.java` - Tracks gym equipment usage.
  - `GymClassDao.java` / `JdbcGymClassDao.java` - Handles fitness class management.
  - `ClassRegistrationDao.java` / `JdbcClassRegistrationDao.java` - Manages user class registrations.

- **`model/`** *(Represents data entities)*
  - `User.java` - Represents a user.
  - `UserDetails.java` - Represents additional user details and goals.
  - `WorkoutSession.java` - Represents a user's workout session.
  - `Exercise.java` - Represents an exercise.
  - `EquipmentUsage.java` - Represents equipment usage data.
  - `GymClass.java` - Represents a gym class.
  - `ClassRegistration.java` - Represents user registration for a class.
  - `LoginDto.java` - Handles user login requests.
  - `RegisterUserDto.java` - Handles new user registrations.
  - `LoginResponseDto.java` - Contains authentication response.

- **`exception/`**
  - `DaoException.java` - Custom exception for database-related errors.

---

## **💾 Database Schema (PostgreSQL)**
The application uses a **PostgreSQL** database with the following tables:
- **`users`** - Stores user credentials.
- **`user_details`** - Stores user profiles and fitness goals.
- **`workout_session`** - Tracks user workout sessions (check-in/out).
- **`exercises`** - Stores available exercises.
- **`equipment_usage`** - Logs user interactions with gym equipment.
- **`gym_class`** - Stores information about fitness classes.
- **`class_registration`** - Links users to their registered fitness classes.

---

## **🚀 Installation & Setup**

### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/YOUR-USERNAME/Fitness-Center-Management.git
cd Fitness-Center-Management
```

### **2️⃣ Backend Setup**
- Install **Java 17** and **PostgreSQL**.
- Update **`application.properties`** with your database credentials.
- Run the application:
  ```sh
  mvn spring-boot:run
  ```

---

## **🔐 User Authentication & Authorization**
- **JWT-based authentication** secures API endpoints.
- User roles:  
  - **`ROLE_ADMIN`** - Full access (manage users, classes, equipment).  
  - **`ROLE_USER`** - Limited access (register for classes, log workouts).

---

## **🎯 Features**
### **User Features**
✅ Secure login & registration  
✅ Check-in & check-out from gym sessions  
✅ Register & unregister for fitness classes  
✅ Log exercise details & equipment usage  
✅ Manage fitness goals & personal profile  

### **Admin Features**
🔹 View all registered users  
🔹 Manage fitness classes & schedules  
🔹 Monitor equipment usage data  

---


## **🤝 Contributing**
Pull requests are welcome! To contribute:
1. Fork the repository 🍴
2. Create a new branch (`git checkout -b feature-name`)
3. Commit your changes (`git commit -m 'Add new feature'`)
4. Push to your fork (`git push origin feature-name`)
5. Open a Pull Request 📌

---

## **📜 License**
This project is licensed under the **MIT License**.

---

## **📞 Contact**
For questions, reach out to:
- **GitHub:** [AhmadHabeh](https://github.com/AhmadHabeh)
- **Email:** ahmadhabaps@gmail.com

---

🔥 Built with **Spring Boot & PostgreSQL** 🔥

