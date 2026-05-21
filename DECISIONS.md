# Decyzje projektowe

## Promocje

Uzyty zostal wzorzec Strategy: `Promotion` jest interfejsem, a kazda promocja ma osobna klase. Dzieki temu dodanie nowej promocji wymaga dopisania nowej klasy, bez zmieniania `Cart`.

## Kolekcje i sortowanie

Finalna wersja dziala na `List<Product>`, czyli na interfejsie kolekcji, a nie na tablicach. Sortowanie jest realizowane przez `Comparator<Product>`. Domyslnie koszyk sortuje malejaco po cenie, a potem alfabetycznie po nazwie, ale comparator mozna podmienic w trakcie dzialania programu.

## Wyszukiwanie produktow

Najtanszy i najdrozszy produkt sa wyszukiwane przez przejscie po liscie i porownanie cen. Dla `n` najtanszych albo najdrozszych produktow lista jest sortowana po cenie, a potem pobierane jest pierwsze `n` elementow.

## Enkapsulacja

`Product` jest niemutowalny: wszystkie pola sa `final`, a promocje tworza nowy produkt z inna `discountPrice`. To upraszcza ponowne nakladanie promocji i chroni oryginalne ceny przed przypadkowa zmiana.

## Sytuacje brzegowe

Koszyk ignoruje `null` przy dodawaniu produktu i promocji. Promocje przyjmuja pusta lub `null` liste bez rzucania wyjatkow. Pusty koszyk zwraca sume `0`, a wyszukiwanie najtanszego/najdrozszego produktu zwraca `null`.
