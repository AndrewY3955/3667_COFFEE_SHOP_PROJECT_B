# Project B
A Java Swing GUI demonstrating the Decorator pattern while letting a user build and price custom coffee drinks.

# What does this project do?
What the app does

- Let users choose a base beverage (Espresso, Dark Roast, Decaf, House Blend).

- Choose exactly one size (Small, Medium, Large). The app applies a size multiplier to the full price. Small is the default size.

- Pick any combination of condiments (Milk, Mocha, Soy, Whip, Caramel, Vanilla).

- Show a live summary: coffee description and total price. Pricing is computed exclusively by the base coffee and condiment decorators.

- The GUI initializes with Small House Blend selected, showing a valid starting price.

- Quick run (VS Code PowerShell recommended)

- The project includes a PowerShell launcher run-gui.ps1:

# If your execution policy allows running scripts:
.\run-gui.ps1

# If scripts are blocked, bypass temporarily:
powershell -ExecutionPolicy Bypass -File .\\run-gui.ps1

# Execution of the program
The script creates an out\ folder if needed, compiles all Java sources, and launches the GUI using the java.exe matching your javac.

Manual compile & run (PowerShell)

Run these commands from the project root if you prefer manual steps:

# Create output folder
New-Item -ItemType Directory -Path .\out -Force | Out-Null

# Compile sources
javac -d out CoffeeShopGUI.java Base\*.java Concrete\*.java Decorator\*.java

# Run GUI with matching java.exe
$javacPath = (Get-Command javac).Source
$binDir = Split-Path $javacPath -Parent
$jdkDir = Split-Path $binDir -Parent
$javaExe = Join-Path $jdkDir 'bin\\java.exe'
& $javaExe -cp out CoffeeShopGUI


# Project layout

CoffeeShopGUI.java – Swing GUI and controller logic, builds decorated Coffee objects at runtime.

Base/Coffee.java – domain interface for beverages.

Concrete/*.java – concrete coffee classes (Espresso, DarkRoast, Decaf, HouseBlend).

Decorator/* – condiment decorators and CondimentDecorator base.

run-gui.ps1 – PowerShell helper to compile and run the GUI.

.gitignore – keeps build outputs and editor files out of commits.

# How pricing works

Total = ((base price × size multiplier) + (sum of all condiment extras × size multiplier))

Size multipliers:

Small = 1.0 (default)

Medium = 1.2

Large = 1.4

The GUI dynamically composes a decorated Coffee object and calls its cost(size) method. The Decorator pattern ensures this formula is applied consistently for both base coffee and condiments.

# Troubleshooting

UnsupportedClassVersionError: Run the java.exe from the same JDK as javac.

PowerShell script blocked: Use -ExecutionPolicy Bypass -File .\\run-gui.ps1 or manual compile/run steps.

Strange characters in dialogs: UI uses ASCII hyphens and x for multipliers to avoid encoding issues.

# Committing & publishing

Ensure out/ is ignored. Example to commit and push:

git add .gitignore README.md run-gui.ps1 Base\Concrete\Decorator\CoffeeShopGUI.java
git commit -m "Update GUI: default Small HouseBlend, decorator pricing, run script, README"
git push origin main
