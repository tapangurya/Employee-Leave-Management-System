<div align="center">
🏢 Leave Management System (LMS)
A role-based Leave Management System built using Spring Boot, Thymeleaf, Hibernate (JPA), and MySQL, designed with a clean Manager–Employee workflow.
</div>
📌 Project Overview

📄 The Leave Management System digitizes the leave approval process inside an organization.

👨‍💼 Employees apply for leave

🧑‍💻 Managers review and approve/reject requests

⚙️ System automatically maintains leave balances

🛠 Tech Stack
🔹 Backend

☕ Java

🌱 Spring Boot

🔄 Spring MVC

🗄 Spring Data JPA (Hibernate)

🔹 Frontend

🌐 HTML5

🎨 CSS3

🍃 Thymeleaf

🔹 Database

🐬 MySQL

🔹 Tools

📦 Maven

🌱 Git & GitHub

🧠 Eclipse IDE

👥 User Roles
Role	Description
👨‍💼 Manager	Approves or rejects leave
👨‍💻 Employee	Applies for leave

⚠️ No Admin role is used in this project.


👥 User Roles & Responsibilities

This system has only two roles:

👨‍💼 Manager

👨‍💻 Employee

👨‍💼 Manager Capabilities (Core Role)

In this system, the Manager is the primary controlling role.

✅ Manager can:

  📝 Register itself (self-registration)
  🔐 Login to the system
  👥 Create employee accounts
  ✏️ Edit employee details, including:
                            Name
                            Email
                            Role / Designation,etc.
  👀 View all employees under their management
  📥 View leave applications submitted by employees
  ✔ Approve leave requests
  ❌ Reject leave requests
  ⚙ Automatically manage leave balances on approval

👨‍💻 Employee Capabilities
✅ Employee can:

  🔐 Login to the system
  👤 View own profile
  ✏️ Edit basic personal details
  📊 View leave balance
  📝 Apply for leave
  ⏳ Track leave status:
                    PENDING
                    APPROVED
                    REJECTED
                  
🔐 Authentication & Authorization
✅ Session-based authentication
✅ Role-based access control
✅ Login required for dashboards
❌ Unauthorized users redirected to login

📌 Design Note
❗ There is no Admin role in this system.
The Manager handles both employee management and leave approval, making the system simple and practical for small-to-medium organizations.

🗃 Database Tables
    🧑‍💼 manager
    👨‍💻 employee
    📝 application
    📊 leave_balance

⚠ Validations Implemented
    ✔ Session validation
    ✔ Leave balance check
    ✔ Date validation
    ✔ Manager authorization
    ✔ Null & invalid request handling

🚀 How to Run the Project
🔽 Clone Repository
git clone https://github.com/your-username/leave-management-system.git

⚙ Configure application.properties
          spring.datasource.url=jdbc:mysql://localhost:3306/lms_db
          spring.datasource.username=root
          spring.datasource.password=yourpassword

Future Enhancements
📧 Email notifications
🔐 Spring Security
🔑 JWT authentication
📄 Leave history reports
📅 Calendar-based leave view
🔐 Random Password Generation & Email Delivery
    When a Manager creates an Employee:
    System generates a secure random password
    Password is encrypted and stored in the database
    Employee credentials are sent to employee’s email

👨‍💻 Author
Tapan Gurya
🎓 B.Tech in Computer Science & Engineering

⭐ If you like this project, give it a star on GitHub ⭐
