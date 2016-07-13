@file:JvmName("NBTFactory")

package net.techcable.spudcompat.spudnbt

import com.google.common.collect.Maps
import com.google.common.primitives.Bytes
import net.techcable.spudcompat.spudnbt.simple.*
import java.util.*

fun createShortList(vararg elements: Short) = createList(NBTType.SHORT, elements.map { createShort(it) })

fun createLongList(vararg elements: Long) = createList(NBTType.LONG, elements.map { createLong(it) })

fun createFloatList(vararg elements: Float) = createList(NBTType.FLOAT, elements.map { createFloat(it) })

fun createDoubleList(vararg elements: Double) = createList(NBTType.DOUBLE, elements.map { createDouble(it) })

fun createStringList(vararg elements: String) = createList(NBTType.STRING, elements.map { createString(it) })

fun <T: NBT> createList(elementType: NBTType, vararg elements: T): NBTList<T> {
    val list = createList<T>(elementType, elements.size)
    elements.forEach({ list.add(it) })
    return list
}

fun <T: NBT> createList(elementType: NBTType, capacity: Int): NBTList<T> = SimpleNBTList(capacity, elementType)

fun <T : NBT> createList(elementType: NBTType): NBTList<T> = SimpleNBTList(elementType)

fun <T : NBT> createList(elementType: NBTType, list: List<T>): NBTList<T> = SimpleNBTList(list, elementType)

fun createIntList(ints: Collection<NBTNumber<Int>>) = createIntList(*ints.map(NBTNumber<Int>::value).toIntArray())

fun createIntList(vararg ints: Int): NBTIntList = SimpleNBTIntList(ints)

fun createByteList(bytes: Collection<NBTNumber<Byte>>) = createByteList(*bytes.map(NBTNumber<Byte>::value).toByteArray())

fun createByteList(vararg bytes: Byte): NBTByteList = SimpleNBTByteList(bytes)

fun createCompound(initialCapacity: Int): NBTCompound = SimpleNBTCompound(initialCapacity)

fun createCompound(): NBTCompound = SimpleNBTCompound()

fun createCompound(elements: Map<String, NBT>): NBTCompound = SimpleNBTCompound(elements)

fun createString(s: String): NBTPrimitive<String> = SimpleNBTString(s)

fun createByte(b: Byte): NBTNumber<Byte> = SimpleNBTByte(b)
fun createShort(s: Short): NBTNumber<Short> = SimpleNBTShort(s)
fun createInt(i: Int): NBTNumber<Int> = SimpleNBTInt(i)
fun createLong(l: Long): NBTNumber<Long> = SimpleNBTLong(l)
fun createFloat(f: Float): NBTNumber<Float> = SimpleNBTFloat(f)
fun createDouble(d: Double): NBTNumber<Double> = SimpleNBTDouble(d)