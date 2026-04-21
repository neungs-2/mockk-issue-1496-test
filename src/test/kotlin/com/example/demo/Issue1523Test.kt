package com.example.demo

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.junit.jupiter.api.Test
import kotlin.test.assertSame

class Issue1523Test {

    class A(
        val b: B,
    )

    interface B

    class C : B

    @InjectMockKs
    lateinit var a: A

    @InjectMockKs
    lateinit var c: C

    @Test
    fun `useDependencyOrder resolves dependency through implemented interface`() {
        MockKAnnotations.init(this)

        assertSame(c, a.b)
    }
}
