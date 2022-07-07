# BANKING SYSTEM - MID-TERM PROJECT IRONHACK

### 📌 ABOUT

This project is the Ironhack Web Development Bootcamp mid-term project. In this project we have created a banking system.

## :computer: Technology Stack

### Pre-requisites

The programming language of this repository is **Java 16.0.1**, therefore must have Java 16 installed.
To check your version of the software, run the following function on your local machine:

```
java -version 16.0.1
```

Other tools used in the development of the project:

* [Maven] - Dependencies handler
* [SpringBoot v2.5.4]
* [MySQL]
* [JUnit]
* [Postman]

### Installation

In order to import the project to your local repository and start application, you should write the following
commands in your IDE terminal:

```
git init
git pull https://github.com/Deingun/midterm-project-banking-system.git
```

You can also clone the project from GitHub or download it as a zip file and open 
it on IntelliJ as you would any other project.

The Application runs on the **BankingsystemApplication.java** archive. Before
running the program you can create the databases in your local MySQL Workbench, for convenience testing the functionalities. 
I have included a script in the [resources folder](/src/main/resources).
#### Important: All the encrypted passwords of the users previously created in the database are: 123456
If no data is inserted into the database, when the application is started, the following user with ADMIN profile is automatically generated:

username: adminTest     

password: 123456

In the same way I include the Postman collection with the different http verbs: Banking System.postman_collection.

### Project requirements

The system must have 4 types of accounts: StudentChecking, Checking, Savings, and CreditCard.
Checking

#### Checking Accounts should have:

- A balance
- A secretKey
- A PrimaryOwner
- An optional SecondaryOwner
- A minimumBalance
- A penaltyFee
- A monthlyMaintenanceFee
- A creationDate
- A status (FROZEN, ACTIVE)

#### StudentChecking

Student Checking Accounts are identical to Checking Accounts except that they do NOT have:

- A monthlyMaintenanceFee
- A minimumBalance

#### Savings

Savings are identical to Checking accounts except that they

- Do NOT have a monthlyMaintenanceFee
- Do have an interestRate

#### CreditCard

CreditCard Accounts have:

- A balance
- A PrimaryOwner
- An optional SecondaryOwner
- A creditLimit
- An interestRate
- A penaltyFee
- The system must have 3 types of Users: Admins and AccountHolders.


##### Author's note: All accounts have an account number with "entity code", "branch code" and account number, generated automatically when creating a new account.

#### AccountHolders

The AccountHolders should be able to access their own accounts and only their accounts when passing the correct credentials using Basic Auth. AccountHolders have:

- A name
- Date of birth
- A primaryAddress (which should be a separate address class)
- An optional mailingAddress

#### Admins

- Admins only have a name

#### ThirdParty

- The ThirdParty Accounts have a hashed key and a name.

Admins can create new accounts. When creating a new account they can create Checking, Savings, or CreditCard Accounts.

#### Savings

Savings accounts have a default interest rate of 0.0025
Savings accounts may be instantiated with an interest rate other than the default, with a maximum interest rate of 0.5
Savings accounts should have a default minimumBalance of 1000
Savings accounts may be instantiated with a minimum balance of less than 1000 but no lower than 100

#### CreditCards

CreditCard accounts have a default creditLimit of 100
CreditCards may be instantiated with a creditLimit higher than 100 but not higher than 100000
CreditCards have a default interestRate of 0.2
CreditCards may be instantiated with an interestRate less than 0.2 but not lower than 0.1

#### CheckingAccounts

When creating a new Checking account, if the primaryOwner is less than 24, a StudentChecking account should be created otherwise a regular Checking Account should be created.
Checking accounts should have a minimumBalance of 250 and a monthlyMaintenanceFee of 12
Interest and Fees should be applied appropriately

### PenaltyFee

The penaltyFee for all accounts should be 40.
If any account drops below the minimumBalance, the penaltyFee should be deducted from the balance automatically
InterestRate

Interest on savings accounts is added to the account annually at the rate of specified interestRate per year. That means that if I have 1000000 in a savings account with a 0.01 interest rate, 1% of 1 Million is added to my account after 1 year. When a savings account balance is accessed, you must determine if it has been 1 year or more since either the account was created or since interest was added to the account, and add the appropriate interest to the balance if necessary.

Interest on credit cards is added to the balance monthly. If you have a 12% interest rate (0.12) then 1% interest will be added to the account monthly. When the balance of a credit card is accessed, check to determine if it has been 1 month or more since the account was created or since interested was added, and if so, add the appropriate interest to the balance.

### Account Access
#### Admins

Admins should be able to access the balance for any account and to modify it.

#### AccountHolders

AccountHolders should be able to access their own account balance
Account holders should be able to transfer money from any of their accounts to any other account (regardless of owner). The transfer should only be processed if the account has sufficient funds. The user must provide the Primary or Secondary owner name and the id of the account that should receive the transfer.
##### Author's note: Transfers are made through automatically generated account numbers.

#### Third-Party Users

There must be a way for third-party users to receive and send money to other accounts.
Third-party users must be added to the database by an admin.
In order to receive and send money, Third-Party Users must provide their hashed key in the header of the HTTP request. They also must provide the amount, the Account id and the account secret key.

### Fraud Detection
The application must recognize patterns that indicate fraud and Freeze the account status when potential fraud is detected.

Patterns that indicate fraud include:

Transactions made in 24 hours total to more than 150% of the customers highest daily total transactions in any other 24 hour period.
More than 2 transactions occurring on a single account within a 1 second period.
##### Author's note: If there are no transfers in the database, fraud is detected when making more than 3 transfers in one day.

### Good Practices
You must include the thorough unit and integration tests.
You must include robust error handling.
You must use the Money class for all currency and BigDecimal for any other decimal or large number math.

## :star: Author


* **David de Ingunza de Caso** - [Deingun](https://github.com/Deingun)


## ♥️ Thanks

Big thanks to TAs and teachers for the help and support in the development of this course:

* **Victor Cardozo** - [GazzD](https://github.com/GazzD)
* **Adrià López** - [AdriaLopez-Ironhack](https://github.com/AdriaLopez-Ironhack)
* **Iván Trujillo** - [IvanTrujillo-Ironhack](https://github.com/IvanTrujillo-Ironhack)

### IH ES WDPT - SEPT21 - BECAS SANTANDER TECH

