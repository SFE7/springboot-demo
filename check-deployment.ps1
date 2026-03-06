# Script de vérification avant déploiement sur Render
Write-Host "🔍 Vérification du projet pour déploiement Render..." -ForegroundColor Cyan
Write-Host ""

# Vérifier les fichiers essentiels
Write-Host "📋 Fichiers de déploiement:" -ForegroundColor Yellow
$files = @(
    "Dockerfile",
    "render.yaml",
    ".dockerignore",
    "pom.xml",
    "src\main\java\com\example\demo\Tp1Application.java"
)

foreach ($file in $files) {
    if (Test-Path $file) {
        Write-Host "  ✅ $file" -ForegroundColor Green
    } else {
        Write-Host "  ❌ $file - MANQUANT" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "📦 Structure Maven:" -ForegroundColor Yellow
if (Test-Path "pom.xml") {
    Write-Host "  ✅ pom.xml trouvé" -ForegroundColor Green
    $pomContent = Get-Content "pom.xml" -Raw
    if ($pomContent -match "spring-boot-starter-web") {
        Write-Host "  ✅ Spring Boot Web configuré" -ForegroundColor Green
    }
    if ($pomContent -match "spring-boot-starter-thymeleaf") {
        Write-Host "  ✅ Thymeleaf configuré" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "🐳 Configuration Docker:" -ForegroundColor Yellow
if (Test-Path "Dockerfile") {
    $dockerContent = Get-Content "Dockerfile" -Raw
    if ($dockerContent -match "eclipse-temurin:21") {
        Write-Host "  ✅ Java 21 configuré" -ForegroundColor Green
    }
    if ($dockerContent -match "EXPOSE 8080") {
        Write-Host "  ✅ Port 8080 exposé" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "✅ Prêt pour le déploiement sur Render!" -ForegroundColor Green
Write-Host ""
Write-Host "📝 Prochaines étapes:" -ForegroundColor Cyan
Write-Host "   1. git add ." -ForegroundColor White
Write-Host "   2. git commit -m 'Ready for Render'" -ForegroundColor White
Write-Host "   3. git push origin main" -ForegroundColor White
Write-Host "   4. Aller sur render.com et créer un Web Service" -ForegroundColor White
Write-Host ""

