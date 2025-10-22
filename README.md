# 3667_COFFEE_SHOP_PROJECT_B

A small Java Swing GUI that demonstrates the Decorator pattern while letting a user build and price custom coffee drinks.

This README is concise: it explains what the app does and the exact commands to run the GUI from the VSCode integrated terminal (PowerShell).

## What the app does

- Let users choose a base beverage (Espresso, Dark Roast, Decaf, House Blend).
- Choose exactly one size (Small, Medium, Large)  the app applies a size multiplier to the full price.
- Pick any combination of condiments (Milk, Mocha, Soy, Whip, Caramel, Vanilla).
- Show a live, easily readable price breakdown: base price, condiment extras, subtotal, size multiplier, and final total.
- Add composed drinks to a persistent cart (view, edit, remove) and perform a simple checkout.

All pricing is computed by the domain model (the base concrete classes and the condiment decorators) so the GUI does not duplicate price logic.

## Quick run  VSCode (PowerShell)  recommended

I provided a single PowerShell launcher `run-gui.ps1` in the project root that compiles and runs the app using the JDK that `javac` belongs to. From the VS Code integrated terminal (PowerShell), run either:

```powershell
# If your execution policy allows running scripts:
.\run-gui.ps1

# If your environment blocks scripts, run with a temporary bypass:
powershell -ExecutionPolicy Bypass -File .\run-gui.ps1
```

The script will create an `out\` folder (if needed), compile the Java sources, and launch the GUI using the `java.exe` that matches `javac` to avoid classfile-version errors.

## Manual compile & run (PowerShell)

If you prefer to run commands manually, use these steps from the project root:

```powershell
# create output folder
New-Item -ItemType Directory -Path .\out -Force | Out-Null

# compile sources (adjust paths if you refactor into packages)
javac -d out src\main\java\CoffeeShopGUI.java Base\*.java Concrete\*.java Decorator\*.java

# run using the java.exe that matches javac (avoids UnsupportedClassVersionError):
$javacPath = (Get-Command javac).Source
$binDir = Split-Path $javacPath -Parent
$jdkDir = Split-Path $binDir -Parent
$javaExe = Join-Path $jdkDir 'bin\\java.exe'
& $javaExe -cp out CoffeeShopGUI
```

## Persistence

Cart items and the last builder selections are saved using `java.util.prefs.Preferences` (per-user desktop preferences). This keeps the demo simple and cross-platform.

## Project layout (important files)

- `src/main/java/CoffeeShopGUI.java`  Swing GUI and controller logic (builds decorated objects at runtime).
- `Base/Coffee.java`  domain interface for coffee beverages.
- `Concrete/*.java`  concrete base coffee classes (Espresso, DarkRoast, Decaf, HouseBlendCoffee).
- `Decorator/*`  condiment decorator implementations and `CoffeeDecorator` base.
- `run-gui.ps1`  PowerShell helper to compile and run the GUI (recommended on Windows).
- `.gitignore`  keeps build outputs and editor files out of commits.

## How pricing works (short)

Total = (base price + sum(condiment extras))  size multiplier

Default multipliers used by the UI:
- Small = 1.0
- Medium = 1.2
- Large = 1.4

The GUI composes a decorated `Coffee` object at runtime and calls its `cost()` method  this ensures the Decorator pattern is the single source of pricing truth.

## Troubleshooting

- UnsupportedClassVersionError: run the `java.exe` from the same JDK that `javac` belongs to (see the manual run steps above).
- PowerShell script blocked by execution policy: run with `-ExecutionPolicy Bypass -File .\run-gui.ps1` or use the manual compile/run commands.
- Strange characters in dialogs: the UI uses ASCII hyphens and `x` for multipliers to avoid encoding issues.

## Committing & publishing (quick)

Before publishing, ensure `out/` is excluded (it's ignored by `.gitignore`). To commit the current changes locally and push to the existing remote:

```powershell
git add .gitignore README.md run-gui.ps1 src\main\java Base\Concrete\Decorator\
git commit -m "Organize project, wire GUI to domain model, add run script and README"
git push origin main
```
