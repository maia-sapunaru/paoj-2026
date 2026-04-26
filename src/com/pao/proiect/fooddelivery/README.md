# Platformă Food Delivery – PAO Java 2026

## Descriere proiect

Aplicația simulează o platformă de livrare mâncare de tip Glovo / Tazz / Bolt Food.
Utilizatorii pot comanda produse de la restaurante, iar comenzile sunt preluate de șoferi pentru livrare.

Proiectul implementează concepte OOP în Java:

* moștenire
* abstractizare
* encapsulare
* polimorfism
* colecții Java
* excepții custom
* servicii Singleton

---

## Funcționalități implementate (10 acțiuni)

1. Adăugare restaurant nou
2. Adăugare client nou
3. Adăugare șofer nou
4. Adăugare produs în meniu
5. Listare restaurante disponibile
6. Căutare produs după nume
7. Plasare comandă
8. Atribuire șofer unei comenzi
9. Vizualizare comenzi client
10. Afișare comenzi active sortate după valoare

---

## Obiecte modelate în sistem

1. User
2. Client
3. Driver
4. Restaurant
5. MenuItem
6. Order
7. OrderItem
8. DeliveryAddress
9. RestaurantCategory
10. OrderStatus

---

## Structură proiect

src/com/pao/proiect/fooddelivery/

* model/ → clase domeniu
* service/ → servicii Singleton
* exception/ → excepții custom
* Main.java → demonstrație aplicație

---

## Relații OOP

* `User` (abstract)

    * `Client`
    * `Driver`

---

## Colecții utilizate

* `List<MenuItem>`
* `Map<Integer, Restaurant>`
* `TreeSet<Order>`

---

## Excepții custom

* `NotFoundException`
* `MenuItemUnavailableException`

---

## Rulare

Se rulează clasa:

Main.java

---
