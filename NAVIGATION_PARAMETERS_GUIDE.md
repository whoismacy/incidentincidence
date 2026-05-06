# Navigation Parameters Guide: Route vs Composable Parameters

## The Two Types of Parameters

When using typed navigation in Compose, there are **two distinct places** where parameters flow:

### 1. **Route Parameters** (Via NavController)
Parameters that are **serialized into the navigation route** itself.

```kotlin
@Serializable
data class EditRoute(val id: Int)

// PASSED HERE via NavController
fun NavController.navigateToEditDestination(id: Int) {
    navigate(EditRoute(id))  // ← The id becomes part of the route
}
```

**Navigation Stack looks like:**
```
"EditRoute/id=5"
```

---

### 2. **Composable Parameters** (Via NavGraphBuilder)
Parameters that are **provided directly to the composable** when it's composed.

```kotlin
fun NavGraphBuilder.editDestination(incidents: List<Incident>) {
    // PASSED HERE via NavGraphBuilder
    composable<EditRoute> { backStackEntry ->
        EditScreen(
            id = editRoute.id,        // From route
            incidents = incidents      // From NavGraphBuilder
        )
    }
}
```

---

## Why This Design?

### **Route Parameters (NavController)**
✅ **Serializable** - Can be saved/restored (deep links, back stack restoration)  
✅ **Persistent** - Survive configuration changes  
✅ **Part of Navigation State** - Can be used for back navigation  
✅ **Small Data** - int, String, Boolean (not large objects)

**Example:**
```kotlin
navigate(EditRoute(id = 5))  // Easy to serialize: "EditRoute/id=5"
```

---

### **Composable Parameters (NavGraphBuilder)**
✅ **Runtime-Only** - In-memory, not serialized  
✅ **Large Objects** - Can pass entire lists, objects, lambdas  
✅ **Dependencies** - Configuration, managers, repositories  
✅ **Callbacks** - Navigation actions, event handlers  

**Example:**
```kotlin
editDestination(
    incidents = displayData,          // Large list
    onNavigate = { ... }              // Lambda
)
```

---

## Complete Example

### **File 1: EditNavigation.kt**
```kotlin
@Serializable
data class EditRoute(val id: Int)  // Only id is serialized

// ROUTE PARAMETERS passed here via NavController
fun NavController.navigateToEditDestination(id: Int) {
    navigate(EditRoute(id))  // Routes are small, serializable
}

// COMPOSABLE PARAMETERS passed here via NavGraphBuilder
fun NavGraphBuilder.editDestination(
    incidents: List<Incident>,  // Too large for route
    viewModel: IncidentViewModel  // Needs to be injected
) {
    composable<EditRoute> { backStackEntry ->
        // Extract route parameter
        val editRoute = backStackEntry.toRoute<EditRoute>()
        
        // Combine both parameter types
        EditScreen(
            id = editRoute.id,           // From NavController route
            incidents = incidents,       // From NavGraphBuilder
            viewModel = viewModel
        )
    }
}
```

### **File 2: AppRoot.kt**
```kotlin
NavHost(navController, startDestination = HomeRoute) {
    // Pass composable parameters here (only once, at graph definition)
    editDestination(
        incidents = displayData,
        viewModel = viewModel
    )
}

// Later, trigger navigation with route parameters
Button(onClick = {
    navController.navigateToEditDestination(id = incident.id)  // Pass id only
})
```

---

## The Flow Visualized

```
┌─────────────────────────────────────────────────────────────┐
│                     App Composition                         │
└─────────────────────────────────────────────────────────────┘
                              │
                              ↓
┌─────────────────────────────────────────────────────────────┐
│  NavHost(navController, startDestination)                   │
│  {                                                          │
│      editDestination(                                       │
│          incidents = displayData,    ← Composable Params   │
│          viewModel = viewModel       ← (Injected once)     │
│      )                                                      │
│  }                                                          │
└─────────────────────────────────────────────────────────────┘
                              │
                              ↓
            ┌─────────────────────────────────┐
            │  Route Definition (NavGraphBuilder)
            │  composable<EditRoute> {
            │      ↓
            │      EditScreen(
            │          id = editRoute.id,
            │          incidents,
            │          viewModel
            │      )
            │  }
            └─────────────────────────────────┘
                        ↑              │
                        │              │
                  Route Params    Composable Params
                  (Small, typed)  (Injected, large)
```

---

## When to Use Which?

### **Use Route Parameters (NavController):**
- ✅ IDs, flags, strings
- ✅ Data that identifies the screen
- ✅ Need to support deep linking
- ✅ Need back stack restoration

```kotlin
navigate(EditRoute(id = 5))
```

---

### **Use Composable Parameters (NavGraphBuilder):**
- ✅ Large data structures (lists, objects)
- ✅ ViewModels, repositories
- ✅ Callback functions
- ✅ Configuration, managers
- ✅ Data that doesn't change per navigation

```kotlin
editDestination(
    incidents = allIncidents,
    viewModel = viewModel,
    onClose = { ... }
)
```

---

## Key Principle

**Route parameters flow DOWN at navigation time** (NavController → Route)  
**Composable parameters flow DOWN at graph definition time** (NavGraphBuilder → Composable)

This separation enables:
- 🔒 Type-safe navigation
- 💾 Serializable routes (for back stack persistence)
- 🎯 Dependency injection (via composable params)
- ⚡ Performance (lazy evaluation)

---

## Common Anti-Patterns

### ❌ DON'T: Pass large objects as route parameters
```kotlin
@Serializable
data class EditRoute(val incident: Incident)  // ❌ Can't serialize complex objects

navigate(EditRoute(incident))  // ❌ Will fail
```

### ❌ DON'T: Forget to extract route parameters
```kotlin
composable<EditRoute> { backStackEntry ->
    EditScreen(id)  // ❌ 'id' undefined! Need to extract first
}
```

### ✅ DO: Use route for IDs, composable params for objects
```kotlin
@Serializable
data class EditRoute(val id: Int)  // ✅ Just the ID

fun NavGraphBuilder.editDestination(incidents: List<Incident>) {  // ✅ Inject large data
    composable<EditRoute> { backStackEntry ->
        val editRoute = backStackEntry.toRoute<EditRoute>()
        EditScreen(id = editRoute.id, incidents = incidents)  // ✅ Both types combined
    }
}
```

---

## Summary

| Aspect | Route Parameters | Composable Parameters |
|--------|-----------------|----------------------|
| **Passed Via** | NavController | NavGraphBuilder |
| **When** | At navigation time | At graph definition time |
| **Serializable** | Yes (small data) | No (in-memory only) |
| **Examples** | IDs, flags, strings | Lists, ViewModels, callbacks |
| **Persistence** | Survives config changes | Lost on recomposition |
| **Use Case** | Screen identification | Screen dependencies |

This design pattern is the **professional standard** in Compose navigation because it combines the benefits of both type-safe routing and flexible dependency injection.

