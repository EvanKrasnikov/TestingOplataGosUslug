# TestingOplataGosUslug
Тестирование https://oplatagosuslug.ru

# Как запускать
```sh
./mvnw clean test
./mvnw allure:serve
```

# Todo
- Поправить тесты для формы "Поиск по документам"
- Добавить тесты для формы "Оплата начислений по штрафу"
- Обновить Readme

---

# Баг или фича?
#### Описание
При тестировании формы на [странице](https://oplatagosuslug.ru/shtrafy_gibdd/) обнаружилось поведение:
1. Если искать по Свид. о регистрации ТС, то находятся 20 начислений.
2. Если по водительскому удостоверению - 1.
3. Если по Свид. о регистрации ТС и водительскому удостоверению - 209

Шаги описаны ниже
[![Alt text](.readme/broken_header.png)]()

#### Дано
Пользователь находится на странице "Проверка и оплата штрафов ГИБДД"
И вкладке "Поиск по документам"

#### Если
1. Ввести в форму следующие данные
    - Свид. о регистрации ТС - 7813690343
    - Оплата водительское удостоверению - 11 11 111111
2. И нажать на кнопку "Найти штрафы"

#### То
1. Заголовок загруженной страницы - "Проверка и оплата штрафов ГИБДД"
2. Найдено начислений - 209 (сомнительно)

#### Ожидание
1. Заголовок загруженной страницы - "Оплата начислений"
2. Адекватное количество начислений
