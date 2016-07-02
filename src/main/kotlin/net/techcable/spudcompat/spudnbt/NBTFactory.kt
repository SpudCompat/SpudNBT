package net.techcable.spudcompat.spudnbt

import net.techcable.spudcompat.spudnbt.immutable.ImmutableNBTByte
import net.techcable.spudcompat.spudnbt.immutable.ImmutableNBTCompound
import net.techcable.spudcompat.spudnbt.immutable.ImmutableNBTInt
import net.techcable.spudcompat.spudnbt.immutable.ImmutableNBTList
import net.techcable.spudcompat.spudnbt.simple.SimpleNBTList

fun <T: NBT> createNbtList(list: List<T>, elementType: NBTType): NBTList<T> = ImmutableNBTList(list, elementType)

fun <T: NBT> createMutableNbtList(list: List<T>, elementType: NBTType): MutableNBTList<T> = SimpleNBTList(list, elementType)

fun <T: NBT> createNBTCompound(map: Map<String, T>): NBTCompound = ImmutableNBTCompound(map)

fun createNbtByte(b: Byte): NBTPrimitive<Byte> = ImmutableNBTByte(b)

fun createNbtInt(i: Int): NBTPrimitive<Int> = ImmutableNBTInt(i)