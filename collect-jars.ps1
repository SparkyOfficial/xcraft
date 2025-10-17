# Create target directory if it doesn't exist
if (!(Test-Path "target")) {
    New-Item -ItemType Directory -Name "target"
}

# Find and copy all JAR files to the target directory
Get-ChildItem -Path "." -Recurse -Filter "*.jar" | ForEach-Object {
    Copy-Item $_.FullName "target\"
    Write-Host "Copied $($_.Name) to target directory"
}

Write-Host "All JAR files have been copied to the target directory."