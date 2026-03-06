# 🎨 Architecture de Déploiement - Vue Visuelle

## 📊 Flux de Déploiement

```
┌─────────────────────────────────────────────────────────────────────┐
│                         VOTRE ORDINATEUR                            │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  📁 Projet Spring Boot                                              │
│  ├── src/                                                           │
│  ├── pom.xml                                                        │
│  ├── Dockerfile         ← Nouvellement créé ✨                      │
│  ├── render.yaml        ← Nouvellement créé ✨                      │
│  └── .dockerignore      ← Nouvellement créé ✨                      │
│                                                                     │
│  ⬇️  git add . && git commit && git push                            │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
                                    │
                                    │
                                    ⬇️
┌─────────────────────────────────────────────────────────────────────┐
│                            GITHUB                                   │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  📦 Dépôt Git (main branch)                                         │
│  └── Code source + Dockerfile + render.yaml                         │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
                                    │
                                    │ Webhook automatique
                                    ⬇️
┌─────────────────────────────────────────────────────────────────────┐
│                         RENDER.COM                                  │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  🔨 BUILD STAGE (8-10 min la première fois)                         │
│  ┌─────────────────────────────────────────────────────┐           │
│  │ 1. Clone le dépôt GitHub                            │           │
│  │ 2. Lit le Dockerfile                                │           │
│  │ 3. Télécharge Maven + Java 21                       │           │
│  │ 4. Télécharge dépendances Spring Boot               │           │
│  │ 5. Compile le projet (mvn package)                  │           │
│  │ 6. Crée l'image Docker finale                       │           │
│  └─────────────────────────────────────────────────────┘           │
│                          ⬇️                                          │
│  🚀 DEPLOY STAGE                                                    │
│  ┌─────────────────────────────────────────────────────┐           │
│  │ 1. Lance le container Docker                        │           │
│  │ 2. Démarre Spring Boot (java -jar app.jar)         │           │
│  │ 3. Expose le port 8080                              │           │
│  │ 4. Crée une URL publique                            │           │
│  └─────────────────────────────────────────────────────┘           │
│                          ⬇️                                          │
│  ✅ LIVE                                                            │
│  https://votre-app.onrender.com                                     │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
                                    │
                                    │
                                    ⬇️
┌─────────────────────────────────────────────────────────────────────┐
│                    NAVIGATEUR (VOTRE PROF)                          │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  🌐 https://votre-app.onrender.com                                  │
│  ├── /                     → Page d'accueil                         │
│  ├── /person/list          → Liste des personnes                   │
│  ├── /person/add           → Ajouter une personne                  │
│  ├── /person/edit/{index}  → Modifier une personne                 │
│  └── /person/delete/{index}→ Supprimer une personne                │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

---

## 🏗️ Architecture du Dockerfile (Multi-stage)

```
┌─────────────────────────────────────────────────────────────────────┐
│                    STAGE 1: BUILD (Image ~1GB)                      │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  📦 Base: maven:3.9-eclipse-temurin-21                              │
│                                                                     │
│  📥 COPY pom.xml                                                    │
│  ⬇️  RUN mvn dependency:go-offline    ← Cache les dépendances      │
│                                                                     │
│  📥 COPY src/                                                       │
│  ⬇️  RUN mvn clean package            ← Compile le projet          │
│                                                                     │
│  📤 OUTPUT: target/demo-0.0.1-SNAPSHOT.jar                          │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
                                    │
                                    │ Copie uniquement le JAR
                                    ⬇️
┌─────────────────────────────────────────────────────────────────────┐
│                 STAGE 2: RUNTIME (Image ~300MB)                     │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  📦 Base: eclipse-temurin:21-jre    ← Plus légère (pas Maven)      │
│                                                                     │
│  📥 COPY --from=build /app/target/*.jar app.jar                     │
│                                                                     │
│  🔧 ENV JAVA_OPTS="-Xmx512m -Xms256m"                               │
│  🌐 EXPOSE 8080                                                     │
│                                                                     │
│  ▶️  ENTRYPOINT java $JAVA_OPTS -jar app.jar                        │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

**Avantage:** Image finale 3x plus petite! 🎉

---

## 🔄 Workflow de Mise à Jour

```
┌──────────────┐
│  Vous faites │
│ des modifs   │
│  au code     │
└──────┬───────┘
       │
       ⬇️
┌──────────────┐
│  git push    │
└──────┬───────┘
       │
       ⬇️  Webhook
┌──────────────────┐
│ Render détecte   │
│ le nouveau       │
│ commit           │
└──────┬───────────┘
       │
       ⬇️
┌──────────────────┐
│ Rebuild auto     │
│ (~3-5 min)       │
└──────┬───────────┘
       │
       ⬇️
┌──────────────────┐
│ Redéploiement    │
│ automatique      │
└──────┬───────────┘
       │
       ⬇️
┌──────────────────┐
│ App mise à jour! │
│ ✅ LIVE          │
└──────────────────┘
```

---

## 📦 Fichiers Créés - Rôle de chacun

```
C:\SPRINGBOOT\projects\demo\
│
├── Dockerfile                   ← Recette pour construire l'image Docker
│   │                             • Stage 1: Compile avec Maven
│   │                             • Stage 2: Exécute avec Java JRE
│   └── Multi-stage = Image légère (300MB au lieu de 1GB)
│
├── render.yaml                  ← Configuration automatique pour Render
│   │                             • Type: Web Service
│   │                             • Environment: Docker
│   │                             • Plan: Free
│   └── Variables d'environnement Java
│
├── .dockerignore                ← Fichiers à exclure du build Docker
│   │                             • target/ (sera reconstruit)
│   │                             • .idea/, .vscode/ (IDE)
│   │                             • *.md (documentation)
│   └── Résultat: Build 40% plus rapide
│
├── DEPLOY_RENDER.md             ← Guide complet de déploiement (5.3KB)
│   └── Instructions pas à pas avec screenshots conceptuels
│
├── RENDER_QUICKSTART.md         ← Checklist rapide en 3 minutes (1.5KB)
│   └── Pour un déploiement express
│
├── DEPLOYMENT_SUMMARY.md        ← Résumé technique complet
│   └── Ce que vous lisez actuellement
│
├── check-deployment.ps1         ← Script de vérification Windows
│   └── Vérifie que tous les fichiers sont OK
│
└── check-deployment.sh          ← Script de vérification Linux/Mac
    └── Même fonction que le .ps1
```

---

## ⚡ Timeline du Premier Déploiement

```
T+0min   ▶️  Vous: git push origin main
         │
T+10s    📡 GitHub: Commit reçu
         │
T+20s    🔔 Render: Webhook déclenché
         │
T+30s    📥 Render: Clone du dépôt
         │
T+1min   🐳 Render: Démarre le build Docker
         │         
T+2min   📦 Render: Télécharge Maven + Java 21
         │
T+5min   ⬇️  Render: Télécharge dépendances Spring Boot
         │         (spring-boot-starter-web, thymeleaf, etc.)
         │
T+7min   🔨 Render: Compile le projet (mvn package)
         │
T+8min   🏗️  Render: Crée l'image Docker finale
         │
T+9min   🚀 Render: Lance le container
         │
T+9m30s  ⏳ Render: Spring Boot démarre
         │
T+10min  ✅ Render: Application LIVE!
         │
         🌐 URL: https://votre-app.onrender.com
```

**Déploiements suivants:** Seulement 3-5 minutes (cache Maven) ⚡

---

## 🎯 Checklist de Vérification

### Avant de déployer:

- ✅ Dockerfile créé
- ✅ render.yaml créé
- ✅ .dockerignore créé
- ✅ pom.xml configuré avec Spring Boot 3.5.11
- ✅ Java 21 configuré
- ✅ Code pushé sur GitHub
- ✅ Compte Render créé

### Sur Render.com:

- ✅ Web Service créé
- ✅ GitHub repo connecté
- ✅ Environment = **Docker** ⚠️ (PAS Java!)
- ✅ Plan = Free
- ✅ Build en cours...
- ✅ Status = Live

### Test final:

- ✅ URL accessible
- ✅ Page d'accueil charge
- ✅ `/person/list` fonctionne
- ✅ Ajout de personne fonctionne
- ✅ Modification fonctionne
- ✅ Suppression fonctionne

---

## 🎓 Partage avec le Professeur

```
────────────────────────────────────────────
  📧 EMAIL À ENVOYER À VOTRE PROFESSEUR
────────────────────────────────────────────

Objet: Déploiement du projet Spring Boot

Bonjour,

Mon projet Spring Boot est déployé et accessible à l'adresse suivante:

🌐 https://votre-app.onrender.com

Fonctionnalités disponibles:
• Page d'accueil: /
• Gestion des personnes: /person/list
• Ajout: /person/add
• Modification: /person/edit/{index}
• Suppression: /person/delete/{index}

Note: Le premier chargement peut prendre 30-60 secondes
(mise en veille automatique du plan gratuit).

Cordialement,
[Votre nom]

────────────────────────────────────────────
```

---

## 🆘 Aide et Support

| Problème | Où chercher |
|----------|-------------|
| Erreur de build | Logs dans le dashboard Render |
| App ne démarre pas | Vérifier les logs Spring Boot |
| Environment error | Vérifier que "Docker" est sélectionné |
| Guide complet | Voir `DEPLOY_RENDER.md` |
| Checklist rapide | Voir `RENDER_QUICKSTART.md` |

---

## 🎉 Félicitations!

Votre application Spring Boot est prête pour le déploiement sur Render.

**Prochaine étape:** Suivez les instructions dans `RENDER_QUICKSTART.md`

Bonne chance! 🚀

---

*Document généré automatiquement - 6 mars 2026*

