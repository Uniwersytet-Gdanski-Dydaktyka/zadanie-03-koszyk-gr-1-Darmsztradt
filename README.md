# Koszyk Codex

Prosta wersja zadania "Koszyk internetowy" w Javie.

Zakres:
- wyszukiwanie najtanszych i najdrozszych produktow,
- sortowanie domyslne i przez podany `Comparator<Product>`,
- liczenie sumy cen,
- promocje: 5% powyzej 300 zl, 2+1, firmowy kubek powyzej 200 zl, kupon 30% na produkt,
- dodawanie i usuwanie promocji przez interfejs `Promotion`,
- testy jednostkowe,
- `DECISIONS.md`,
- diagram PlantUML w `diagram.puml`.

Punkt ekstra o szukaniu najlepszej kolejnosci promocji nie jest zaimplementowany.

Uruchomienie testow:

```powershell
.\gradlew.bat test
```
