## Stock Web Application
This is an application where you can sell/buy stocks.

This project made of 3 separate Docker containers that holds:

- Java backend (Spring Boot)
- Frontend (Angular)
- PostgreSQL database

The entry point for a user is a website which is available under the address: http://localhost:4200/

---
## Technologies 

- Java 17
- Angular 15
- PostgreSQL
- Docker
- Spring (Boot, Security, Web, Data, JPA)
- Hibernate
- JWT
- Lombok
---

## Prerequisites

### 1. Docker

In order to run this application you need to install two tools: **Docker** & **Docker Compose**.

Instructions how to install **Docker** on [Ubuntu](https://docs.docker.com/install/linux/docker-ce/ubuntu/), [Windows](https://docs.docker.com/docker-for-windows/install/) , [Mac](https://docs.docker.com/docker-for-mac/install/) .

**Dosker Compose** is already included in installation packs for *Windows* and *Mac*, so only Ubuntu users needs to follow [this instructions](https://docs.docker.com/compose/install/) .

### 2. [Property configuration](StocksApplicationBackend%2Fsrc%2Fmain%2Fresources%2Fapplication.properties)
This REST API interacts with [Alpha Vantage](https://www.alphavantage.co/) API. You should get your token by this [link](https://www.alphavantage.co/support/#api-key) and input in property *api.key* like this:
```
api.key=A3BNL15D3N8LSKD
```
Stocks which you want to retrieve you should specify in [application.properties](StocksApplicationBackend%2Fsrc%2Fmain%2Fresources%2Fapplication.properties) stock.symbols using format:
{Company: 'Symbol'}, for example:

```
{Apple:'AAPL',Tesla:'TSLA'}
```
---

### How to run it?

You can run entire application using a single command in a terminal:

```
docker-compose up
```

If you want to stop this app you can use this command:

```
docker-compose down
```





