# url_shortcut
Это приложение для обеспечения безопасности пользователей, все ссылки на сайте заменяются ссылками на наш сервис.

[//]: # (## Техническое задание на проект содержит такие требования:)

[//]: # (1. Регистрация сайта.)

[//]: # (2. Авторизация.)

[//]: # (3. Регистрация URL.)

[//]: # (4. Переадресация. Выполняется без авторизации.)

[//]: # (5. Статистика.)

## Используемые технологии:
![java](https://img.shields.io/badge/Java--17-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot--2.7.3-F2F4F9?style=for-the-badge&logo=spring-boot)
![Bootstrap](https://img.shields.io/badge/Bootstrap--5.2.2-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)
![PostgresSQL](https://img.shields.io/badge/PostgreSQL--14-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-1.18.24-green?style=for-the-badge&logo=lombok&logoColor=white)
![Liquibase](https://img.shields.io/badge/Liquibase-4.9.1-red?style=for-the-badge&logo=liquibase&logoColor=white)

Перед запуском установите:
- PostgreSQL 14
- Java 17
- Apache Maven 3.x

## Запуск приложения

1. Создайте базу данных url_shortcut:
```sql
create database url_shortcut;
```

2. Запуск приложения производится с использованием maven.
   Перейдите в корневой каталог проекта и в командной строке
   выполните команды:
```
    mvn clean install
    mvn spring-boot:run
```
### Описание:
1. Регистрация сайта.
Сервисом могут пользоваться разные сайты. Каждому сайту выдается пару пароль и логин.
Чтобы зарегистрировать сайт в систему нужно отправить запрос.
````
POST /registration
````
C телом JSON объекта:
````
{
  "site" : "example.com"
}
````
Ответ от сервера придет в виде:
````
{registration : true/false, login: УНИКАЛЬНЫЙ_КОД, password : УНИКАЛЬНЫЙ_КОД}
````
Флаг registration указывает, что регистрация выполнена или нет, отрицательный ответ обозначает, 
что такой сайт уже есть в системе.
![Reg page](img/1Registr.JPG)
2. Авторизация.
   Авторизацию выполнена через JWT. Пользователь отправляет POST запрос с login и password и получает ключ - токен.
Этот ключ в дальнейших запросах пользователь отправляет в запросе в блоке HEAD. 
Выполняем запрос
````
POST /login
````
с телом JSON объекта:
````
{"login" : УНИКАЛЬНЫЙ_КОД, "password": УНИКАЛЬНЫЙ_КОД}
````
В ответе сервера в headers будет содержаться токен:
````
Authorization: Bearer e25d31c5-db66-4cf2-85d4-8faa8c544ad6
````
![Login page](img/2login.JPG)
3. Регистрация URL.
После того как пользователь зарегистрировал свой сайт, он может отправлять на сервис ссылки и 
получать короткие преобразованные ссылки.
````
POST /convert
````
с телом JSON объекта:
````
{"url": "https://job4j.ru/profile/exercise/106/task-view/532"}
````
Ответ от сервера:
````
{"code" : УНИКАЛЬНЫЙ_КОД}
````
![Convert page](img/3convert.JPG)
4. Переадресация. Выполняется без авторизации.
   В запросе отправляется код, в ответе возвращается ассоциированная ссылка.
````
GET /redirect/УНИКАЛЬНЫЙ_КОД
````
Ответ от сервера в заголовке:
````
HTTP CODE - 302 REDIRECT URL
````
![Redirect page](img/4Redirect.JPG)
5. Статистика.
В сервисе считается количество вызовов каждого адреса.
По сайту можно получить статистку всех адресов и количество вызовов этого адреса.
````
GET /statistic
````
Ответ от сервера в виде JSON:
![Statistic page](img/5Stat.JPG)

Связаться со мной можно по электронной почте a_esipov_it@list.ru
или в телеграм  @Alex46volokno


<div id="socials" align="center">
    <!-- <a href="linkedin-url">
    <img src="https://img.shields.io/badge/LinkedIn-blue?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn"/>
  </a> -->

  <a href="https://t.me/alex46volokno">
    <img src="https://img.shields.io/badge/Telegram-blue?style=for-the-badge&logo=telegram&logoColor=white" alt="Telegram"/>
  </a>
</div>








