# 🚀 Projeto Spring Boot – Configuração Padrão (DEV / PROD)

Este projeto utiliza **Spring Boot + Java 21 + Docker + PostgreSQL**, com uma estrutura pensada para ser **reutilizada em qualquer novo projeto**, separando corretamente os ambientes de **desenvolvimento (DEV)** e **produção (PROD)**.

A ideia é:
- Rodar **banco de dados no Docker**
- Rodar a **API localmente pelo IntelliJ (LOCAL)**
- Ou rodar **tudo no Docker (PROD/DEV)**

---

## 🧱 Estrutura do Projeto

```
myproject/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com.saj.api
│ │ └── resources/
│ │ ├── application.yml
│ │ ├── application-dsv.yml
│ │ └── application-local.yml
│ └── test/
├── docker-compose.dev.yml
├── docker-compose.prod.yml
├── docker-compose.yml
├── .env
├── Dockerfile
├── pom.xml
└── README.md
```

---

## ⚙️ Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker
- Docker Compose
- Maven

---

## 🔐 Arquivo `.env`

As variáveis de ambiente ficam centralizadas no arquivo `.env`.

### Exemplo de `.env`:

```env
SPRING_PROFILES_ACTIVE=local

POSTGRES_HOST=postgres
POSTGRES_PORT=5432
POSTGRES_DB=myprojectdb
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_EXTERNAL_PORT=5432
```

---

## 🧠 Perfis do Spring Boot

O projeto utiliza profiles do Spring para separar configurações.

```
Ambiente	Arquivo
LOCAL	        application-local.yml
DEV	        application-dsv.yml
PROD	        application.yml
```
---

## 🚀 Rodando o Projeto em Desenvolvimento (LOCAL)
✅ Subir apenas o banco de dados no Docker

Recomendado quando a API será rodada pelo IntelliJ.

```
docker compose -f docker-compose.local.yml up -d 
```

Isso irá subir:

### PostgreSQL
- Volume persistente do banco

### Depois:
- Abra o projeto no IntelliJ
- Rode a aplicação normalmente (Run / Debug)

---

## 🔍 Como saber qual profile está ativo

No log da aplicação, você verá algo como:

```
The following 1 profile is active: "dev"
```


##  🐳 Rodando tudo no Docker (DEV)

### Para subir API + Banco via Docker:
```
docker compose -f docker-compose.dev.yml up -d
```

### Para parar:
```
docker compose -f docker-compose.dev.yml down
```
---

## 📦 Docker Compose por Ambiente

### docker-compose.local.yml

- Sobe apenas dependências (ex: PostgreSQL)
- Usado para desenvolvimento local
- API roda fora do Docker

### docker-compose.dev.yml

- Sobe banco + API
- Usado para ambiente desenvolvimento ou testes completos

### docker-compose.prod.yml

- Sobe somente API
- Usado para ambiente de produção banco de dados em outro servidor

## 🧰 Comandos Úteis

### Subir containers
```
docker compose -f docker-compose.dev.yml up -d
```

### Parar containers
```
docker compose -f docker-compose.dev.yml down
```

###  Ver logs
```
docker logs -f nome-do-container
```

### Listar containers ativos
```
docker ps
```
