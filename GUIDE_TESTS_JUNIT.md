# 🧪 GUIDE COMPLET DES TESTS UNITAIRES AVEC JUNIT

## 📋 Table des Matières
1. [Pourquoi Tester ?](#pourquoi-tester)
2. [Structure des Tests](#structure-des-tests)
3. [Tests Créés](#tests-créés)
4. [Comment Exécuter les Tests](#comment-exécuter-les-tests)
5. [Comprendre les Résultats](#comprendre-les-résultats)
6. [Concepts Clés](#concepts-clés)

---

## 🎯 Pourquoi Tester ?

### Objectifs des Tests Unitaires

✅ **Valider le comportement** - S'assurer que le contrôleur fonctionne correctement  
✅ **Vérifier les vues** - Confirmer que les bonnes vues sont retournées  
✅ **Tests rapides** - Tester sans démarrer tout le serveur  
✅ **Documentation vivante** - Les tests documentent le comportement attendu  
✅ **Prévenir les régressions** - Détecte si une modification casse une fonctionnalité  
✅ **Confiance** - Modifier le code en sachant que les tests détecteront les erreurs

---

## 📁 Structure des Tests

```
src/test/java/com/example/demo/
├── Tp1ApplicationTests.java (Test de contexte)
└── controllers/
    ├── FirstControllerTest.java (7 tests) ✨ NOUVEAU
    └── PersonControllerTest.java (15 tests) ✨ NOUVEAU
```

---

## 🧪 Tests Créés

### 1️⃣ FirstControllerTest.java (7 tests)

#### Tests Inclus:
1. ✅ **testHomeReturnsStatus200** - Vérifie le statut HTTP 200
2. ✅ **testHomeContainsFramework** - Vérifie qu'un framework personnalisé apparaît
3. ✅ **testHomeDefaultFramework** - Vérifie le framework par défaut (Spring Boot)
4. ✅ **testHomeDefaultVersion** - Vérifie la version par défaut (v3.3.3)
5. ✅ **testHomeReturnsCorrectView** - Vérifie que la vue "pages/home" est retournée
6. ✅ **testHomeWithCustomVersion** - Vérifie une version personnalisée
7. ❌ **testHomeContainsSymfony** - Test volontairement en ÉCHEC (démo)

#### Code Exemple:
```java
@Test
public void testHomeReturnsStatus200() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk());
}

@Test
public void testHomeContainsFramework() throws Exception {
    mockMvc.perform(get("/?framework=Spring"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Spring")));
}
```

---

### 2️⃣ PersonControllerTest.java (15 tests)

#### Tests de la Page d'Accueil (2 tests):
1. ✅ **testIndexReturnsStatus200** - Page d'index accessible
2. ✅ **testIndexContainsWelcomeMessage** - Message de bienvenue présent

#### Tests de la Liste (3 tests):
3. ✅ **testPersonListReturnsStatus200** - Liste accessible
4. ✅ **testPersonListContainsInitialPersons** - Personnes initiales affichées
5. ✅ **testPersonListContainsActionLinks** - Liens Modifier/Supprimer présents

#### Tests d'Ajout (3 tests):
6. ✅ **testAddPersonFormReturnsStatus200** - Formulaire accessible
7. ✅ **testAddPersonWithValidData** - Ajout avec données valides
8. ✅ **testAddPersonWithEmptyData** - Ajout avec données vides (échec attendu)

#### Tests de Modification (4 tests):
9. ✅ **testEditPersonFormReturnsStatus200** - Formulaire d'édition accessible
10. ✅ **testEditPersonFormContainsPersonData** - Données pré-remplies
11. ✅ **testEditPersonWithInvalidIndex** - Index invalide gère correctement
12. ✅ **testUpdatePersonWithValidData** - Mise à jour réussie

#### Tests de Suppression (3 tests):
13. ✅ **testDeletePersonWithValidIndex** - Suppression avec index valide
14. ✅ **testDeletePersonWithInvalidIndex** - Index invalide géré
15. ✅ **testDeletePersonWithNegativeIndex** - Index négatif géré

#### Code Exemple:
```java
@Test
public void testPersonListContainsInitialPersons() throws Exception {
    mockMvc.perform(get("/person/list"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Einstein")))
        .andExpect(content().string(containsString("Curie")))
        .andExpect(content().string(containsString("Hawking")));
}

@Test
public void testAddPersonWithValidData() throws Exception {
    mockMvc.perform(post("/person/add")
            .param("firstName", "Isaac")
            .param("lastName", "Newton"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/person/list"));
}
```

---

## 🚀 Comment Exécuter les Tests

### Méthode 1: Depuis IntelliJ IDEA / Spring Tool Suite

#### Option A: Exécuter une classe de test
1. Ouvrir `FirstControllerTest.java` ou `PersonControllerTest.java`
2. Clic droit sur le nom de la classe
3. Sélectionner **"Run 'FirstControllerTest'"** ou **"Run 'PersonControllerTest'"**

#### Option B: Exécuter un seul test
1. Cliquer sur la méthode de test
2. Clic droit sur le nom de la méthode
3. Sélectionner **"Run 'testHomeReturnsStatus200()'"**

#### Option C: Exécuter tous les tests du projet
1. Clic droit sur le dossier `src/test/java`
2. Sélectionner **"Run 'All Tests'"**

### Méthode 2: Avec Maven (ligne de commande)

```powershell
# Tous les tests
.\mvnw.cmd test

# Une seule classe de test
.\mvnw.cmd test -Dtest=FirstControllerTest

# Un seul test spécifique
.\mvnw.cmd test -Dtest=FirstControllerTest#testHomeReturnsStatus200
```

### Méthode 3: Pendant le build
```powershell
# Build avec tests
.\mvnw.cmd clean install

# Build sans tests (skip)
.\mvnw.cmd clean install -DskipTests
```

---

## 📊 Comprendre les Résultats

> **📚 Guides Détaillés Disponibles:**
> - **GUIDE_COMPRENDRE_RESULTATS.md** - Guide exhaustif avec tous les détails
> - **GUIDE_VISUEL_RESULTATS.md** - Guide visuel avec illustrations ASCII art
> 
> Cette section donne un aperçu rapide. Pour une compréhension approfondie, consultez les guides ci-dessus.

### Onglet JUnit dans l'IDE

```
✓ FirstControllerTest (6/7)                                    1.234s
  ├─ ✓ testHomeReturnsStatus200                              0.123s
  ├─ ✓ testHomeContainsFramework                             0.145s
  ├─ ✓ testHomeDefaultFramework                              0.132s
  ├─ ✓ testHomeDefaultVersion                                0.128s
  ├─ ✓ testHomeReturnsCorrectView                            0.156s
  ├─ ✓ testHomeWithCustomVersion                             0.141s
  └─ ✗ testHomeContainsSymfony ← ÉCHEC VOLONTAIRE            0.409s

Tests run: 7, Failures: 1, Errors: 0, Skipped: 0
```

### Symboles:
- ✓ (🟢 Vert) = Test réussi
- ✗ (🔴 Rouge) = Test échoué
- ⚠ (🟡 Jaune) = Test ignoré (annotation @Disabled)
- 💥 (🔴 Rouge foncé) = Erreur (Exception non gérée)

### Console Output:
```
[INFO] Tests run: 7, Failures: 1, Errors: 0, Skipped: 0

[ERROR] testHomeContainsSymfony  Time elapsed: 0.123 s  <<< FAILURE!
java.lang.AssertionError: 
Expected: a string containing "Symfony"
     but: was "<!DOCTYPE html>...Spring Boot..."
```

### Interpréter les Statistiques

```
Tests run: 22, Failures: 1, Errors: 0, Skipped: 0
│          │   │            │          │
│          │   │            │          └─ Tests ignorés (@Disabled)
│          │   │            └─ Exceptions non gérées
│          │   └─ Tests échoués (assertion failed)
│          └─ Total de tests exécutés
└─ Métrique principale
```

**Taux de réussite:** (22 - 1) / 22 = 21/22 = **95.5%**

### Types de Résultats

| Résultat | Symbole | Signification | Action |
|----------|---------|---------------|--------|
| **Passed** | ✅ | Assertion réussie | ✅ Continuer |
| **Failed** | ❌ | Assertion échouée | 🔍 Investiguer |
| **Error** | 💥 | Exception non gérée | 🚨 Corriger urgent |
| **Skipped** | ⚠️ | Test désactivé | ℹ️ Noter |

### Lire un Message d'Erreur

```
[ERROR] FirstControllerTest.testHomeContainsSymfony:89
        └──┬──┘ └──────────┬─────────────┘ └┬┘
           │              │                 │
        Classe        Méthode            Ligne

Expected: a string containing "Symfony"  ← Ce qui était ATTENDU
     but: was "...Spring Boot..."        ← Ce qui a été REÇU
```

**Comment Réagir:**
1. Identifier la **différence** entre Expected et Actual
2. Décider si c'est le **code** ou le **test** qui est incorrect
3. **Corriger** le problème
4. **Ré-exécuter** les tests

### Pour Aller Plus Loin

📖 **Consultez les guides détaillés:**
- **GUIDE_COMPRENDRE_RESULTATS.md** - Tous les scénarios, workflows, cas pratiques
- **GUIDE_VISUEL_RESULTATS.md** - Simulations d'interface, tableaux de bord visuels

---

## 🔑 Concepts Clés

### 1. Annotations Importantes

| Annotation | Description |
|------------|-------------|
| `@WebMvcTest(Controller.class)` | Teste uniquement un contrôleur MVC sans charger toute l'application |
| `@Autowired` | Injection de dépendances Spring |
| `@Test` | Marque une méthode comme test JUnit |
| `@BeforeEach` | Exécuté avant chaque test |
| `@AfterEach` | Exécuté après chaque test |
| `@Disabled` | Désactive temporairement un test |

### 2. MockMvc - L'Outil Principal

**Qu'est-ce que MockMvc ?**
- C'est un objet qui **simule des requêtes HTTP** sans démarrer un vrai serveur
- **Rapide** : Pas besoin de démarrer Tomcat
- **Fiable** : Tests isolés et reproductibles
- **Complet** : Teste toute la chaîne MVC (contrôleur → vue)

**Méthodes Principales:**

```java
// Simuler une requête GET
mockMvc.perform(get("/person/list"))

// Simuler une requête POST avec paramètres
mockMvc.perform(post("/person/add")
    .param("firstName", "Isaac")
    .param("lastName", "Newton"))

// Vérifier le statut HTTP
.andExpect(status().isOk())              // 200 OK
.andExpect(status().is3xxRedirection())   // 3xx Redirect
.andExpect(status().isNotFound())         // 404 Not Found

// Vérifier le contenu
.andExpect(content().string(containsString("Einstein")))

// Vérifier la vue
.andExpect(view().name("pages/person/personList"))

// Vérifier la redirection
.andExpect(redirectedUrl("/person/list"))
```

### 3. Matchers Hamcrest

```java
// Vérifier qu'un texte contient une chaîne
containsString("Spring")

// Vérifier la taille d'une collection
hasSize(3)

// Vérifier l'égalité
equalTo("Spring Boot")

// Combiner plusieurs matchers
allOf(containsString("Spring"), containsString("Boot"))
```

### 4. Types de Tests

#### Test de Statut HTTP:
```java
@Test
public void testHomeReturnsStatus200() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk());
}
```

#### Test de Contenu:
```java
@Test
public void testHomeContainsFramework() throws Exception {
    mockMvc.perform(get("/?framework=Spring"))
        .andExpect(content().string(containsString("Spring")));
}
```

#### Test de Vue:
```java
@Test
public void testHomeReturnsCorrectView() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(view().name("pages/home"));
}
```

#### Test de Redirection:
```java
@Test
public void testAddPersonRedirects() throws Exception {
    mockMvc.perform(post("/person/add")
            .param("firstName", "Isaac")
            .param("lastName", "Newton"))
        .andExpect(redirectedUrl("/person/list"));
}
```

---

## ❓ Questions de Compréhension

### Q1: Qu'est-ce que MockMvc ?

**Réponse:** MockMvc est un objet qui simule des requêtes HTTP sans vraiment démarrer le serveur Tomcat. Cela rend les tests rapides et fiables. Il permet de tester toute la chaîne MVC (contrôleur → modèle → vue) de manière isolée.

### Q2: Pourquoi tester au niveau du contrôleur et pas du navigateur ?

**Réponse:** 
- **Rapidité** : Les tests unitaires sont 100x plus rapides
- **Fiabilité** : Pas de dépendance sur le navigateur, JavaScript, CSS
- **Facilité** : Automatisation simple avec Maven/Gradle
- **Isolation** : Teste uniquement la logique métier
- Les tests navigateur (Selenium) viennent après pour les tests d'intégration

### Q3: Que teste exactement containsString("Spring") ?

**Réponse:** Il vérifie que le texte "Spring" apparaît **quelque part** dans le contenu HTML retourné par le contrôleur. C'est une vérification souple qui ne nécessite pas de connaître la structure exacte du HTML.

### Q4: Pourquoi @WebMvcTest et pas @SpringBootTest ?

**Réponse:**
- `@WebMvcTest` : Charge **uniquement la couche MVC** (rapide, focalisé)
- `@SpringBootTest` : Charge **toute l'application** (lent, test d'intégration)

Pour les tests unitaires de contrôleurs, utilisez `@WebMvcTest`.

### Q5: Que se passe-t-il si un test échoue ?

**Réponse:** 
1. Le build Maven **échoue** (status code ≠ 0)
2. L'IDE affiche une **croix rouge** ✗
3. Le message d'erreur indique **ce qui était attendu vs ce qui a été reçu**
4. Le développeur **corrige le code ou le test**

C'est une bonne chose ! Les tests détectent les bugs avant la production.

---

## 🎨 Bonnes Pratiques

### 1. Nommage des Tests
```java
// ✅ BON : Nom descriptif
@Test
public void testAddPersonWithValidData() { ... }

// ❌ MAUVAIS : Nom vague
@Test
public void test1() { ... }
```

### 2. Un Test = Une Assertion Principale
```java
// ✅ BON : Teste une chose
@Test
public void testHomeReturnsStatus200() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk());
}

// ❌ MOINS BON : Teste trop de choses
@Test
public void testEverything() throws Exception {
    // Teste le statut, le contenu, la vue, les attributs...
}
```

### 3. Tests Indépendants
Chaque test doit pouvoir s'exécuter **seul** et dans **n'importe quel ordre**.

### 4. Utiliser des Données de Test Réalistes
```java
.param("firstName", "Isaac")  // ✅ Réaliste
.param("firstName", "test")   // ❌ Pas réaliste
```

---

## 📈 Couverture de Code

### Vérifier la Couverture

Dans IntelliJ IDEA:
1. Clic droit sur une classe de test
2. Sélectionner **"Run with Coverage"**
3. Voir le rapport de couverture (% de code testé)

### Objectifs:
- ✅ **80%+** de couverture pour les contrôleurs
- ✅ **90%+** pour la logique métier critique
- ⚠ **100%** n'est pas toujours nécessaire

---

## 🔥 Test Volontairement en Échec

### testHomeContainsSymfony()
```java
@Test
public void testHomeContainsSymfony() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Symfony")));
}
```

**Pourquoi ce test échoue:**
- La page retourne "Spring Boot" 
- Le test cherche "Symfony"
- Donc : ÉCHEC ❌

**Purpose:**
Démontrer que les tests **vérifient réellement** le comportement.
Si tous les tests passent toujours, ils ne testent probablement rien !

---

## 🎯 Résumé

### Ce que nous avons créé:
- ✅ **22 tests unitaires** (7 pour FirstController, 15 pour PersonController)
- ✅ Tests de **tous les endpoints** (GET et POST)
- ✅ Tests de **validation** (données valides/invalides)
- ✅ Tests de **cas limites** (index négatif, invalide)
- ✅ Tests de **redirection** et **vues**
- ✅ Un test en **échec volontaire** (démo)

### Bénéfices:
- 🚀 **Confiance** pour modifier le code
- 🐛 **Détection précoce** des bugs
- 📚 **Documentation** du comportement
- ⚡ **Tests rapides** (< 5 secondes)
- 🔄 **Intégration CI/CD** facile

---

## 🚀 Pour Aller Plus Loin

### Tests Avancés:
- **@MockBean** : Mocker les services
- **@DataJpaTest** : Tester les repositories
- **@SpringBootTest** : Tests d'intégration complets
- **Testcontainers** : Tests avec base de données réelle
- **Selenium** : Tests end-to-end avec navigateur

### Ressources:
- Documentation JUnit 5: https://junit.org/junit5/
- Spring Testing: https://docs.spring.io/spring-framework/reference/testing.html
- AssertJ (alternative à Hamcrest): https://assertj.github.io/doc/

---

**🎉 Vos tests sont prêts à être exécutés !**

