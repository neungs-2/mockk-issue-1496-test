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

# MockK Issue #1523 - Interface Dependency Order Resolution Test

Test cases and benchmarks for [MockK Issue #1523](https://github.com/mockk/mockk/issues/1523).

## Issue Summary

**Problem:** MockK's `useDependencyOrder = true` resolves `@InjectMockKs` dependencies by concrete class relationships, but misses dependencies declared as an interface when another `@InjectMockKs` field provides an implementation.

```kotlin
class A(val b: B)  // A depends on interface B
interface B
class C : B        // C is the injectable implementation

class TestClass {
    @InjectMockKs lateinit var a: A
    @InjectMockKs lateinit var c: C
}

MockKAnnotations.init(this, useDependencyOrder = true)

// Expected: a.b === c
// Bug: C is not considered as a dependency candidate for interface B
```

**Solution:** Dependency ordering should consider assignable types, so an injected implementation can satisfy a constructor dependency declared as its interface or supertype.

## Test Results

| MockK Version | Result |
|---------------|--------|
| 1.14.9 (bug) | FAILED |
| 1.14.10-issue1523-SNAPSHOT (fix) | PASSED |

## Test Cases

The test has **constructor dependency type ≠ injected field concrete type** to reproduce the bug:

| # | Test | Dependency | Injectable Field | Bug |
|---|------|------------|------------------|-----|
| 1 | Interface implementation (A→B, C:B) | `A` needs `B` | `c: C` | ✅ |

## Running Tests

```bash
# Test with bug (1.14.9) - Issue1523Test fails
./gradlew test --tests com.example.demo.Issue1523Test

# Test with fix (1.14.10-issue1523-SNAPSHOT) - Issue1523Test passes
# Edit build.gradle.kts to use 1.14.10-issue1523-SNAPSHOT + mavenLocal()
./gradlew test --tests com.example.demo.Issue1523Test
```

## Running Benchmarks

`Issue1523BenchmarkTest` compares initialization throughput with and without dependency ordering across independent, wide, linear, diamond, interface, and nested-interface graphs.

```bash
./gradlew issue1523Benchmark

# Optional: pass custom JMH arguments
./gradlew issue1523Benchmark -Pissue1523BenchmarkArgs="com.example.demo.Issue1523BenchmarkTest.initWithDependencyOrderInterface5 -wi 1 -i 3 -r 10s -w 10s -f 1"
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
    testImplementation("io.mockk:mockk-jvm:1.14.10-issue1523-SNAPSHOT")  // Fixed (from mavenLocal)
    // testImplementation("io.mockk:mockk-jvm:1.14.9")                   // Bug (from mavenCentral)

    implementation("io.mockk:mockk-jvm:1.14.10-issue1523-SNAPSHOT")      // For benchmarks
    // implementation("io.mockk:mockk-jvm:1.14.9")
}
```
