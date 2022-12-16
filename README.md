# Cash Receipt API (тестовое задание)


# Запустить docker 

https://hub.docker.com/r/alekseikhilko/cashreceiptapi

# GIT
git clone https://github.com/AlekseiKhilko/cashreceiptapi

cd cashreceiptapi 

docker-compose up

 Инициализация базы (товаров и скидочных карт)
http://127.0.0.1:8080/init

## Получить чек

http://127.0.0.1:8080/api/check?items=1-2,2-10,3-10&card=1

Где запятая "," разделяет товары. Символ минуса "-" разделяет id товара и количество. card содержит номер карты.


# API

## Список всех товаров
http://127.0.0.1:8080/api/products

## Получить один элемент. Метод GET
http://127.0.0.1:8080/api/products/{id}

## Добавляет товар. Метод POST 
Рекомендуется использовать Postman.
Использовать Content-Type = application/json

http://127.0.0.1:8080/api/products

{"name":"Test product", "price":20.0, "promo":false}

## Модифицировать товар. Метод PUT

http://127.0.0.1:8080/api/products/{id}

{"id":1, "name":"Updated product", "price":20.0, "promo":true}

## Удалить. Метод DELETE
http://127.0.0.1:8080/api/products/{id}

curl -X DELETE http://127.0.0.1:8080/api/products/1
-H "Accept: application/json"


## Скидочные карты (аналогично products)
http://127.0.0.1:8080/api/cards