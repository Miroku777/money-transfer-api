## Money Transfer Service
### Описание:
REST-сервис для: 
1. перевода денег с валидациями и потокобезопасностью от текущего вошедшего пользователя - целевому;
2. пользователь может менять только собственные данные: телефон, email;
3. поиска пользователей с фильтрами;
4. начислением процентов на баланс каждого клиента +10% но не более 207% от начального депозита;
5. и с JWT-аутентификацией по email+password, либо по phone+password.

### Технологии: 
Java 11, Spring Boot 2.7.18, PostgreSQL, Redis, Maven, JWT, Docker.

### Тестирование
```bash
.\mvnw.cmd test
```
![Tests](https://github.com/Miroku777/money-transfer-api/actions/workflows/test.yml/badge.svg)

### Запуск:
```bash
git clone https://github.com/Miroku777/money-transfer-api.git
cd money-transfer-api
.\mvnw.cmd clean package -DskipTests
docker-compose up -d
.\mvnw.cmd spring-boot:run
```

Swagger - http://localhost:8080/swagger-ui/index.html

Ссылка на ТЗ - https://docs.google.com/document/d/1CFoy4TBvHQ01gYuotUfdUjl4afF7QW_k/edit

В начальном тз нет поля initialBalance - добавил его для логики расчёта джобы до лимита 207% от его значения