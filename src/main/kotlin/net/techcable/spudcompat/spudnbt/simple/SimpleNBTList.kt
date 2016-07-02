package net.techcable.spudcompat.spudnbt.simple

import com.google.common.collect.ForwardingList
import net.techcable.spudcompat.spudnbt.MutableNBTList
import net.techcable.spudcompat.spudnbt.NBT
import net.techcable.spudcompat.spudnbt.NBTType
import net.techcable.spudcompat.spudnbt.immutable.ImmutableNBTList
import net.techcable.spudcompat.spudnbt.verifyListType
import java.util.*

internal class SimpleNBTList<T: NBT> : ForwardingList<T>, MutableNBTList<T> {

    override fun delegate() = list

    override val elementType: NBTType
    private val list: ArrayList<T>

    @Suppress("UNCHECKED_CAST") // Its safe
    override fun clone() = SimpleNBTList(list.clone() as ArrayList<T>, elementType)

    private constructor(arrayList: ArrayList<T>, elementType: NBTType) {
        this.list = arrayList
        this.elementType = elementType
    }

    constructor(list: List<T>, elementType: NBTType) : this(ArrayList(list), elementType) {
        verifyListType(list, elementType)
    }

    override fun snapshot() = ImmutableNBTList(list, elementType)

    override fun forEach(action: (T) -> Unit) {
        list.forEach({ action(it) })
    }
}