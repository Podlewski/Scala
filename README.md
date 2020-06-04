# Programowanie w języku Scala - projekt

## Autorzy

+ [ZN](https://github.com/Shreq)
+ [Karol Podlewski](https://github.com/Podlewski)

## Opis projektu

W ramach projektu wykonano prosty web scrapper ofert na serwisie OLX. Wyszukuje on oferty, wyświetlając statystyki takie jak:

+ liczba ofert,
+ średnia cen,
+ minimalna cena,
+ maksymalna cena,
+ liczba ofert darmowych,
+ liczba ofert z zamianą.

Poza liczbami, wypisywane są linki do darmowych, oferujących zamianę, najtańszych oraz najdroższych ofert.

Statytyki wyświetlane są dla 3 obszarów poszukiwań: całego kraju, województwa Łódzkiego oraz samej Łodzi.

Dodatkowo, przeszukiwania mogą zostać ograniczone do konkretnych kategorii.

## Uruchamianie projektu

### Bez argumentów

Program wypisze kategorie, wraz z linkami do nich oraz przypisanym numerem, który będzie potrzeby w przypadku chęci wyszukiwania wyników tylko dla danej kategorii.

### Z argumentami

Wszystkie argumenty zostaną zamienione na frazę po której program będzie wyszukiwał ofert na serwisie

### Z argumentami, kiedy pierwszy argument jest liczbą

Wpisana liczba zostanie przypisana kategorii, nie będąc częścią składową samego zapytania.
