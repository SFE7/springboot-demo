# 🚀 Guide de Déploiement sur Render

Ce guide vous explique comment déployer votre application Spring Boot sur Render gratuitement.

## 📋 Prérequis

1. Un compte GitHub (gratuit)
2. Un compte Render (gratuit) - créez-le sur [render.com](https://render.com)
3. Votre projet doit être dans un dépôt GitHub

## 📦 Fichiers Créés

Les fichiers suivants ont été créés pour le déploiement :

- **Dockerfile** : Instructions pour construire l'image Docker de votre application
- **render.yaml** : Configuration pour Render (optionnel mais recommandé)
- **.dockerignore** : Fichiers à exclure du build Docker

## 🔧 Étape 1 : Préparer votre dépôt GitHub

### Option A : Si votre projet n'est pas encore sur GitHub

1. Ouvrez un terminal dans le dossier de votre projet
2. Exécutez les commandes suivantes :

```bash
git init
git add .
git commit -m "Initial commit avec Dockerfile pour Render"
git branch -M main
git remote add origin https://github.com/VOTRE-USERNAME/VOTRE-REPO.git
git push -u origin main
```

### Option B : Si votre projet est déjà sur GitHub

```bash
git add .
git commit -m "Ajout Dockerfile pour déploiement sur Render"
git push origin main
```

## 🌐 Étape 2 : Déployer sur Render

### 1. Connexion à Render

- Allez sur [render.com](https://render.com)
- Cliquez sur **Sign Up** ou **Log In**
- Connectez-vous avec votre compte GitHub

### 2. Créer un nouveau Web Service

- Cliquez sur **New +** (en haut à droite)
- Sélectionnez **Web Service**

### 3. Connecter votre dépôt GitHub

- Render affichera vos dépôts GitHub
- Trouvez votre projet et cliquez sur **Connect**
- Si vous ne voyez pas votre dépôt :
  - Cliquez sur **Configure account**
  - Donnez accès à Render pour le dépôt souhaité

### 4. Configurer le service

Render devrait détecter automatiquement votre Dockerfile. Vérifiez ces paramètres :

| Paramètre | Valeur |
|-----------|--------|
| **Name** | `spring-boot-demo` (ou votre choix) |
| **Region** | `Frankfurt (EU Central)` (le plus proche) |
| **Branch** | `main` |
| **Environment** | **Docker** ⚠️ IMPORTANT |
| **Plan** | **Free** |

### 5. Variables d'environnement (optionnel)

Si vous utilisez `render.yaml`, ces variables sont déjà configurées. Sinon, ajoutez-les manuellement :

- `JAVA_OPTS` = `-Xmx512m -Xms256m`
- `SERVER_PORT` = `8080`

### 6. Déployer

- Cliquez sur **Create Web Service**
- Render va :
  1. Cloner votre dépôt
  2. Construire l'image Docker (peut prendre 5-10 minutes la première fois)
  3. Démarrer votre application
  4. Vous donner une URL publique

## 🎉 Étape 3 : Accéder à votre application

Une fois le déploiement terminé (statut **Live**), vous verrez :

- Une URL comme : `https://spring-boot-demo-XXXX.onrender.com`
- Cliquez dessus pour voir votre application en ligne !

### Partager avec votre professeur

Copiez simplement l'URL et envoyez-la à votre professeur. Par exemple :
```
https://spring-boot-demo-abcd.onrender.com
```

## ⚠️ Important : Plan Gratuit de Render

Le plan gratuit de Render a quelques limitations :

1. **Mise en veille automatique** : L'application s'endort après 15 minutes d'inactivité
   - Le premier accès après la mise en veille prend 30-60 secondes
   - C'est normal et gratuit !

2. **750 heures par mois** : Largement suffisant pour des démonstrations

3. **Redémarrage quotidien** : L'application redémarre automatiquement chaque jour

## 🔄 Mises à jour

Pour mettre à jour votre application déployée :

1. Faites vos modifications localement
2. Commitez et poussez sur GitHub :
   ```bash
   git add .
   git commit -m "Description des modifications"
   git push origin main
   ```
3. Render redéploiera automatiquement votre application !

## 🐛 Dépannage

### L'application ne démarre pas ?

Vérifiez les logs dans le dashboard Render :
- Cliquez sur votre service
- Allez dans l'onglet **Logs**
- Cherchez les erreurs (en rouge)

### Erreur "Environment must be Docker" ?

- Retournez dans les paramètres du service
- Changez **Environment** de "Java" vers **Docker**
- Sauvegardez et redéployez

### Le build est trop lent ?

Le premier build prend 5-10 minutes (téléchargement des dépendances Maven).
Les builds suivants sont plus rapides grâce au cache Docker.

### L'application redémarre toute seule ?

C'est normal avec le plan gratuit : redémarrage quotidien et mise en veille après 15 minutes d'inactivité.

## 📊 Vérifier que tout fonctionne

Testez ces URLs (remplacez par votre URL Render) :

1. Page d'accueil : `https://votre-app.onrender.com/`
2. Liste des personnes : `https://votre-app.onrender.com/person/list`
3. Ajouter une personne : `https://votre-app.onrender.com/person/add`

## 🎓 Pour votre professeur

Votre professeur peut :
- Accéder à l'application via l'URL publique
- Tester toutes les fonctionnalités (ajouter, modifier, supprimer des personnes)
- Voir l'application en temps réel sans installation

## 📞 Besoin d'aide ?

- Documentation Render : [docs.render.com](https://docs.render.com)
- Support Render : [community.render.com](https://community.render.com)

---

**Créé le 6 mars 2026** 🚀

