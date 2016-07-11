@file:JvmName("NBTFactory")

package net.techcable.spudcompat.spudnbt

import net.techcable.spudcompat.spudnbt.simple.*

fun <T : NBT> createList(list: List<T>, elementType: NBTType): NBTList<T> = SimpleNBTList(list, elementType)

fun createIntList(ints: Collection<NBTNumber<Int>>) = createIntList(ints.map(NBTNumber<Int>::value).toIntArray())

fun createIntList(ints: IntArray): NBTIntList = SimpleNBTIntList(ints)

fun createByteList(bytes: Collection<NBTNumber<Byte>>) = createByteList(bytes.map(NBTNumber<Byte>::value).toByteArray())

fun createByteList(bytes: ByteArray): NBTByteList = SimpleNBTByteList(bytes)

fun createCompound(elements: Map<String, NBT>): NBTCompound = SimpleNBTCompound(elements)

fun createString(s: String): NBTPrimitive<String> = SimpleNBTString(s)

fun createByte(b: Byte): NBTNumber<Byte> = SimpleNBTByte(b)
fun createShort(s: Short): NBTNumber<Short> = SimpleNBTShort(s)
fun createInt(i: Int): NBTNumber<Int> = SimpleNBTInt(i)
fun createLong(l: Long): NBTNumber<Long> = SimpleNBTLong(l)
fun createFloat(f: Float): NBTNumber<Float> = SimpleNBTFloat(f)
fun createDouble(d: Double): NBTNumber<Double> = SimpleNBTDouble(d)