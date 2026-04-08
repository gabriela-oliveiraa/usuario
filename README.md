# 👤 Usuario — Microsserviço de Gerenciamento de Usuários

Microsserviço responsável pelo cadastro, autenticação e gerenciamento de usuários da plataforma **Agendador de Tarefas**. Desenvolvido com **Spring Boot 4**, **Java 21**, **PostgreSQL** e suporte a autenticação via **JWT**, com deploy containerizado via Docker.

---

## 🚀 Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 4.0.2 |
| Spring Security | — |
| Spring Data JPA | — |
| JWT (jjwt) | 0.13.0 |
| Spring Cloud OpenFeign | 2025.1.0 |
| SpringDoc OpenAPI (Swagger) | 3.0.2 |
| Lombok | — |
| PostgreSQL | latest |
| Docker / Docker Compose | — |
| Gradle | — |

---

## 📁 Estrutura do Projeto

```
usuario/
├── .github/workflows/        # Pipelines CI/CD (GitHub Actions)
├── gradle/wrapper/           # Wrapper do Gradle
├── src/main/                 # Código-fonte principal
│   ├── java/                 # Classes Java (controllers, services, repositories, etc.)
│   └── resources/            # Configurações (application.properties/yml)
├── Dockerfile                # Imagem Docker da aplicação
├── docker-compose.yml        # Orquestração: app + banco PostgreSQL
├── build.gradle              # Dependências e configurações de build
└── settings.gradle           # Configurações do projeto Gradle
```

---

## ⚙️ Configuração e Execução

### Pré-requisitos

- Java 21+
- Docker e Docker Compose
- Gradle (ou use o wrapper `./gradlew`)

### Executando com Docker Compose

```bash
# Clone o repositório
git clone https://github.com/gabriela-oliveiraa/usuario.git
cd usuario

# Suba a aplicação e o banco de dados
docker-compose up --build
```

A aplicação estará disponível em: `http://localhost:8080`

### Executando localmente (sem Docker)

> Configure o banco de dados PostgreSQL localmente e ajuste as variáveis de ambiente ou o `application.properties`.

```bash
# Build do projeto
./gradlew build

# Execução
./gradlew bootRun
```

---

## 🐳 Docker Compose

O `docker-compose.yml` sobe dois serviços:

- **`app`** — A aplicação Spring Boot na porta `8080`
- **`db`** — Banco de dados PostgreSQL na porta `5432`

Variáveis de ambiente configuradas:

| Variável | Valor |
|---|---|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://db:5432/db_usuario` |
| `SPRING_DATASOURCE_USERNAME` | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | `admin123` |
| `POSTGRES_DB` | `db_usuario` |
| `POSTGRES_USER` | `postgres` |
| `POSTGRES_PASSWORD` | `admin123` |

---

## 📖 Documentação da API

Com a aplicação rodando, acesse a documentação interativa (Swagger UI):

```
http://localhost:8080/swagger-ui.html
```

---

## 🔒 Segurança

A autenticação é baseada em **JWT (JSON Web Token)** com suporte ao Spring Security. Os tokens são gerados no login e devem ser enviados no header `Authorization: Bearer <token>` nas requisições protegidas.

---

## 🔗 Integração com outros serviços

Este microsserviço utiliza **Spring Cloud OpenFeign** para comunicação com outros serviços da plataforma, como o `agendador-tarefas` e o `bff-agendador-tarefas`.

---

## 🧪 Testes

```bash
./gradlew test
```

---

## 📄 Licença

Este projeto foi desenvolvido para fins de estudo e prática com microsserviços em Java/Spring Boot.
