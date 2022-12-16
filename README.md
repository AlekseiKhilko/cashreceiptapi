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
curl http://127.0.0.1:8080/api/products \
-H 'Content-Type: application/json'

## Получить один элемент. Метод GET
curl http://127.0.0.1:8080/api/products/1 \
-H 'Content-Type: application/json'

## Добавить товар. Метод POST 
Рекомендуется использовать Postman.
Установить Content-Type = application/json

curl -X POST http://127.0.0.1:8080/api/products \
-H 'Content-Type: application/json' \
-d '{"name":"Test product", "price":20.0, "promo":false}'



## Изменить товар. Метод PUT

curl -X PUT  http://127.0.0.1:8080/api/products/1 \
-H 'Content-Type: application/json' \
-d '{"name":"Updated product", "price":20.0, "promo":true}'


## Удалить. Метод DELETE
http://127.0.0.1:8080/api/products/{id}

curl -X DELETE http://127.0.0.1:8080/api/products/1
-H "Accept: application/json"


## Скидочные карты (аналогично products)
http://127.0.0.1:8080/api/cards