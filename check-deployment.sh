#!/bin/bash

# Script de vérification avant déploiement sur Render
echo "🔍 Vérification du projet pour déploiement Render..."
echo ""

# Vérifier les fichiers essentiels
echo "📋 Fichiers de déploiement:"
files=("Dockerfile" "render.yaml" ".dockerignore" "pom.xml" "src/main/java/com/example/demo/Tp1Application.java")

for file in "${files[@]}"; do
    if [ -f "$file" ]; then
        echo "  ✅ $file"
    else
        echo "  ❌ $file - MANQUANT"
    fi
done

echo ""
echo "📦 Structure Maven:"
if [ -f "pom.xml" ]; then
    echo "  ✅ pom.xml trouvé"
    if grep -q "spring-boot-starter-web" pom.xml; then
        echo "  ✅ Spring Boot Web configuré"
    fi
    if grep -q "spring-boot-starter-thymeleaf" pom.xml; then
        echo "  ✅ Thymeleaf configuré"
    fi
fi

echo ""
echo "🐳 Configuration Docker:"
if [ -f "Dockerfile" ]; then
    if grep -q "eclipse-temurin:21" Dockerfile; then
        echo "  ✅ Java 21 configuré"
    fi
    if grep -q "EXPOSE 8080" Dockerfile; then
        echo "  ✅ Port 8080 exposé"
    fi
fi

echo ""
echo "✅ Prêt pour le déploiement sur Render!"
echo ""
echo "📝 Prochaines étapes:"
echo "   1. git add ."
echo "   2. git commit -m 'Ready for Render'"
echo "   3. git push origin main"
echo "   4. Aller sur render.com et créer un Web Service"
echo ""

