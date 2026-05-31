Технологии: Java 11, Spring Boot 2.7.18, PostgreSQL, Redis
```bash
docker run -d --name postgresMoneyTransfer -p 5432:5432 -e POSTGRES_PASSWORD=password -e POSTGRES_DB=money_transfer postgres:15
docker run -d --name redisMoneyTransfer -p 6379:6379 redis:7-alpine
```
Ссылка на ТЗ - https://docs.google.com/document/d/1CFoy4TBvHQ01gYuotUfdUjl4afF7QW_k/edit

Swagger - http://localhost:8080/swagger-ui/index.html

В начальном тз нет поля initialBalance - добавил его для логики расчёта джобы до лимита 207% от его значения
Для интеграционных тестов - `spring.cache.type=redis`

По заданию для тестов не нужно использовать миграции - поэтому делал их ручными файл [init.sql](./init.sql)