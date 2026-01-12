package com.example.demo

import io.mockk.MockKAnnotations
import io.mockk.MockKException
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Test cases for Issue #1496: @InjectMockKs dependency resolution
 * https://github.com/mockk/mockk/issues/1496
 *
 * Problem: MockK processes @InjectMockKs fields in alphabetical order,
 * which fails when dependency order differs from alphabetical order.
 *
 * Solution: Use topological sort to resolve dependencies in correct order.
 *
 * ALL tests in this file FAIL on MockK 1.14.7 and PASS on 1.14.8+ (with fix)
 */
@DisplayName("Issue #1496: @InjectMockKs Dependency Order Resolution")
class Issue1496Test {

    // ===========================================
    // Test Domain Classes
    // ===========================================

    interface Repository {
        fun getData(): String
    }

    // Two-level: A depends on B
    class ServiceB(val repository: Repository)
    class ServiceA(val b: ServiceB)

    // Three-level chain: A -> B -> C (reverse alphabetical)
    class LevelC(val repository: Repository)
    class LevelB(val c: LevelC)
    class LevelA(val b: LevelB)

    // Circular dependency
    class CircularX(val y: CircularY)
    class CircularY(val x: CircularX)

    // Diamond dependency: D -> (B, C), B -> A, C -> A
    class DiamondA(val repository: Repository)
    class DiamondB(val a: DiamondA)
    class DiamondC(val a: DiamondA)
    class DiamondD(val b: DiamondB, val c: DiamondC)

    // ===========================================
    // BUG REPRODUCTION TEST 1: Basic two-level
    // Field 'a' < 'b' alphabetically, but A depends on B
    // ===========================================

    @Nested
    @DisplayName("BUG: Two-level dependency (A -> B, but 'a' < 'b')")
    inner class TwoLevelDependencyTest {

        @MockK
        lateinit var repository: Repository

        @InjectMockKs
        lateinit var a: ServiceA  // 'a' < 'b', but A needs B

        @InjectMockKs
        lateinit var b: ServiceB

        @Test
        fun `should resolve A depending on B regardless of alphabetical order`() {
            MockKAnnotations.init(this)

            assertNotNull(b, "ServiceB should be created")
            assertNotNull(a, "ServiceA should be created")
            assertEquals(b, a.b, "ServiceA.b should be the injected ServiceB")
        }
    }

    // ===========================================
    // BUG REPRODUCTION TEST 2: Three-level chain
    // Fields: a < b < c alphabetically
    // Dependencies: A -> B -> C (completely reverse!)
    // ===========================================

    @Nested
    @DisplayName("BUG: Three-level chain (A -> B -> C, but 'a' < 'b' < 'c')")
    inner class ThreeLevelChainTest {

        @MockK
        lateinit var repository: Repository

        @InjectMockKs
        lateinit var a: LevelA  // Depends on B

        @InjectMockKs
        lateinit var b: LevelB  // Depends on C

        @InjectMockKs
        lateinit var c: LevelC  // Independent (only needs @MockK)

        @Test
        fun `should resolve three-level reverse dependency chain`() {
            MockKAnnotations.init(this)

            assertNotNull(c, "LevelC should be created first")
            assertNotNull(b, "LevelB should be created second")
            assertNotNull(a, "LevelA should be created third")
            assertEquals(c, b.c, "LevelB.c should be the injected LevelC")
            assertEquals(b, a.b, "LevelA.b should be the injected LevelB")
        }
    }

    // ===========================================
    // BUG REPRODUCTION TEST 3: Diamond dependency
    // Fields: alpha < beta < gamma < zeta alphabetically
    // But: alpha(D) needs beta(B) & gamma(C), which need zeta(A)
    // Required order: zeta -> beta, gamma -> alpha
    // ===========================================

    @Nested
    @DisplayName("BUG: Diamond dependency (alpha needs beta,gamma which need zeta)")
    inner class DiamondDependencyTest {

        @MockK
        lateinit var repository: Repository

        @InjectMockKs
        lateinit var alpha: DiamondD  // 'alpha' is first, but needs beta & gamma

        @InjectMockKs
        lateinit var beta: DiamondB   // Needs zeta

        @InjectMockKs
        lateinit var gamma: DiamondC  // Needs zeta

        @InjectMockKs
        lateinit var zeta: DiamondA   // 'zeta' is last, but is independent (leaf)

        @Test
        fun `should resolve diamond dependency graph`() {
            MockKAnnotations.init(this)

            assertNotNull(zeta, "DiamondA (zeta) should be created")
            assertNotNull(beta, "DiamondB (beta) should be created")
            assertNotNull(gamma, "DiamondC (gamma) should be created")
            assertNotNull(alpha, "DiamondD (alpha) should be created")

            assertEquals(zeta, beta.a, "DiamondB.a should be zeta")
            assertEquals(zeta, gamma.a, "DiamondC.a should be zeta")
            assertEquals(beta, alpha.b, "DiamondD.b should be beta")
            assertEquals(gamma, alpha.c, "DiamondD.c should be gamma")
        }
    }

    // ===========================================
    // BUG REPRODUCTION TEST 4: Reverse naming
    // Fields: zService < aService (z < a? No! a < z)
    // Wait, let's use: serviceZ and serviceA
    // 'serviceA' < 'serviceZ', but ServiceZ is independent, ServiceA depends on Z
    // ===========================================

    class ServiceZ(val repository: Repository)
    class ServiceY(val z: ServiceZ)

    @Nested
    @DisplayName("BUG: Misleading names (ServiceY -> ServiceZ, 'serviceY' < 'serviceZ')")
    inner class MisleadingNamesTest {

        @MockK
        lateinit var repository: Repository

        @InjectMockKs
        lateinit var serviceY: ServiceY  // 'serviceY' < 'serviceZ', but Y depends on Z

        @InjectMockKs
        lateinit var serviceZ: ServiceZ

        @Test
        fun `should resolve despite misleading field names`() {
            MockKAnnotations.init(this)

            assertNotNull(serviceZ, "ServiceZ should be created")
            assertNotNull(serviceY, "ServiceY should be created")
            assertEquals(serviceZ, serviceY.z, "ServiceY.z should be the injected ServiceZ")
        }
    }

    // ===========================================
    // CIRCULAR DEPENDENCY TEST
    // Should fail with clear error message (improvement over 1.14.7)
    // ===========================================

    @Nested
    @DisplayName("CIRCULAR: Should detect with clear error message")
    inner class CircularDependencyTest {

        @InjectMockKs
        lateinit var x: CircularX

        @InjectMockKs
        lateinit var y: CircularY

        @Test
        fun `should detect circular dependency with clear error message`() {
            val exception = assertThrows<MockKException> {
                MockKAnnotations.init(this)
            }

            assertTrue(
                exception.message?.contains("Circular dependency") == true,
                "Error should mention 'Circular dependency', but was: ${exception.message}"
            )

            assertTrue(
                exception.message?.contains("x") == true && exception.message?.contains("y") == true,
                "Error should include field names 'x' and 'y', but was: ${exception.message}"
            )
        }
    }
}
