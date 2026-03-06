# 🧪 EXÉCUTION DES TESTS - GUIDE RAPIDE

## ⚡ Démarrage Rapide

### Option 1: Depuis l'IDE (Recommandé)

#### IntelliJ IDEA / Spring Tool Suite:

1. **Exécuter FirstControllerTest:**
   - Ouvrir: `src/test/java/com/example/demo/controllers/FirstControllerTest.java`
   - Clic droit sur la classe → **Run 'FirstControllerTest'**
   - ✅ 6 tests passent
   - ❌ 1 test échoue volontairement (`testHomeContainsSymfony`)

2. **Exécuter PersonControllerTest:**
   - Ouvrir: `src/test/java/com/example/demo/controllers/PersonControllerTest.java`
   - Clic droit sur la classe → **Run 'PersonControllerTest'**
   - ✅ 15 tests passent

3. **Exécuter TOUS les tests:**
   - Clic droit sur `src/test/java` → **Run 'All Tests'**

---

## ⚠️ Test Volontairement en Échec

### testHomeContainsSymfony() - POURQUOI IL ÉCHOUE

Dans `FirstControllerTest.java`, il y a un test qui échoue **intentionnellement**:

```java
/**
 * TEST VOLONTAIREMENT EN ÉCHEC
 * Ce test échoue car la page ne contient pas "Symfony"
 * Purpose : Démontrer que les tests vérifient réellement le comportement
 */
@Test
public void testHomeContainsSymfony() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Symfony")));
}
```

**Pourquoi ?**
- La page d'accueil contient "**Spring Boot**"
- Le test cherche "**Symfony**"
- Donc : **ÉCHEC** ❌

**Purpose:**
Démontrer que les tests vérifient réellement le contenu et ne passent pas "par chance".

---

## 🔧 Options pour Gérer ce Test

### Option A: Commenter le Test (Pour avoir 100% de succès)

```java
/**
 * TEST VOLONTAIREMENT EN ÉCHEC
 * COMMENTÉ pour avoir tous les tests verts
 */
// @Test
public void testHomeContainsSymfony() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Symfony")));
}
```

### Option B: Le Désactiver avec @Disabled

```java
@Test
@Disabled("Test de démonstration - volontairement en échec")
public void testHomeContainsSymfony() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Symfony")));
}
```

### Option C: Le Garder (Recommandé pour apprendre)

Gardez-le tel quel pour voir à quoi ressemble un test qui échoue !

---

## 📊 Résultats Attendus

### Avec testHomeContainsSymfony ACTIF:

```
FirstControllerTest
  ✓ testHomeReturnsStatus200
  ✓ testHomeContainsFramework
  ✓ testHomeDefaultFramework
  ✓ testHomeDefaultVersion
  ✓ testHomeReturnsCorrectView
  ✓ testHomeWithCustomVersion
  ✗ testHomeContainsSymfony ← ÉCHEC VOLONTAIRE

Tests run: 7, Failures: 1, Errors: 0, Skipped: 0
```

### Avec testHomeContainsSymfony DÉSACTIVÉ:

```
FirstControllerTest
  ✓ testHomeReturnsStatus200
  ✓ testHomeContainsFramework
  ✓ testHomeDefaultFramework
  ✓ testHomeDefaultVersion
  ✓ testHomeReturnsCorrectView
  ✓ testHomeWithCustomVersion

Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
```

---

## 🎯 Résumé des Tests

| Classe de Test | Nombre de Tests | Tests Passants | Tests Échouants |
|----------------|-----------------|----------------|-----------------|
| **FirstControllerTest** | 7 | 6 | 1 (volontaire) |
| **PersonControllerTest** | 15 | 15 | 0 |
| **TOTAL** | 22 | 21 | 1 |

---

## 🚀 Exécution avec Maven (Ligne de Commande)

```powershell
# Tous les tests
.\mvnw.cmd test

# Uniquement FirstControllerTest
.\mvnw.cmd test -Dtest=FirstControllerTest

# Uniquement PersonControllerTest
.\mvnw.cmd test -Dtest=PersonControllerTest

# Un test spécifique
.\mvnw.cmd test -Dtest=FirstControllerTest#testHomeReturnsStatus200

# Ignorer les échecs de tests
.\mvnw.cmd test -Dmaven.test.failure.ignore=true
```

---

## 📝 Message d'Erreur Typique

Quand `testHomeContainsSymfony` échoue, vous verrez:

```
java.lang.AssertionError: Response content 
Expected: a string containing "Symfony"
     but: was "<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Page d'accueil</title>
</head>
<body>
    <h1>Bienvenue !</h1>
    <p>Framework: Spring Boot</p>
    <p>Version: v3.3.3</p>
</body>
</html>"
```

**Interprétation:**
- ❌ Le test cherchait "Symfony"
- ✅ Mais la page contient "Spring Boot"
- 💡 Donc le test détecte correctement l'erreur !

---

## 🎓 Apprendre des Tests qui Échouent

C'est une **bonne chose** qu'un test échoue quand il y a un problème !

### Scénario 1: Le Test Est Correct, Le Code Est Faux
→ **Corriger le code** pour satisfaire le test

### Scénario 2: Le Code Est Correct, Le Test Est Faux
→ **Corriger le test** pour refléter le comportement attendu

### Scénario 3: Test de Démonstration (Notre Cas)
→ Commenter ou désactiver pour l'apprentissage

---

## ✅ Checklist pour Exécuter les Tests

- [ ] Java 21 installé
- [ ] Projet ouvert dans l'IDE
- [ ] Dépendances Maven téléchargées
- [ ] Pas d'erreurs de compilation
- [ ] Fichiers de test créés:
  - [ ] `FirstControllerTest.java`
  - [ ] `PersonControllerTest.java`
- [ ] Contrôleurs implémentés:
  - [ ] `FirstController.java`
  - [ ] `PersonController.java`

---

## 🔍 Debugging des Tests

### Si un test échoue de manière inattendue:

1. **Lire le message d'erreur** - Il indique ce qui était attendu vs ce qui a été reçu
2. **Vérifier le contrôleur** - Le code correspond-il au test ?
3. **Vérifier la vue** - Le template Thymeleaf retourne-t-il le bon contenu ?
4. **Exécuter en mode Debug** - Placer un breakpoint dans le test
5. **Vérifier les logs** - Regarder la console pour les erreurs

### Outils de Debugging:

```java
// Ajouter du logging dans le test
@Test
public void testDebug() throws Exception {
    MvcResult result = mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andReturn();
    
    // Afficher le contenu retourné
    System.out.println(result.getResponse().getContentAsString());
}
```

---

## 🎉 Conclusion

Vous avez maintenant:
- ✅ **22 tests unitaires** fonctionnels
- ✅ Couverture complète des **contrôleurs**
- ✅ Tests de **tous les endpoints** (GET/POST)
- ✅ Tests de **validation** et **cas limites**
- ✅ Un exemple de **test en échec** (éducatif)

**Les tests sont le filet de sécurité de votre application !**

---

## 📚 Voir Aussi

- `GUIDE_TESTS_JUNIT.md` - Guide complet et détaillé
- `README.md` - Documentation du projet
- Documentation JUnit 5: https://junit.org/junit5/
- Spring Testing: https://docs.spring.io/spring-framework/reference/testing.html

---

**🚀 Bonne exécution de vos tests !**

