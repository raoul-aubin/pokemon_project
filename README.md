# Pokémon Manager – Full Stack Application

A full-stack Pokémon management application built with Angular (frontend) and Java Jakarta EE / Payara (backend) using JWT authentication.

Users can explore Pokémon publicly and manage their own collection after creating an account.

---
## Features

### Public access

Without logging in, users can:

- View the Pokémon list

- Search Pokémon

- Open a Pokémon detail page

- Register a new account

- Login to an existing account

The application opens directly on the Pokémon list page.
Authentication is handled using JWT tokens.


### Authenticated access

Once logged in, users can access additional features from the navigation menu.


#### My Pokémons

Users can:

- View their personal Pokémon collection

- Add a new Pokémon

- Update a Pokémon

- Delete a Pokémon

Important:
- Pokémon can only be edited or deleted from the My Pokémons page, not from the public Pokémon list.


#### My Account

Users can manage their account:

- Update username

- Update email

- Change password

- Delete their account

Deleting the account also removes their Pokémon.


#### Logout

Users can logout by clicking Logout in the navigation menu.

---
## Technologies Used
### Frontend

- Angular

- TypeScript

- Bootstrap

- RxJS

- Angular Router

- HTTP Interceptor (JWT)


### Backend

- Java

- Jakarta EE

- JAX-RS

- JPA (Hibernate)

- JWT authentication

- Payara Server

---
## How to Run the Project
### Start the Backend

- Build the backend:
```bash
cd pokemon_backend
mvn clean package
```

- Start Payara server:

```bash
C:\payara6\bin\asadmin.bat start-domain
```

- Deploy the backend:

```bash
C:\payara6\bin\asadmin.bat deploy --contextroot pokemon_backend target\pokemon_backend.war
```

- The backend will run at:
```bash
http://localhost:8080/pokemon_backend
```


### Start the Frontend

```bash
cd pokemon
npm install
ng serve
```

- The application will run at:
```bash
http://localhost:4200
```

---
## API Endpoints Overview
### Public
```bash
GET /api/pokemons
GET /api/pokemons/{id}
GET /api/pokemons/search?name=
POST /auth/login
POST /auth/register
```


### Protected (JWT required)
```bash
GET /api/me
PUT /api/me
DELETE /api/me
PUT /api/me/password
GET /api/me/pokemons

POST /api/pokemons
PUT /api/pokemons/{id}
DELETE /api/pokemons/{id}
```

---
## Author

Developed by Raoul Tchangou as a full-stack learning project combining Angular and Java backend development.