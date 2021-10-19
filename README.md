# Задача

### Условие

Есть строка
insert into db1.table1(int1, float2, string3,date4) select int1, float2, string3,date4 from db2.table2

У вас запущено 2 приложения, в них вы передаете эту строку.
Одно приложение записывает данные в db1.table1, другое читает данные из db2.table2.

Обе БД реляционные. 
Надо реализовать функционал двух приложений пишущего и читающего, чтоб все данные перелить из  таблицы table2 в таблицу table1

### Инструменты
* БД - h2 (in-memory db)
* Коммуникация между приложениями - RabbitMQ
* Инфрастуктура - Spring Boot + Maven

### Решение
Реализовал проект с сервисами по отправке и получению сообщений. 
Это 2 консольных приложения на базе одного проекта с разными ролями при запуске.

Роли разграничиваются с помощью spring профайлов.

Отправитель читает строки из таблицы db2.table2, конвертирует их в массив байтов и отправляет сообщения в очередь и завершает работу.

Получатель читает сообщения из очереди, конвертирует их в строки для бд и записывает в db1.table1 и ждёт новые сообщения. Для завершения работы получателя нужно выйти из приложения.

### Запуск
1. git clone https://github.com/alex-dylda/Sber-task.git
2. cd Sber-task
3. mvn clean install
* Отправитель: java -jar target/task-0.0.1.jar --spring.profiles.active=sender
* Получатель:  java -jar target/task-0.0.1.jar --spring.profiles.active=receiver

### Проверить данные в бд
* БД источник: [localhost:8082/h2](http://localhost:8082/h2)   (JDBC url: jdbc:h2:mem:testdb)

* БД таргет: [localhost:8080/h2](http://localhost:8080/h2)   (JDBC url: jdbc:h2:mem:testdb2)