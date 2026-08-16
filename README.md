# 🏦 Bank Management Application

A **Bank Management REST API** developed using **Spring Boot** and **PostgreSQL** to manage banks, addresses, and customer accounts.

The application follows a **Layered Architecture** and uses **JPA/Hibernate association mappings** to establish relationships between `Bank`, `Address`, and `Account` entities.

The project also implements business validations for account creation, deposits, withdrawals, transfers, account searching, sorting, and deletion restrictions.

---

## 🚀 Features

### 🏦 Bank Management

* Create a bank
* Create multiple banks
* Retrieve all banks
* Retrieve bank by ID
* Delete a bank
* Retrieve banks using pagination and sorting
* Find banks using IFSC code
* Find banks by address
* Find banks by city
* Find banks by contact number

### 📍 Address Management

* Retrieve address by ID
* Update address using PUT/PATCH
* Retrieve address by bank
* Retrieve addresses by city
* Retrieve addresses by city and street

### 💳 Account Management

* Create an account
* Create multiple accounts
* Retrieve all accounts
* Retrieve account by ID
* Delete an account
* Deposit money
* Withdraw money
* Transfer money between accounts
* Retrieve accounts by bank
* Retrieve accounts by account type
* Retrieve accounts whose balance is greater than a specified value
* Retrieve account by account number
* Sort accounts by account holder name

---

## 🛠️ Tech Stack

| Technology          | Purpose                                   |
| ------------------- | ----------------------------------------- |
| **Java**            | Programming Language                      |
| **Spring Boot**     | Backend application development           |
| **Spring Data JPA** | Database interaction and repository layer |
| **Hibernate**       | ORM and entity mapping                    |
| **PostgreSQL**      | Relational database                       |
| **Lombok**          | Reducing boilerplate code                 |
| **Apache Tomcat**   | Embedded application server               |
| **Postman**         | REST API testing                          |
| **Maven**           | Dependency management and build tool      |

---

## 🏗️ Architecture

The application follows a **Layered Architecture**:

```text
                    Client
                      │
                      ▼
               ┌──────────────┐
               │  Controller  │
               └──────┬───────┘
                      │
                      ▼
               ┌──────────────┐
               │   Service    │
               │ Business     │
               │   Logic      │
               └──────┬───────┘
                      │
                      ▼
               ┌──────────────┐
               │  Repository  │
               └──────┬───────┘
                      │
                      ▼
               ┌──────────────┐
               │  PostgreSQL  │
               └──────────────┘
```

### Layers

#### Controller Layer

Responsible for:

* Receiving HTTP requests
* Mapping REST endpoints
* Accepting path variables and request bodies
* Returning `ResponseEntity`
* Communicating with the Service layer

#### Service Layer

Responsible for:

* Implementing business logic
* Validating input data
* Performing banking operations
* Handling exceptions
* Communicating with the Repository layer

#### Repository Layer

Responsible for:

* Database operations
* CRUD operations
* Derived query methods using Spring Data JPA
* Sorting and filtering

#### Entity Layer

Contains the JPA entity classes representing the database tables:

* `Bank`
* `Address`
* `Account`

---

## 🔗 Entity Relationships

The application contains three main entities.

```text
              ┌──────────────┐
              │     Bank     │
              └──────┬───────┘
                     │
                1    │    1
                     │
                     ▼
              ┌──────────────┐
              │   Address    │
              └──────────────┘


              ┌──────────────┐
              │     Bank     │
              └──────┬───────┘
                     │
                1    │    *
                     │
                     ▼
              ┌──────────────┐
              │   Account    │
              └──────────────┘
```

### Bank → Address

A bank is associated with an address using a **One-to-One relationship**.

### Bank → Account

A bank can have multiple accounts, while each account belongs to one bank. This is implemented using a **One-to-Many / Many-to-One association**.

Example:

```java
@ManyToOne
@JoinColumn
private Bank bank;
```

---

# 📋 API Overview

## 🏦 Bank APIs

| Operation                         | Description                            |
| --------------------------------- | -------------------------------------- |
| Create Bank                       | Creates a new bank                     |
| Get All Banks                     | Retrieves all bank records             |
| Get Bank By ID                    | Retrieves a bank using its ID          |
| Delete Bank                       | Deletes a bank if business rules allow |
| Get Banks By Pagination & Sorting | Retrieves paginated and sorted banks   |
| Get Bank By IFSC                  | Finds a bank using IFSC code           |
| Get Bank By Address               | Finds bank using address details       |
| Get Bank By City                  | Finds banks located in a city          |
| Get Bank By Contact Number        | Finds bank using contact number        |

---

# 📍 Address APIs

| Operation                    | Description                              |
| ---------------------------- | ---------------------------------------- |
| Get Address By ID            | Retrieves an address using ID            |
| Update Address               | Updates an existing address              |
| Get Address By Bank          | Retrieves address associated with a bank |
| Get Address By City          | Finds addresses using city               |
| Get Address By City & Street | Finds addresses using city and street    |

---

# 💳 Account APIs

| Operation                     | Description                                  |
| ----------------------------- | -------------------------------------------- |
| Create Account                | Creates a new bank account                   |
| Create Multiple Accounts      | Saves multiple accounts                      |
| Get All Accounts              | Retrieves all accounts                       |
| Get Account By ID             | Retrieves account using ID                   |
| Delete Account                | Deletes an account                           |
| Deposit Amount                | Deposits money into an account               |
| Withdraw Amount               | Withdraws money from an account              |
| Transfer Amount               | Transfers money between two accounts         |
| Get Account By Bank           | Retrieves accounts belonging to a bank       |
| Get Account By Type           | Retrieves accounts based on account type     |
| Get Account By Balance        | Retrieves accounts above a specified balance |
| Get Account By Account Number | Retrieves an account using account number    |
| Sort Accounts                 | Sorts accounts by account holder name        |

---

# 💰 Banking Business Logic

The application implements important banking rules.

### 1. Unique Account Number

Every account must have a unique account number.

```java
@Column(unique = true)
private String accountNumber;
```

Duplicate account numbers are not allowed.

---

### 2. Bank Must Exist

An account cannot be created if the associated bank does not exist.

```text
Create Account
      │
      ▼
Check Bank
      │
 ┌────┴────┐
 │         │
Exists    Not Exists
 │         │
 ▼         ▼
Save    Reject
```

---

### 3. Minimum Balance

Minimum balance rules are applied depending on the account type:

* Savings Account
* Current Account

The account must satisfy the required minimum balance during creation and applicable operations.

---

### 4. Deposit

The deposit amount must be greater than zero.

```text
Deposit Amount > 0
        │
        ▼
Check Account
        │
        ▼
Add Amount To Balance
```

Example:

```text
Old Balance = ₹10,000
Deposit     = ₹2,000
-----------------------
New Balance = ₹12,000
```

---

### 5. Withdrawal

Withdrawal amount must:

* Be greater than zero
* Belong to an existing account
* Not exceed the available balance

```text
Withdrawal Amount > 0
          │
          ▼
Check Account
          │
          ▼
Check Balance
          │
     ┌────┴────┐
     │         │
 Sufficient  Insufficient
     │         │
     ▼         ▼
Withdraw    Reject
```

---

### 6. Money Transfer

Transfer requires:

* Transfer amount must be greater than zero
* Sender account must exist
* Receiver account must exist
* Sender and receiver must not be the same
* Sender must have sufficient balance

```text
Sender Account
      │
      ▼
Check Balance
      │
      ▼
Deduct Amount
      │
      ▼
Receiver Account
      │
      ▼
Add Amount
```

The transfer operation updates both accounts.

---

### 7. Bank Deletion Restriction

A bank cannot be deleted if it has associated active accounts.

```text
Delete Bank
     │
     ▼
Check Accounts
     │
 ┌───┴────┐
 │        │
Exists   None
 │        │
 ▼        ▼
Reject   Delete
```

This prevents deletion of a bank that is still being used by accounts.

---

### 8. Pincode Validation

The address pincode must contain exactly **6 digits**.

```text
Pincode → 6 digits → Valid
Pincode → Not 6 digits → Reject
```

---

### 9. Contact Number Validation

The bank contact number must contain exactly **10 digits**.

```text
Contact Number → 10 digits → Valid
Contact Number → Not 10 digits → Reject
```

---

# 📦 Response Structure

The application uses a custom generic `ResponseStructure` to maintain a consistent API response format.

Example:

```json
{
    "statusCode": 200,
    "message": "Accounts retrieved successfully",
    "data": [
        {
            "accountNumber": "ACC100001",
            "accountHolderName": "Prajwal Kumar",
            "balance": 25000.50
        }
    ]
}
```

The generic structure allows different types of response data:

```java
ResponseStrucutre<Bank>
ResponseStrucutre<Account>
ResponseStrucutre<List<Bank>>
ResponseStrucutre<List<Account>>
ResponseStrucutre<String>
```

---

# 🌐 ResponseEntity

`ResponseEntity` is used in the Controller layer to return:

* Response body
* HTTP status code

Example:

```java
return new ResponseEntity<>(
        accountService.getAccountByBalanceGreaterThan(value),
        HttpStatus.OK
);
```

This provides better control over HTTP responses.

---

# 🔎 Spring Data JPA Derived Queries

The project uses Spring Data JPA derived query methods for filtering and searching.

Example:

```java
List<Account> findByBalanceGreaterThan(Double value);
```

This retrieves accounts where:

```text
balance > given value
```

Other examples include:

```java
findByAccountNumber(...)
findByAccType(...)
findByBank_BankId(...)
findByAddress_City(...)
findByBalanceGreaterThan(...)
```

This reduces the need to write SQL manually for common operations.

---

# 📊 Sorting & Pagination

The application uses Spring Data JPA's `Sort` and pagination functionality.

Example sorting:

```java
accountRepository.findAll(
    Sort.by("accountHolderName").ascending()
);
```

This allows accounts to be retrieved in ascending order based on the account holder's name.

Pagination and sorting are also implemented for bank retrieval.

---

# 🧩 Exception Handling

The application uses custom exceptions for business-rule violations.

Examples include:

```text
NoRecordAvailableException
NoProperPincode
ContactNumberNotProper
```

These exceptions are used when:

* Requested record does not exist
* Invalid pincode is provided
* Invalid contact number is provided
* Business rules are violated

---

# 🧪 API Testing

All REST APIs were tested using **Postman**.

Testing includes:

* POST requests
* GET requests
* PUT requests
* PATCH requests
* DELETE requests
* Request body validation
* Path variable validation
* Business logic validation
* Error scenarios
* Database operations

---

# 🗄️ Database

The application uses **PostgreSQL** as the relational database.

Hibernate/JPA handles:

* Entity-to-table mapping
* Relationships
* CRUD operations
* Generated IDs
* Unique constraints
* Database persistence

Example relationship:

```text
Bank
 │
 ├── bankId
 ├── bankName
 ├── IFSC
 ├── branchName
 ├── contact
 │
 └── Address

Account
 │
 ├── accountId
 ├── accountNumber
 ├── accountHolderName
 ├── accountType
 ├── balance
 │
 └── Bank
```

---

# 📁 Project Structure

```text
src
└── main
    ├── java
    │   └── org.jsp.Bank_Management_App
    │       │
    │       ├── controller
    │       │   ├── BankController
    │       │   ├── AddressController
    │       │   └── AccountController
    │       │
    │       ├── service
    │       │   ├── BankService
    │       │   ├── AddressService
    │       │   └── AccountService
    │       │
    │       ├── repository
    │       │   ├── BankRepository
    │       │   ├── AddressRepository
    │       │   └── AccountRepository
    │       │
    │       ├── entity
    │       │   ├── Bank
    │       │   ├── Address
    │       │   └── Account
    │       │
    │       ├── exception
    │       │   └── Custom Exceptions
    │       │
    │       └── response
    │           └── ResponseStructure
    │
    └── resources
        └── application.properties
```

---

# ⚙️ Configuration

Configure PostgreSQL in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Replace the database name, username, and password with your local PostgreSQL configuration.

---

# ▶️ How to Run

### 1. Clone the repository

```bash
git clone <your-github-repository-url>
```

### 2. Open the project

Open the project using:

* IntelliJ IDEA
* Eclipse
* VS Code

### 3. Configure PostgreSQL

Create a PostgreSQL database and update `application.properties`.

### 4. Build the project

```bash
mvn clean install
```

### 5. Run the application

Run the Spring Boot main class.

The application will start on the configured Apache Tomcat server.

### 6. Test APIs

Open Postman and test the available REST endpoints.

---

# 🔐 Validation & Data Integrity

The project combines application-level business validation with database-level constraints.

Examples:

* Unique account number
* Valid account type
* Bank existence validation
* Minimum account balance
* Positive deposit amount
* Positive withdrawal amount
* Sufficient withdrawal balance
* Positive transfer amount
* Valid sender and receiver
* Sender and receiver cannot be the same
* Six-digit pincode
* Ten-digit contact number
* Bank deletion restriction when accounts exist

---

# 🎯 Learning Outcomes

Through this project, I gained practical experience in:

* Building RESTful APIs using Spring Boot
* Implementing layered architecture
* Designing entity relationships
* One-to-One and Many-to-One association mapping
* Working with Spring Data JPA
* Creating derived query methods
* Implementing CRUD operations
* Implementing pagination and sorting
* Handling business logic in the service layer
* Creating custom exceptions
* Using `ResponseEntity`
* Creating generic response structures
* Working with PostgreSQL
* Testing APIs using Postman
* Implementing real-world banking business rules

---

# 📌 Future Enhancements

Possible future improvements include:

* Spring Security and JWT authentication
* Role-based authorization
* Transaction history
* Account statement generation
* Customer profile management
* Global exception handler using `@ControllerAdvice`
* API documentation using Swagger/OpenAPI
* DTO layer for request/response separation
* Unit and integration testing
* Docker containerization

---

# 👨‍💻 Author

**Prajwal K**

Java Backend / Spring Boot Developer

---

## ⭐ Project Highlights

> A Spring Boot based Bank Management REST API implementing real-world banking operations with PostgreSQL persistence, JPA association mappings, layered architecture, business validations, custom exception handling, pagination, sorting, and transaction operations.
