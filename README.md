# LMS — сервис пользователей с QR-кодами

Небольшой REST-сервис на Spring Boot для учёта пользователей и их QR-кодов. QR-код используется как одноразовый «пропуск»: после входа по нему старый код удаляется и пользователю создаётся новый.

## Стек

- Java 17
- Spring Boot 4.1.2 (Spring Web MVC, Spring Data JPA, Bean Validation)
- PostgreSQL 16
- Lombok
- Maven (в репозитории есть `mvnw` / `mvnw.cmd`)
- Docker Compose (для локального запуска базы данных)

## Структура проекта

```
src/main/java/com/example/demo/
├── controller/   # REST-контроллеры и обработчик ошибок
├── dto/          # DTO и маппер
├── entity/       # JPA-сущности User и Qr
├── exception/    # кастомные исключения и формат ошибки
├── repository/   # Spring Data репозитории
└── service/      # бизнес-логика
```

## Требования

- JDK 17 или новее
- Docker и Docker Compose (для PostgreSQL)
- Доступный порт `8080` (приложение) и `5432` (база данных)

## Запуск

### 1. Поднимите базу данных

```bash
docker compose up -d
```

Compose поднимает PostgreSQL с параметрами:

| Параметр | Значение |
| --- | --- |
| База данных | `lms` |
| Пользователь / пароль | `postgres` / `postgres` |
| Порт | `5432` |
| Образ | `postgres:16` |

При первом запуске выполняется `init.sql`: создаются таблицы `users` и `users_qr` и добавляются три тестовых пользователя с QR-кодами.

> Том для данных в `docker-compose.yml` закомментирован, поэтому при удалении контейнера данные будут потеряны.

### 2. Запустите приложение

Windows:

```bat
mvnw.cmd spring-boot:run
```

Linux / macOS:

```bash
./mvnw spring-boot:run
```

Либо соберите JAR и запустите его:

```bash
./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

Настройки подключения к БД находятся в `src/main/resources/application.yaml`. Там же включены вывод SQL (`show-sql: true`) и `hibernate.ddl-auto: update`.

## API

Базовый URL: `http://localhost:8080`

### Пользователи

| Метод | Эндпоинт | Описание | Успешный ответ |
| --- | --- | --- | --- |
| `POST` | `/api/v1/users` | Создать пользователя (вместе с QR-кодом) | `201 Created`, `ReturnUserDto` |
| `GET` | `/api/v1/users/{id}` | Получить пользователя по ID | `200 OK`, `ReturnUserDto` |
| `PUT` | `/api/v1/users/{id}` | Обновить пользователя | `201 Created`, `ReturnUserDto` |
| `DELETE` | `/api/v1/users/{id}` | Удалить пользователя | `204 No Content` |
| `POST` | `/api/v1/users/enter/{qr}` | Войти по QR-коду: старый код удаляется, создаётся новый | `201 Created`, `ReturnUserDto` |

Тело запроса для `POST` и `PUT` (`UserDto`):

```json
{
  "lastName": "Иванов",
  "firstName": "Иван",
  "middleName": "Иванович"
}
```

Поля `lastName`, `firstName`, `middleName` обязательны (`@NotBlank`) и должны содержать от 2 до 20 символов (проверка применяется при создании).

Пример ответа (`ReturnUserDto`):

```json
{
  "id": 4,
  "lastName": "Иванов",
  "firstName": "Иван",
  "middleName": "Иванович"
}
```

### QR-коды

| Метод | Эндпоинт | Описание | Успешный ответ |
| --- | --- | --- | --- |
| `POST` | `/api/v1/qrs/{id}` | Создать новый QR-код для пользователя | `201 Created`, `ReturnUserDto` |
| `DELETE` | `/api/v1/qrs/{qr}` | Удалить QR-код по UUID | `204 No Content` |

`{id}` — идентификатор пользователя, `{qr}` — UUID QR-кода.

Успешные ответы `POST` содержат заголовок `Location` со ссылкой на пользователя и технический заголовок `Custom-Header: Value`.

### Ошибки

Если пользователь или QR-код не найден, возвращается `404 Not Found`:

```json
{
  "message": "Пользователь с ID 42 не найден"
}
```

## Примеры запросов

```bash
# Создать пользователя
curl -X POST http://localhost:8080/api/v1/users \
  -H 'Content-Type: application/json' \
  -d '{"lastName":"Иванов","firstName":"Иван","middleName":"Иванович"}'

# Получить пользователя
curl http://localhost:8080/api/v1/users/1

# Войти по QR-коду (UUID взять из таблицы users_qr)
curl -X POST http://localhost:8080/api/v1/users/enter/00000000-0000-0000-0000-000000000001

# Создать и удалить QR-код
curl -X POST http://localhost:8080/api/v1/qrs/1
curl -X DELETE http://localhost:8080/api/v1/qrs/00000000-0000-0000-0000-000000000001
```