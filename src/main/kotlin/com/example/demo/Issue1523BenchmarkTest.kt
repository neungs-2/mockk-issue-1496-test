package com.example.demo

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.unmockkAll
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.TearDown
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
open class Issue1523BenchmarkTest {
    @TearDown(Level.Invocation)
    fun tearDown() = unmockkAll()

    @Benchmark fun initWithoutDependencyOrderIndependent5(blackhole: Blackhole) = init(Independent5(), false, blackhole)
    @Benchmark fun initWithDependencyOrderIndependent5(blackhole: Blackhole) = init(Independent5(), true, blackhole)
    @Benchmark fun initWithoutDependencyOrderIndependent20(blackhole: Blackhole) = init(Independent20(), false, blackhole)
    @Benchmark fun initWithDependencyOrderIndependent20(blackhole: Blackhole) = init(Independent20(), true, blackhole)

    @Benchmark fun initWithoutDependencyOrderWide5(blackhole: Blackhole) = init(Wide5(), false, blackhole)
    @Benchmark fun initWithDependencyOrderWide5(blackhole: Blackhole) = init(Wide5(), true, blackhole)
    @Benchmark fun initWithoutDependencyOrderWide20(blackhole: Blackhole) = init(Wide20(), false, blackhole)
    @Benchmark fun initWithDependencyOrderWide20(blackhole: Blackhole) = init(Wide20(), true, blackhole)

    @Benchmark fun initWithoutDependencyOrderLinear5(blackhole: Blackhole) = init(Linear5(), false, blackhole)
    @Benchmark fun initWithDependencyOrderLinear5(blackhole: Blackhole) = init(Linear5(), true, blackhole)
    @Benchmark fun initWithoutDependencyOrderLinear20(blackhole: Blackhole) = init(Linear20(), false, blackhole)
    @Benchmark fun initWithDependencyOrderLinear20(blackhole: Blackhole) = init(Linear20(), true, blackhole)

    @Benchmark fun initWithoutDependencyOrderDiamond5(blackhole: Blackhole) = init(Diamond5(), false, blackhole)
    @Benchmark fun initWithDependencyOrderDiamond5(blackhole: Blackhole) = init(Diamond5(), true, blackhole)
    @Benchmark fun initWithoutDependencyOrderDiamond20(blackhole: Blackhole) = init(Diamond20(), false, blackhole)
    @Benchmark fun initWithDependencyOrderDiamond20(blackhole: Blackhole) = init(Diamond20(), true, blackhole)

    @Benchmark fun initWithoutDependencyOrderInterface5(blackhole: Blackhole) = init(Interface5(), false, blackhole)
    @Benchmark fun initWithDependencyOrderInterface5(blackhole: Blackhole) = init(Interface5(), true, blackhole)
    @Benchmark fun initWithoutDependencyOrderInterface20(blackhole: Blackhole) = init(Interface20(), false, blackhole)
    @Benchmark fun initWithDependencyOrderInterface20(blackhole: Blackhole) = init(Interface20(), true, blackhole)

    @Benchmark fun initWithoutDependencyOrderNestedInterface5(blackhole: Blackhole) = init(NestedInterface5(), false, blackhole)
    @Benchmark fun initWithDependencyOrderNestedInterface5(blackhole: Blackhole) = init(NestedInterface5(), true, blackhole)
    @Benchmark fun initWithoutDependencyOrderNestedInterface20(blackhole: Blackhole) = init(NestedInterface20(), false, blackhole)
    @Benchmark fun initWithDependencyOrderNestedInterface20(blackhole: Blackhole) = init(NestedInterface20(), true, blackhole)

    private fun init(
        target: Any,
        useDependencyOrder: Boolean,
        blackhole: Blackhole,
    ) {
        MockKAnnotations.init(target, useDependencyOrder = useDependencyOrder)
        blackhole.consume(target)
    }

    class Independent5 {
        @InjectMockKs lateinit var independent1: Independent1
        @InjectMockKs lateinit var independent2: Independent2
        @InjectMockKs lateinit var independent3: Independent3
        @InjectMockKs lateinit var independent4: Independent4
        @InjectMockKs lateinit var independent5: Independent5Node
    }

    class Independent20 {
        @InjectMockKs lateinit var independent1: Independent1
        @InjectMockKs lateinit var independent2: Independent2
        @InjectMockKs lateinit var independent3: Independent3
        @InjectMockKs lateinit var independent4: Independent4
        @InjectMockKs lateinit var independent5: Independent5Node
        @InjectMockKs lateinit var independent6: Independent6
        @InjectMockKs lateinit var independent7: Independent7
        @InjectMockKs lateinit var independent8: Independent8
        @InjectMockKs lateinit var independent9: Independent9
        @InjectMockKs lateinit var independent10: Independent10
        @InjectMockKs lateinit var independent11: Independent11
        @InjectMockKs lateinit var independent12: Independent12
        @InjectMockKs lateinit var independent13: Independent13
        @InjectMockKs lateinit var independent14: Independent14
        @InjectMockKs lateinit var independent15: Independent15
        @InjectMockKs lateinit var independent16: Independent16
        @InjectMockKs lateinit var independent17: Independent17
        @InjectMockKs lateinit var independent18: Independent18
        @InjectMockKs lateinit var independent19: Independent19
        @InjectMockKs lateinit var independent20: Independent20Node
    }

    class Wide5 {
        @InjectMockKs lateinit var aLeaf1: WideLeaf1
        @InjectMockKs lateinit var aLeaf2: WideLeaf2
        @InjectMockKs lateinit var aLeaf3: WideLeaf3
        @InjectMockKs lateinit var aLeaf4: WideLeaf4
        @InjectMockKs lateinit var zRoot: WideRoot5
    }

    class Wide20 {
        @InjectMockKs lateinit var aLeaf1: WideLeaf1
        @InjectMockKs lateinit var aLeaf2: WideLeaf2
        @InjectMockKs lateinit var aLeaf3: WideLeaf3
        @InjectMockKs lateinit var aLeaf4: WideLeaf4
        @InjectMockKs lateinit var aLeaf5: WideLeaf5
        @InjectMockKs lateinit var aLeaf6: WideLeaf6
        @InjectMockKs lateinit var aLeaf7: WideLeaf7
        @InjectMockKs lateinit var aLeaf8: WideLeaf8
        @InjectMockKs lateinit var aLeaf9: WideLeaf9
        @InjectMockKs lateinit var aLeaf10: WideLeaf10
        @InjectMockKs lateinit var aLeaf11: WideLeaf11
        @InjectMockKs lateinit var aLeaf12: WideLeaf12
        @InjectMockKs lateinit var aLeaf13: WideLeaf13
        @InjectMockKs lateinit var aLeaf14: WideLeaf14
        @InjectMockKs lateinit var aLeaf15: WideLeaf15
        @InjectMockKs lateinit var aLeaf16: WideLeaf16
        @InjectMockKs lateinit var aLeaf17: WideLeaf17
        @InjectMockKs lateinit var aLeaf18: WideLeaf18
        @InjectMockKs lateinit var aLeaf19: WideLeaf19
        @InjectMockKs lateinit var zRoot: WideRoot20
    }

    class Linear5 {
        @InjectMockKs lateinit var aNode5: LinearNode5
        @InjectMockKs lateinit var bNode4: LinearNode4
        @InjectMockKs lateinit var cNode3: LinearNode3
        @InjectMockKs lateinit var dNode2: LinearNode2
        @InjectMockKs lateinit var eNode1: LinearNode1
    }

    class Linear20 {
        @InjectMockKs lateinit var aNode20: LinearNode20
        @InjectMockKs lateinit var bNode19: LinearNode19
        @InjectMockKs lateinit var cNode18: LinearNode18
        @InjectMockKs lateinit var dNode17: LinearNode17
        @InjectMockKs lateinit var eNode16: LinearNode16
        @InjectMockKs lateinit var fNode15: LinearNode15
        @InjectMockKs lateinit var gNode14: LinearNode14
        @InjectMockKs lateinit var hNode13: LinearNode13
        @InjectMockKs lateinit var iNode12: LinearNode12
        @InjectMockKs lateinit var jNode11: LinearNode11
        @InjectMockKs lateinit var kNode10: LinearNode10
        @InjectMockKs lateinit var lNode9: LinearNode9
        @InjectMockKs lateinit var mNode8: LinearNode8
        @InjectMockKs lateinit var nNode7: LinearNode7
        @InjectMockKs lateinit var oNode6: LinearNode6
        @InjectMockKs lateinit var pNode5: LinearNode5
        @InjectMockKs lateinit var qNode4: LinearNode4
        @InjectMockKs lateinit var rNode3: LinearNode3
        @InjectMockKs lateinit var sNode2: LinearNode2
        @InjectMockKs lateinit var tNode1: LinearNode1
    }

    class Diamond5 {
        @InjectMockKs lateinit var aShared: DiamondShared
        @InjectMockKs lateinit var bLeft: DiamondLeft
        @InjectMockKs lateinit var cRight: DiamondRight
        @InjectMockKs lateinit var dExtra: DiamondExtra
        @InjectMockKs lateinit var zRoot: DiamondRoot5
    }

    class Diamond20 {
        @InjectMockKs lateinit var aShared: DiamondShared
        @InjectMockKs lateinit var branch1: DiamondBranch1
        @InjectMockKs lateinit var branch2: DiamondBranch2
        @InjectMockKs lateinit var branch3: DiamondBranch3
        @InjectMockKs lateinit var branch4: DiamondBranch4
        @InjectMockKs lateinit var branch5: DiamondBranch5
        @InjectMockKs lateinit var branch6: DiamondBranch6
        @InjectMockKs lateinit var branch7: DiamondBranch7
        @InjectMockKs lateinit var branch8: DiamondBranch8
        @InjectMockKs lateinit var branch9: DiamondBranch9
        @InjectMockKs lateinit var branch10: DiamondBranch10
        @InjectMockKs lateinit var branch11: DiamondBranch11
        @InjectMockKs lateinit var branch12: DiamondBranch12
        @InjectMockKs lateinit var branch13: DiamondBranch13
        @InjectMockKs lateinit var branch14: DiamondBranch14
        @InjectMockKs lateinit var branch15: DiamondBranch15
        @InjectMockKs lateinit var branch16: DiamondBranch16
        @InjectMockKs lateinit var branch17: DiamondBranch17
        @InjectMockKs lateinit var branch18: DiamondBranch18
        @InjectMockKs lateinit var zRoot: DiamondRoot20
    }

    class Interface5 {
        @InjectMockKs lateinit var aProvider1: Provider1Impl
        @InjectMockKs lateinit var aProvider2: Provider2Impl
        @InjectMockKs lateinit var aProvider3: Provider3Impl
        @InjectMockKs lateinit var aProvider4: Provider4Impl
        @InjectMockKs lateinit var aProvider5: Provider5Impl
        @InjectMockKs lateinit var zConsumer1: InterfaceConsumer1
        @InjectMockKs lateinit var zConsumer2: InterfaceConsumer2
        @InjectMockKs lateinit var zConsumer3: InterfaceConsumer3
        @InjectMockKs lateinit var zConsumer4: InterfaceConsumer4
        @InjectMockKs lateinit var zConsumer5: InterfaceConsumer5
    }

    class Interface20 {
        @InjectMockKs lateinit var aProvider1: Provider1Impl
        @InjectMockKs lateinit var aProvider2: Provider2Impl
        @InjectMockKs lateinit var aProvider3: Provider3Impl
        @InjectMockKs lateinit var aProvider4: Provider4Impl
        @InjectMockKs lateinit var aProvider5: Provider5Impl
        @InjectMockKs lateinit var aProvider6: Provider6Impl
        @InjectMockKs lateinit var aProvider7: Provider7Impl
        @InjectMockKs lateinit var aProvider8: Provider8Impl
        @InjectMockKs lateinit var aProvider9: Provider9Impl
        @InjectMockKs lateinit var aProvider10: Provider10Impl
        @InjectMockKs lateinit var aProvider11: Provider11Impl
        @InjectMockKs lateinit var aProvider12: Provider12Impl
        @InjectMockKs lateinit var aProvider13: Provider13Impl
        @InjectMockKs lateinit var aProvider14: Provider14Impl
        @InjectMockKs lateinit var aProvider15: Provider15Impl
        @InjectMockKs lateinit var aProvider16: Provider16Impl
        @InjectMockKs lateinit var aProvider17: Provider17Impl
        @InjectMockKs lateinit var aProvider18: Provider18Impl
        @InjectMockKs lateinit var aProvider19: Provider19Impl
        @InjectMockKs lateinit var aProvider20: Provider20Impl
        @InjectMockKs lateinit var zConsumer1: InterfaceConsumer1
        @InjectMockKs lateinit var zConsumer2: InterfaceConsumer2
        @InjectMockKs lateinit var zConsumer3: InterfaceConsumer3
        @InjectMockKs lateinit var zConsumer4: InterfaceConsumer4
        @InjectMockKs lateinit var zConsumer5: InterfaceConsumer5
        @InjectMockKs lateinit var zConsumer6: InterfaceConsumer6
        @InjectMockKs lateinit var zConsumer7: InterfaceConsumer7
        @InjectMockKs lateinit var zConsumer8: InterfaceConsumer8
        @InjectMockKs lateinit var zConsumer9: InterfaceConsumer9
        @InjectMockKs lateinit var zConsumer10: InterfaceConsumer10
        @InjectMockKs lateinit var zConsumer11: InterfaceConsumer11
        @InjectMockKs lateinit var zConsumer12: InterfaceConsumer12
        @InjectMockKs lateinit var zConsumer13: InterfaceConsumer13
        @InjectMockKs lateinit var zConsumer14: InterfaceConsumer14
        @InjectMockKs lateinit var zConsumer15: InterfaceConsumer15
        @InjectMockKs lateinit var zConsumer16: InterfaceConsumer16
        @InjectMockKs lateinit var zConsumer17: InterfaceConsumer17
        @InjectMockKs lateinit var zConsumer18: InterfaceConsumer18
        @InjectMockKs lateinit var zConsumer19: InterfaceConsumer19
        @InjectMockKs lateinit var zConsumer20: InterfaceConsumer20
    }

    class NestedInterface5 {
        @InjectMockKs lateinit var aProvider1: NestedProvider1Impl
        @InjectMockKs lateinit var aProvider2: NestedProvider2Impl
        @InjectMockKs lateinit var aProvider3: NestedProvider3Impl
        @InjectMockKs lateinit var aProvider4: NestedProvider4Impl
        @InjectMockKs lateinit var aProvider5: NestedProvider5Impl
        @InjectMockKs lateinit var zConsumer1: NestedConsumer1
        @InjectMockKs lateinit var zConsumer2: NestedConsumer2
        @InjectMockKs lateinit var zConsumer3: NestedConsumer3
        @InjectMockKs lateinit var zConsumer4: NestedConsumer4
        @InjectMockKs lateinit var zConsumer5: NestedConsumer5
    }

    class NestedInterface20 {
        @InjectMockKs lateinit var aProvider1: NestedProvider1Impl
        @InjectMockKs lateinit var aProvider2: NestedProvider2Impl
        @InjectMockKs lateinit var aProvider3: NestedProvider3Impl
        @InjectMockKs lateinit var aProvider4: NestedProvider4Impl
        @InjectMockKs lateinit var aProvider5: NestedProvider5Impl
        @InjectMockKs lateinit var aProvider6: NestedProvider6Impl
        @InjectMockKs lateinit var aProvider7: NestedProvider7Impl
        @InjectMockKs lateinit var aProvider8: NestedProvider8Impl
        @InjectMockKs lateinit var aProvider9: NestedProvider9Impl
        @InjectMockKs lateinit var aProvider10: NestedProvider10Impl
        @InjectMockKs lateinit var aProvider11: NestedProvider11Impl
        @InjectMockKs lateinit var aProvider12: NestedProvider12Impl
        @InjectMockKs lateinit var aProvider13: NestedProvider13Impl
        @InjectMockKs lateinit var aProvider14: NestedProvider14Impl
        @InjectMockKs lateinit var aProvider15: NestedProvider15Impl
        @InjectMockKs lateinit var aProvider16: NestedProvider16Impl
        @InjectMockKs lateinit var aProvider17: NestedProvider17Impl
        @InjectMockKs lateinit var aProvider18: NestedProvider18Impl
        @InjectMockKs lateinit var aProvider19: NestedProvider19Impl
        @InjectMockKs lateinit var aProvider20: NestedProvider20Impl
        @InjectMockKs lateinit var zConsumer1: NestedConsumer1
        @InjectMockKs lateinit var zConsumer2: NestedConsumer2
        @InjectMockKs lateinit var zConsumer3: NestedConsumer3
        @InjectMockKs lateinit var zConsumer4: NestedConsumer4
        @InjectMockKs lateinit var zConsumer5: NestedConsumer5
        @InjectMockKs lateinit var zConsumer6: NestedConsumer6
        @InjectMockKs lateinit var zConsumer7: NestedConsumer7
        @InjectMockKs lateinit var zConsumer8: NestedConsumer8
        @InjectMockKs lateinit var zConsumer9: NestedConsumer9
        @InjectMockKs lateinit var zConsumer10: NestedConsumer10
        @InjectMockKs lateinit var zConsumer11: NestedConsumer11
        @InjectMockKs lateinit var zConsumer12: NestedConsumer12
        @InjectMockKs lateinit var zConsumer13: NestedConsumer13
        @InjectMockKs lateinit var zConsumer14: NestedConsumer14
        @InjectMockKs lateinit var zConsumer15: NestedConsumer15
        @InjectMockKs lateinit var zConsumer16: NestedConsumer16
        @InjectMockKs lateinit var zConsumer17: NestedConsumer17
        @InjectMockKs lateinit var zConsumer18: NestedConsumer18
        @InjectMockKs lateinit var zConsumer19: NestedConsumer19
        @InjectMockKs lateinit var zConsumer20: NestedConsumer20
    }

    class Independent1
    class Independent2
    class Independent3
    class Independent4
    class Independent5Node
    class Independent6
    class Independent7
    class Independent8
    class Independent9
    class Independent10
    class Independent11
    class Independent12
    class Independent13
    class Independent14
    class Independent15
    class Independent16
    class Independent17
    class Independent18
    class Independent19
    class Independent20Node

    class WideLeaf1
    class WideLeaf2
    class WideLeaf3
    class WideLeaf4
    class WideLeaf5
    class WideLeaf6
    class WideLeaf7
    class WideLeaf8
    class WideLeaf9
    class WideLeaf10
    class WideLeaf11
    class WideLeaf12
    class WideLeaf13
    class WideLeaf14
    class WideLeaf15
    class WideLeaf16
    class WideLeaf17
    class WideLeaf18
    class WideLeaf19
    class WideRoot5(val leaf1: WideLeaf1, val leaf2: WideLeaf2, val leaf3: WideLeaf3, val leaf4: WideLeaf4)
    class WideRoot20(
        val leaf1: WideLeaf1, val leaf2: WideLeaf2, val leaf3: WideLeaf3, val leaf4: WideLeaf4, val leaf5: WideLeaf5,
        val leaf6: WideLeaf6, val leaf7: WideLeaf7, val leaf8: WideLeaf8, val leaf9: WideLeaf9, val leaf10: WideLeaf10,
        val leaf11: WideLeaf11, val leaf12: WideLeaf12, val leaf13: WideLeaf13, val leaf14: WideLeaf14,
        val leaf15: WideLeaf15, val leaf16: WideLeaf16, val leaf17: WideLeaf17, val leaf18: WideLeaf18,
        val leaf19: WideLeaf19,
    )

    class LinearNode1(val next: LinearNode2)
    class LinearNode2(val next: LinearNode3)
    class LinearNode3(val next: LinearNode4)
    class LinearNode4(val next: LinearNode5)
    class LinearNode5(val next: LinearNode6? = null)
    class LinearNode6(val next: LinearNode7)
    class LinearNode7(val next: LinearNode8)
    class LinearNode8(val next: LinearNode9)
    class LinearNode9(val next: LinearNode10)
    class LinearNode10(val next: LinearNode11)
    class LinearNode11(val next: LinearNode12)
    class LinearNode12(val next: LinearNode13)
    class LinearNode13(val next: LinearNode14)
    class LinearNode14(val next: LinearNode15)
    class LinearNode15(val next: LinearNode16)
    class LinearNode16(val next: LinearNode17)
    class LinearNode17(val next: LinearNode18)
    class LinearNode18(val next: LinearNode19)
    class LinearNode19(val next: LinearNode20)
    class LinearNode20

    class DiamondShared
    class DiamondLeft(val shared: DiamondShared)
    class DiamondRight(val shared: DiamondShared)
    class DiamondExtra(val shared: DiamondShared)
    class DiamondRoot5(val left: DiamondLeft, val right: DiamondRight, val extra: DiamondExtra)
    class DiamondBranch1(val shared: DiamondShared)
    class DiamondBranch2(val shared: DiamondShared)
    class DiamondBranch3(val shared: DiamondShared)
    class DiamondBranch4(val shared: DiamondShared)
    class DiamondBranch5(val shared: DiamondShared)
    class DiamondBranch6(val shared: DiamondShared)
    class DiamondBranch7(val shared: DiamondShared)
    class DiamondBranch8(val shared: DiamondShared)
    class DiamondBranch9(val shared: DiamondShared)
    class DiamondBranch10(val shared: DiamondShared)
    class DiamondBranch11(val shared: DiamondShared)
    class DiamondBranch12(val shared: DiamondShared)
    class DiamondBranch13(val shared: DiamondShared)
    class DiamondBranch14(val shared: DiamondShared)
    class DiamondBranch15(val shared: DiamondShared)
    class DiamondBranch16(val shared: DiamondShared)
    class DiamondBranch17(val shared: DiamondShared)
    class DiamondBranch18(val shared: DiamondShared)
    class DiamondRoot20(
        val branch1: DiamondBranch1, val branch2: DiamondBranch2, val branch3: DiamondBranch3, val branch4: DiamondBranch4,
        val branch5: DiamondBranch5, val branch6: DiamondBranch6, val branch7: DiamondBranch7, val branch8: DiamondBranch8,
        val branch9: DiamondBranch9, val branch10: DiamondBranch10, val branch11: DiamondBranch11,
        val branch12: DiamondBranch12, val branch13: DiamondBranch13, val branch14: DiamondBranch14,
        val branch15: DiamondBranch15, val branch16: DiamondBranch16, val branch17: DiamondBranch17,
        val branch18: DiamondBranch18,
    )

    interface Provider1
    interface Provider2
    interface Provider3
    interface Provider4
    interface Provider5
    interface Provider6
    interface Provider7
    interface Provider8
    interface Provider9
    interface Provider10
    interface Provider11
    interface Provider12
    interface Provider13
    interface Provider14
    interface Provider15
    interface Provider16
    interface Provider17
    interface Provider18
    interface Provider19
    interface Provider20
    class Provider1Impl : Provider1
    class Provider2Impl : Provider2
    class Provider3Impl : Provider3
    class Provider4Impl : Provider4
    class Provider5Impl : Provider5
    class Provider6Impl : Provider6
    class Provider7Impl : Provider7
    class Provider8Impl : Provider8
    class Provider9Impl : Provider9
    class Provider10Impl : Provider10
    class Provider11Impl : Provider11
    class Provider12Impl : Provider12
    class Provider13Impl : Provider13
    class Provider14Impl : Provider14
    class Provider15Impl : Provider15
    class Provider16Impl : Provider16
    class Provider17Impl : Provider17
    class Provider18Impl : Provider18
    class Provider19Impl : Provider19
    class Provider20Impl : Provider20
    class InterfaceConsumer1(val provider: Provider1)
    class InterfaceConsumer2(val provider: Provider2)
    class InterfaceConsumer3(val provider: Provider3)
    class InterfaceConsumer4(val provider: Provider4)
    class InterfaceConsumer5(val provider: Provider5)
    class InterfaceConsumer6(val provider: Provider6)
    class InterfaceConsumer7(val provider: Provider7)
    class InterfaceConsumer8(val provider: Provider8)
    class InterfaceConsumer9(val provider: Provider9)
    class InterfaceConsumer10(val provider: Provider10)
    class InterfaceConsumer11(val provider: Provider11)
    class InterfaceConsumer12(val provider: Provider12)
    class InterfaceConsumer13(val provider: Provider13)
    class InterfaceConsumer14(val provider: Provider14)
    class InterfaceConsumer15(val provider: Provider15)
    class InterfaceConsumer16(val provider: Provider16)
    class InterfaceConsumer17(val provider: Provider17)
    class InterfaceConsumer18(val provider: Provider18)
    class InterfaceConsumer19(val provider: Provider19)
    class InterfaceConsumer20(val provider: Provider20)

    interface NestedRoot1
    interface NestedRoot2
    interface NestedRoot3
    interface NestedRoot4
    interface NestedRoot5
    interface NestedRoot6
    interface NestedRoot7
    interface NestedRoot8
    interface NestedRoot9
    interface NestedRoot10
    interface NestedRoot11
    interface NestedRoot12
    interface NestedRoot13
    interface NestedRoot14
    interface NestedRoot15
    interface NestedRoot16
    interface NestedRoot17
    interface NestedRoot18
    interface NestedRoot19
    interface NestedRoot20
    interface NestedProvider1Level1 : NestedRoot1
    interface NestedProvider1Level2 : NestedProvider1Level1
    interface NestedProvider1Level3 : NestedProvider1Level2
    interface NestedProvider1 : NestedProvider1Level3
    interface NestedProvider2Level1 : NestedRoot2
    interface NestedProvider2Level2 : NestedProvider2Level1
    interface NestedProvider2Level3 : NestedProvider2Level2
    interface NestedProvider2 : NestedProvider2Level3
    interface NestedProvider3Level1 : NestedRoot3
    interface NestedProvider3Level2 : NestedProvider3Level1
    interface NestedProvider3Level3 : NestedProvider3Level2
    interface NestedProvider3 : NestedProvider3Level3
    interface NestedProvider4Level1 : NestedRoot4
    interface NestedProvider4Level2 : NestedProvider4Level1
    interface NestedProvider4Level3 : NestedProvider4Level2
    interface NestedProvider4 : NestedProvider4Level3
    interface NestedProvider5Level1 : NestedRoot5
    interface NestedProvider5Level2 : NestedProvider5Level1
    interface NestedProvider5Level3 : NestedProvider5Level2
    interface NestedProvider5 : NestedProvider5Level3
    interface NestedProvider6Level1 : NestedRoot6
    interface NestedProvider6Level2 : NestedProvider6Level1
    interface NestedProvider6Level3 : NestedProvider6Level2
    interface NestedProvider6 : NestedProvider6Level3
    interface NestedProvider7Level1 : NestedRoot7
    interface NestedProvider7Level2 : NestedProvider7Level1
    interface NestedProvider7Level3 : NestedProvider7Level2
    interface NestedProvider7 : NestedProvider7Level3
    interface NestedProvider8Level1 : NestedRoot8
    interface NestedProvider8Level2 : NestedProvider8Level1
    interface NestedProvider8Level3 : NestedProvider8Level2
    interface NestedProvider8 : NestedProvider8Level3
    interface NestedProvider9Level1 : NestedRoot9
    interface NestedProvider9Level2 : NestedProvider9Level1
    interface NestedProvider9Level3 : NestedProvider9Level2
    interface NestedProvider9 : NestedProvider9Level3
    interface NestedProvider10Level1 : NestedRoot10
    interface NestedProvider10Level2 : NestedProvider10Level1
    interface NestedProvider10Level3 : NestedProvider10Level2
    interface NestedProvider10 : NestedProvider10Level3
    interface NestedProvider11Level1 : NestedRoot11
    interface NestedProvider11Level2 : NestedProvider11Level1
    interface NestedProvider11Level3 : NestedProvider11Level2
    interface NestedProvider11 : NestedProvider11Level3
    interface NestedProvider12Level1 : NestedRoot12
    interface NestedProvider12Level2 : NestedProvider12Level1
    interface NestedProvider12Level3 : NestedProvider12Level2
    interface NestedProvider12 : NestedProvider12Level3
    interface NestedProvider13Level1 : NestedRoot13
    interface NestedProvider13Level2 : NestedProvider13Level1
    interface NestedProvider13Level3 : NestedProvider13Level2
    interface NestedProvider13 : NestedProvider13Level3
    interface NestedProvider14Level1 : NestedRoot14
    interface NestedProvider14Level2 : NestedProvider14Level1
    interface NestedProvider14Level3 : NestedProvider14Level2
    interface NestedProvider14 : NestedProvider14Level3
    interface NestedProvider15Level1 : NestedRoot15
    interface NestedProvider15Level2 : NestedProvider15Level1
    interface NestedProvider15Level3 : NestedProvider15Level2
    interface NestedProvider15 : NestedProvider15Level3
    interface NestedProvider16Level1 : NestedRoot16
    interface NestedProvider16Level2 : NestedProvider16Level1
    interface NestedProvider16Level3 : NestedProvider16Level2
    interface NestedProvider16 : NestedProvider16Level3
    interface NestedProvider17Level1 : NestedRoot17
    interface NestedProvider17Level2 : NestedProvider17Level1
    interface NestedProvider17Level3 : NestedProvider17Level2
    interface NestedProvider17 : NestedProvider17Level3
    interface NestedProvider18Level1 : NestedRoot18
    interface NestedProvider18Level2 : NestedProvider18Level1
    interface NestedProvider18Level3 : NestedProvider18Level2
    interface NestedProvider18 : NestedProvider18Level3
    interface NestedProvider19Level1 : NestedRoot19
    interface NestedProvider19Level2 : NestedProvider19Level1
    interface NestedProvider19Level3 : NestedProvider19Level2
    interface NestedProvider19 : NestedProvider19Level3
    interface NestedProvider20Level1 : NestedRoot20
    interface NestedProvider20Level2 : NestedProvider20Level1
    interface NestedProvider20Level3 : NestedProvider20Level2
    interface NestedProvider20 : NestedProvider20Level3
    class NestedProvider1Impl : NestedProvider1
    class NestedProvider2Impl : NestedProvider2
    class NestedProvider3Impl : NestedProvider3
    class NestedProvider4Impl : NestedProvider4
    class NestedProvider5Impl : NestedProvider5
    class NestedProvider6Impl : NestedProvider6
    class NestedProvider7Impl : NestedProvider7
    class NestedProvider8Impl : NestedProvider8
    class NestedProvider9Impl : NestedProvider9
    class NestedProvider10Impl : NestedProvider10
    class NestedProvider11Impl : NestedProvider11
    class NestedProvider12Impl : NestedProvider12
    class NestedProvider13Impl : NestedProvider13
    class NestedProvider14Impl : NestedProvider14
    class NestedProvider15Impl : NestedProvider15
    class NestedProvider16Impl : NestedProvider16
    class NestedProvider17Impl : NestedProvider17
    class NestedProvider18Impl : NestedProvider18
    class NestedProvider19Impl : NestedProvider19
    class NestedProvider20Impl : NestedProvider20
    class NestedConsumer1(val provider: NestedRoot1)
    class NestedConsumer2(val provider: NestedRoot2)
    class NestedConsumer3(val provider: NestedRoot3)
    class NestedConsumer4(val provider: NestedRoot4)
    class NestedConsumer5(val provider: NestedRoot5)
    class NestedConsumer6(val provider: NestedRoot6)
    class NestedConsumer7(val provider: NestedRoot7)
    class NestedConsumer8(val provider: NestedRoot8)
    class NestedConsumer9(val provider: NestedRoot9)
    class NestedConsumer10(val provider: NestedRoot10)
    class NestedConsumer11(val provider: NestedRoot11)
    class NestedConsumer12(val provider: NestedRoot12)
    class NestedConsumer13(val provider: NestedRoot13)
    class NestedConsumer14(val provider: NestedRoot14)
    class NestedConsumer15(val provider: NestedRoot15)
    class NestedConsumer16(val provider: NestedRoot16)
    class NestedConsumer17(val provider: NestedRoot17)
    class NestedConsumer18(val provider: NestedRoot18)
    class NestedConsumer19(val provider: NestedRoot19)
    class NestedConsumer20(val provider: NestedRoot20)
}
