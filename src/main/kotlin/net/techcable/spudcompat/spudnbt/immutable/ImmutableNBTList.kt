package net.techcable.spudcompat.spudnbt.immutable

import com.google.common.collect.ForwardingList
import com.google.common.collect.ImmutableList
import net.techcable.spudcompat.spudnbt.*
import net.techcable.spudcompat.spudnbt.simple.SimpleNBTList

internal final class ImmutableNBTList<T : NBT> private constructor(var list: ImmutableList<T>, override var elementType: NBTType) : ForwardingList<T>(), NBTList<T> {

    override fun forEach(action: (T) -> Unit) {
        for (i in 0..list.size) {
            action(list[i])
        }
    }

    override fun delegate() = list

    override fun asMutable(): MutableNBTList<T> = SimpleNBTList(list, elementType)

    // We're immutable :D
    override fun snapshot() = this

    override fun clone() = this

    constructor(list: List<T>, elementType: NBTType) : this(ImmutableList.copyOf(list), elementType) {
        verifyListType(list, elementType)
    }
}