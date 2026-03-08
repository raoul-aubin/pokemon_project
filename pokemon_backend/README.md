# 🐱‍👤 Pokémon Backend API

Dieses Projekt ist ein sicheres RESTful Backend zur Verwaltung von Pokémon und Benutzern.
Es wurde mit Jakarta EE, Payara Server, JPA (EclipseLink) und MySQL entwickelt.

Die API implementiert eine JWT-basierte Authentifizierung, eine Benutzerverwaltung sowie eine Zugriffskontrolle, die sicherstellt, dass nur der Besitzer eines Pokémon dieses ändern oder löschen darf.

## 🚀 Technologien

- Java 17

- Jakarta EE 10

- JAX-RS (REST)

- CDI

- JPA (EclipseLink)

- Payara Server 6

- MySQL

- Maven

- JWT (JJWT)

- BCrypt

- Postman (API-Tests)

- DBeaver (Datenbankverwaltung)

## 📦 Aktuelle Funktionen
### 🔐 Authentifizierung & Sicherheit

- Benutzerregistrierung (register)

- Benutzeranmeldung (login)

- JWT-Authentifizierung (Bearer Token)

- Passwort-Hashing mit BCrypt

- Globaler Authentifizierungsfilter (AuthFilter)

- Öffentliche und geschützte Endpunkte

- Zugriff auf den angemeldeten Benutzer über SecurityContext

### 👤 Benutzer

- Eigenes Profil anzeigen (/me)

- Benutzername und E-Mail ändern

- Passwort ändern

- Benutzerkonto löschen

- Eigene Pokémon anzeigen

- Automatisches Löschen aller Pokémon beim Löschen des Kontos

### 🐾 Pokémon

- Alle Pokémon anzeigen (öffentlich)

- Pokémon nach ID anzeigen (öffentlich)

- Pokémon erstellen (authentifiziert)

- Pokémon bearbeiten (nur Besitzer)

- Pokémon löschen (nur Besitzer)

- Beziehung User ↔ Pokémon (1:n)

## 🗂️ Projektstruktur
src/main/java  
└── com.pokemon.pokemon_backend    
├── model      # JPA-Entities (User, Pokemon)  
├── dto        # DTOs (Auth, Register, Login, UserPublic)  
├── service    # Business-Logik & Transaktionen
├── resource   # REST-Endpunkte
└── security   # JWT-Service & Auth-Filter

## 🗄️ Datenbank

- MySQL

- Verbindung über JNDI Datasource in Payara

- Automatische Schema-Generierung durch JPA

## Wichtige Tabellen

- users

- pokemon (mit Fremdschlüssel owner_id)

## ⚙️ Konfiguration
### persistence.xml

- Pfad: src/main/resources/META-INF/persistence.xml


### Verwendet:

- JTA

- JNDI Datasource: jdbc/pokemonDS

- Automatische Schema-Erstellung

## 🔌 REST-Endpunkte
### 🔓 Öffentliche Endpunkte
| Methode | Endpoint         | Beschreibung             |
| ------- | ---------------- | ------------------------ |
| POST    | `/auth/register` | Benutzer registrieren    |
| POST    | `/auth/login`    | Benutzer anmelden        |
| GET     | `/pokemons`      | Alle Pokémon anzeigen    |
| GET     | `/pokemons/{id}` | Pokémon nach ID anzeigen |

### 🔐 Geschützte Endpunkte (JWT erforderlich)
#### Benutzer (/me)
| Methode | Endpoint       | Beschreibung                   |
| ------- | -------------- | ------------------------------ |
| GET     | `/me`          | Eigenes Profil anzeigen        |
| PUT     | `/me`          | Benutzername und E-Mail ändern |
| PUT     | `/me/password` | Passwort ändern                |
| DELETE  | `/me`          | Benutzerkonto löschen          |
| GET     | `/me/pokemons` | Eigene Pokémon anzeigen        |

#### Pokémon
| Methode | Endpoint         | Beschreibung                      |
| ------- | ---------------- | --------------------------------- |
| POST    | `/pokemons`      | Pokémon erstellen                 |
| PUT     | `/pokemons/{id}` | Pokémon bearbeiten (nur Besitzer) |
| DELETE  | `/pokemons/{id}` | Pokémon löschen (nur Besitzer)    |

## 🔑 Authentifizierung (JWT)

Geschützte Endpunkte benötigen folgenden Header:

Authorization: Bearer <JWT_TOKEN>

Der Token wird beim Login oder bei der Registrierung generiert.

## 🧪 Tests

- Manuelle API-Tests mit Postman

- Datenbankprüfung mit DBeaver

- Ownership-Tests (403 Forbidden)

- Sicherheitstests (401 Unauthorized)

## 🔒 Sicherheitsaspekte

- Passwörter werden niemals im Klartext gespeichert

- JWT-Signatur (HS256)

- Stateless Backend (keine Sessions)

- Zugriffskontrolle auf Service-Ebene

- Sichere Löschung abhängiger Daten

## 👤 Autor

- Raoul Tchangou

- Projekt für Lernzwecke und Portfolio

- Status: funktionsfähig und erweiterbar