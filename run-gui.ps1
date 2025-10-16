# Compile and run the CoffeeShop GUI (PowerShell)
# Usage: .\run-gui.ps1

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Definition
Push-Location $projectRoot

# Ensure output directory
$OutDir = Join-Path $projectRoot 'out'
if (-not (Test-Path $OutDir)) { New-Item -ItemType Directory -Path $OutDir | Out-Null }

Write-Output "Compiling sources..."
javac -d out src\main\java\CoffeeShopGUI.java src\main\java\Base\*.java src\main\java\Concrete\*.java src\main\java\Decorator\*.java
if ($LASTEXITCODE -ne 0) { Write-Error "Compilation failed."; Pop-Location; exit 1 }

# Find matching java for javac
$javacPath = (Get-Command javac).Source
$binDir = Split-Path $javacPath -Parent
$jdkDir = Split-Path $binDir -Parent
$javaExe = Join-Path $jdkDir 'bin\java.exe'

Write-Output "Starting GUI using: $javaExe"
& $javaExe -cp out CoffeeShopGUI

Pop-Location
