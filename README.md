# 3D Renderer Controller

Tento projekt představuje základní 3D grafický renderer. Umožňuje vykreslování a manipulaci s 3D objekty pomocí klávesnice a myši.

## Funkce

- **Podpora perspektivní a ortogonální projekce**
- **Ovládání kamery (WSAD + myš)**
- **Manipulace s objekty (přesun, rotace, škálování)**
- **Animace otáčející se kostky**
- **Podpora více 3D objektů a křivek (Bezier, Ferguson, Coons)**

## Klávesové zkratky

| Klávesa | Akce |
|--------|------|
| `P` | Perspektivní projekce |
| `O` | Ortogonální projekce |
| `1` – `8` | Aktivace různých objektů |
| `W`, `A`, `S`, `D` | Pohyb kamery |
| `↑`, `↓`, `←`, `→` | Posun objektu |
| `X`, `Y`, `Z` | Rotace objektu kolem os |
| `M` | Zvětšení objektu |
| `N` | Zmenšení objektu |

## Myš

- **Tažení myši**: Rotace kamery
- **Kliknutí**: Uložení poslední pozice pro rotaci

## Třídy objektů

- `Cube`, `Cuboid`, `Pyramid`, `Polygon3D`
- `Axes` – referenční osy
- `Cubid3D` – křivky: Bezier, Ferguson, Coons

## Animace

- Otáčející se kostka (`animatedCube`) s ~30 FPS pomocí `javax.swing.Timer`

## Závislosti

- Java 8+
- Knihovny: `javax.swing`, `java.awt.event`, vlastní `transforms`, `solids`, `renderer`, `rasterizer`

## Spuštění

Zavolej konstruktor `Controller3D` s instancí `Panel`, který obsahuje `RasterImage`.

```java
new Controller3D(panel);
