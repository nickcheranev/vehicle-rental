#Описание системы

Крупная международная организация предоставляет услуги проката транспортных средств (ТС). Предоставляемые на прокат транспортные средства включаю следующие типы (виды) техники:
1. Автомобили
2. Мотоциклы
3. Скутеры.

Представлены разные модели разных марок.

Точки проката расположены в нескольких странах мира. Можно взять транспортное средство в прокат в одной точке и сдать в другой.

##Справочники:

1. Точка проката (наименование - текст, координаты - широта, долгота)
2. Тип техники (наименование: автомобиль, мотоцикл, скутер)
3. Марки (модель) транспортных средств (Тип техники - справочник, наименование - текст)
4. Арендатор (ФИО - текст, удостоверение - текст)
5. Статус ТС (перечисление: в парке, выдано)
6. Трекер (идентификатор в системе трекинга - текст)

##Реестры: 

1. Транспортные средства в эсплуатации (Марка - справочник, Регистрационный гос. номер - текст, Трекер - справочник)
2. Транспортное средство в аренде(Транспортное средство в эксплуатации - реестр, Точка проката выдачи ТС - справочник, Время выдачи - дата/время, Время возврата - дата/время)
3. История перемещения ТС (Транспортное средство в аренде - реестр, Местоположение ТС - координаты долгота/широта, Время записи - дата/время)

##Задачи системы:

1. Выдать Заказчику ТС.
2. Периодически отслеживать местоположение ТС по информации от датчика GPS.
3. Принять от заказчика ТС по окончанию аренды.
3. Вести историю перемещения ТС взятых в аренду.
4. Обеспечивать ввод в эксплуатацию новых ТС.

##Отчеты:

1. Отчет "Какова средняя продолжительность аренды ТС каждого типа и каждой марки по каждой Точке проката"
2. Отчет по истории эксплуатации в разрезах:
 - тип и марка ТС;
 - дата взятия в прокат, дата возврата в прокат;
 - ФИО арендатора; 
 - регистрационный гос. номер;

##Требования к системе:

1. Ваимодействие с системой должно быть реализовано через универсальный API, для использования в различных типах клиентских приложений.
2. Предусмотреть автоматизированное тестирование.
3. Реализовать инструмент автоматического развертывания серверной инфраструктуры, состоящей из 
- балансировщика запросов;
- n экземпляров приложений (количество экземпляров задается при инициализации) на основе Docker-контейнеров;
- системы хранения данных.

##Реализация

Использованы компоненты:

1. **Приложение:** Веб-сервер на Spring Boot. Spring Boot позволяет с минимальными затратами создать веб-приложение со встроенным сервером приложений (используется контейнер сервлетов Tomcat), встроенным сервером БД (H2 Database). Отлично подходит как для демонстрационных проектов, так и для промышленных решений.
2. **Хранилище данных:** H2 Database (встроенная, для проекта использована реализация in memory)
3. **ORM:** JPA, Hibernate, реализация на JpaRepository
4. **Frontend:** фреймворк Vaadin, позволяет создавать SPA на Java, имеет интеграцию со Spring Boot.
5. **API для сторонних клиентов, интеграция с внешними системами:** REST Веб-сервис.

##Описание классов проекта

Модели ORM сущностей расположены в пакете ru.cheranev.rental.domain, репозитории расположены в пакете ru.cheranev.rental.jpa. Представлены следующими классами:
1. **Customer** - Заказчик (арендатор), поля Наименование (ФИО), репозиторий CustomerRepository; 
2. **RentalPoint** - Точка проката, поля: Наименование, Расположение (географические координаты в текстовом представлении). Репозиторий RentalPointRepository;
3. **Tracker** - GPS трекер, поля: Идентификатор (уникальный идентификатор во внешней системе, содержится в сигнале трекера). Репозиторий TrackerRepository.
4. **VehicleType** - Тип транспортного средства, поля: Наименование (автомобиль, мотоцикл, скутер). Репозиторий VehicleTypeRepository.
5. **VehicleModel** - Модель транспортного средства, поля: Наименование, Тип ТС (). Репозиторий VehicleModelRepository.
6. **Vehicle** - Конкретное транспортное средство, поля: Гос. номер, Модель ТС, Трекер. Репозиторий VehicleRepository.
7. **Status** - Статусы ТС в аренде: **ТС находится в пункте проката** - PARKED, **ТС выдано в аренду** - RENTED, **ТС передано в другой пункт проката** - CLOSED 
8. **VehicleRented** - Запись о выдаче в прокат ТС, поля: Заказчик, ТС, Пункт проката выдачи, Пункт проката приема, Дата/Время выдачи, Дата/Время приема, Статус ТС. Репозиторий VehicleRentHistoryRepository.
9. **VehicleRentHistory** - История перемещения ТС, поля: ТС в аренде, Дата/Время записи, Координаты (переданы GPS трекером). Репозиторий TrackerRepository.
10. **ReportAverageTime** - Отчет о средней продолжительнсти аренды ТС, поля Точка проката, Тип ТС, Модель ТС, Среднее время проката:  Построен на витрине данных AVG_TIME_VIEW. Репозиторий ReportAverageTimeRepository.
11. **ReportRentalHistory** - Отчет по истории эксплуатации ТС, поля: Тип ТС, Модель, Гос. номер, Заказчик, Дата/Время начала аренды, Дата/Время окончания аренды. Построен на витрине данных RENTAL_HISTORY_VIEW. Репозиторий ReportRentalHistoryRepository.

Сервисы реализации бизнес-логики размещены в пакете ru.cheranev.rental.service
1. **RentalService** - сервис основных бизнес-операций. Содержит методы:

    **pushToRent** - Выдать ТС в аренду. Параметры: ТС, Заказчик. Реализует бизнес-логику:
    - Проверить доступность конкретного ТС для выдачи (со статусом PARKED)
    - Создать запись об аренде ТС. Далее для записи:
    - Установить Заказчика
    - Установить Точку проката выдачи
    - Установить Время выдачи
    - Установить статус "ТС выдано в аренду" (RENTED)
    - Сохранить запись
    - Для предыдущей записи аренды данного ТС установить статус "ТС передано в другой пункт проката" (CLOSED)
    - Создать запись в истории перемещения ТС с координатами Точки выдачи и временем выдачи
    
    **pullFromRent** - Принять ТС из аренды. Параметры: ТС, Пункт проката прибытия ТС. Реализует бизнес-логику:
    - Найти запись о прокате ТС. Далее для записи:
    - Установить Пункт проката прибытия
    - Установить время прибытия
    - Установить статус "ТС находится в пункте проката" (PARKED)
    - Добавить в историю перемещения ТС запись о размещении ТС в Точке проката прибытия.
    
     **findAllVehicleRentedByStatus** - Получить все ТС с определенным статусом
     
     **findAllCustomers** - Получить всех зарегистрированных арендаторов
     
     **findAllRentalPoints** - Получить все пункты проката{

2. **DemoService** - сервис заполнения БД данными для демонстрации. Запускается при старте приложения. Сервис генерирует элементы справочников и формирует записи о ТС выданных/принятых в/из аренды. Также генерируется содержмое реестра перемещения ТС.

REST Веб-сервис для взаимодействия с клиентами различных типов и интеграции с внешними системами. Размещен в пакете ru.cheranev.rental.rest и имеет классы
1. ModelResource - CRUD для взаимодействия со справочником моделей ТС.
2. RentalPointResource - CRUD для взаимодействия со справочником Точки проката.
3. TrackerResource - CRUD для взаимодействия со справочником GPS Трекеры.
4. VehicleResource - CRUD для взаимодействия со справочником ТC.
5. RestRentalService - API бизнес-операций системы (Выдача, прием и пр.).

Интерфейс пользователя реализован на Vaadin. Приложение имеет страницы:
1. О программе.
2. Страница выдачи ТС в аренду.
3. Страница приема ТС из аренды.
3. Отчет "Какова средняя продолжительность аренды ТС каждого типа и каждой марки по каждой Точке проката"
4. Отчет по истории эксплуатации в разрезах:
    - тип и марка ТС;
    - дата взятия в прокат, дата возврата в прокат;
    - ФИО арендатора; 
    - регистрационный гос. номер;
    
    Для полей отчета имеется возможность фильтрации. Для каждой записи можно просмотреть историю перемещения ТС (последние 10 записей) выбрав строку в таблице.
    
##Дополнительные возможности проекта:
- проект может быть помещен в Docker контейнер средствами maven plugin dockerfile командой:
   
    mvn dockerfile:build     
      
- запущен из докер контейнера командой

    docker run -p 8080:8080 -t ncheranev/vehicle-rental:0.0.1-SNAPSHOT