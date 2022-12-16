# Cash Receipt API (тестовое задание API)


# Запустить docker 

https://hub.docker.com/r/alekseikhilko/cashreceiptapi

# GIT
https://github.com/AlekseiKhilko/cashreceiptapi


# Инициализация базы (товаров и скидочных карт)
http://127.0.0.1:8080/init

## Получить чек

http://127.0.0.1:8080/api/check?items=1-2,2-10,3-10&card=1

Где запятая "," разделяет товары. Символ минуса "-" разделяет id товара и количество. card содержит номер карты.


# API

## Список всех товаров
http://127.0.0.1:8080/api/products

## Метод POST добавляет товар
Рекомендуется использовать Postman
Content-Type = application/json

http://127.0.0.1:8080/api/products

{"name":"Test product", "price":20.0, "promo":false}

## Модифицировать товар PUT

http://127.0.0.1:8080/api/products/{id}

{"id":1, "name":"Updated product", "price":20.0, "promo":true}

## Удалить метод DELETE
http://127.0.0.1:8080/api/products/{id}

curl -X DELETE http://127.0.0.1:8080/api/products/1
-H "Accept: application/json"


## Аналогично для карт
http://127.0.0.1:8080/api/cars