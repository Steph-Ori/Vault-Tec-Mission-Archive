# Vault-Tec Mission Archive

Vault-Tec Mission Archive is a Fallout-inspired Java web application built with Jakarta Servlets, JSP, Hibernate, and MySQL. The application began as a basic task archive and was expanded into a themed mission management system with mission categories, priority levels, status updates, dashboard counts, structured logging, load testing, and a health check endpoint.

## Project Purpose

This project is to show a more production-ish ready software development workflow. The app includes features that support troubleshooting, testing, monitoring, and incident response.

## Technologies Used

- Java
- Jakarta Servlets
- JSP
- Hibernate ORM
- MySQL
- Apache Tomcat
- Maven
- Apache JMeter
- GitHub

## Application Features

- Create mission records
- Assign mission category
- Assign priority level
- Update mission status
- Search the mission archive
- Purge/delete mission records
- View dashboard counts
- Check application health through `/health`

## Advanced Final Project Features

### 1. Logging and Analysis

The application uses structured JSON-style logging for important user actions, including:

- Mission creation
- Archive searches
- Mission status updates
- Mission purges/deletions

Example log entry:

```json
{"event":"MISSION_CREATED", "title":"Repair water purifier", "category":"Maintenance", "priority":"Urgent"}
