# ⚡ Déploiement Render - Checklist Rapide

## ✅ Fichiers créés

- ✅ `Dockerfile` - Build l'application Spring Boot
- ✅ `render.yaml` - Configuration automatique pour Render
- ✅ `.dockerignore` - Optimise le build Docker
- ✅ `DEPLOY_RENDER.md` - Guide complet de déploiement

## 🚀 Étapes de Déploiement (3 minutes)

### 1️⃣ Pousser sur GitHub

```bash
git add .
git commit -m "Ready for Render deployment"
git push origin main
```

### 2️⃣ Sur Render.com

1. **New +** → **Web Service**
2. **Connect** votre dépôt GitHub
3. Vérifier :
   - Environment: **Docker** ⚠️
   - Plan: **Free**
4. **Create Web Service**

### 3️⃣ Attendre (~8 minutes)

- Render construit l'image Docker
- Status: Building → Deploying → **Live** ✅

### 4️⃣ Tester

Cliquez sur l'URL fournie : `https://XXXXX.onrender.com`

## 🎯 URLs à tester

```
/                   → Page d'accueil
/person/list        → Liste des personnes
/person/add         → Ajouter une personne
```

## ⚠️ Important

- **Premier accès après 15 min** : Peut prendre 30-60 secondes (mise en veille gratuite)
- **Gratuit et illimité** : Parfait pour démo à votre professeur
- **Mises à jour auto** : À chaque push sur GitHub

## 🆘 Problème ?

1. Vérifiez les **Logs** dans Render
2. Assurez-vous d'avoir sélectionné **Environment: Docker**
3. Consultez `DEPLOY_RENDER.md` pour plus de détails

---

**🎓 Partagez simplement l'URL Render à votre professeur !**

