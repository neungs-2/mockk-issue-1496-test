# MockK Issue #1496 - Dependency Order Resolution Test

Test cases for [MockK Issue #1496](https://github.com/mockk/mockk/issues/1496).

## Issue Summary

**Problem:** MockK's `@InjectMockKs` processes fields in alphabetical order, failing when dependency order differs.

```kotlin
class A(val b: B)  // A depends on B
class B(val repo: Repository)

class TestClass {
    @MockK lateinit var repository: Repository
    @InjectMockKs lateinit var a: A  // Processed first ('a' < 'b')
    @InjectMockKs lateinit var b: B  // Processed second, but A needs B!
}
// Result: MockKException - No matching constructors found
```

**Solution:** Topological sort to resolve dependencies in correct order.

## Test Results

| MockK Version | Result |
|---------------|--------|
| 1.14.7 (bug) | 5/5 FAILED |
| 1.14.8+ (fix) | 5/5 PASSED |

## Test Cases

All tests have **alphabetical order ≠ dependency order** to reproduce the bug:

| # | Test | Alphabetical | Dependency | Bug |
|---|------|--------------|------------|-----|
| 1 | Two-level (A→B) | a < b | B → A | ✅ |
| 2 | Three-level (A→B→C) | a < b < c | C → B → A | ✅ |
| 3 | Diamond (D→B,C→A) | alpha < beta < gamma < zeta | zeta → beta,gamma → alpha | ✅ |
| 4 | Misleading names (Y→Z) | serviceY < serviceZ | Z → Y | ✅ |
| 5 | Circular (X↔Y) | - | impossible | ✅ error msg |

## Running Tests

```bash
# Test with bug (1.14.7) - all fail
./gradlew test  # expects 5 failures

# Test with fix (1.14.8-SNAPSHOT) - all pass
# Edit build.gradle.kts to use 1.14.8-SNAPSHOT + mavenLocal()
./gradlew test  # expects 5 passes
```

## Building Fixed MockK

To test with the fix, build and publish MockK to local Maven:

```bash
# In MockK project root
cd /path/to/mockk

# Build and publish to local Maven (~/.m2/repository)
./gradlew publishToMavenLocal
```

## Switching MockK Version

In `build.gradle.kts`:

```kotlin
repositories {
    mavenLocal()  // Required for SNAPSHOT from local Maven
    mavenCentral()
}

dependencies {
    // Choose one:
    testImplementation("io.mockk:mockk-jvm:1.14.8-SNAPSHOT")  // Fixed (from mavenLocal)
    // testImplementation("io.mockk:mockk-jvm:1.14.7")        // Bug (from mavenCentral)
}
```
