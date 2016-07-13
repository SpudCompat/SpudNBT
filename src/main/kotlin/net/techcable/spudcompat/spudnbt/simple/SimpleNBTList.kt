package net.techcable.spudcompat.spudnbt.simple

import com.google.common.collect.ForwardingIterator
import com.google.common.collect.ForwardingList
import com.google.common.collect.ForwardingListIterator
import net.techcable.spudcompat.spudnbt.NBT
import net.techcable.spudcompat.spudnbt.NBTList
import net.techcable.spudcompat.spudnbt.NBTType
import java.util.*

internal final class SimpleNBTList<T : NBT> private constructor(val delegate: ArrayList<T>, override val elementType: NBTType) : ForwardingList<T>(), NBTList<T>, RandomAccess {

    override fun delegate() = delegate

    @Suppress("UNCHECKED_CAST") // Its safe
    override fun clone(): NBTList<T> = SimpleNBTList(ArrayList(delegate), elementType)


    constructor(elementType: NBTType): this(ArrayList(), elementType)

    constructor(capacity: Int, elementType: NBTType): this(ArrayList(capacity), elementType)

    constructor(list: List<T>, elementType: NBTType) : this(ArrayList(list), elementType) {
        elementType.verifyMatchesAll(list)
    }

    override fun forEach(action: (T) -> Unit) {
        delegate.forEach({ action(it) })
    }

    override fun add(element: T) = delegate.add(elementType.verifyMatches(element))

    override fun add(index: Int, element: T) = delegate.add(index, elementType.verifyMatches(element))

    override fun addAll(index: Int, elements: Collection<T>) = delegate.addAll(index, elementType.verifyMatchesAll(elements))

    override fun addAll(elements: Collection<T>) = delegate.addAll(elementType.verifyMatchesAll(elements))

    override fun listIterator(): MutableListIterator<T> {
        val delegate = delegate.listIterator()
        return object : ForwardingListIterator<T>() {
            override fun delegate() = delegate

            override fun set(element: T) {
                delegate.set(elementType.verifyMatches(element))
            }

            override fun add(element: T) {
                super.add(elementType.verifyMatches(element))
            }
        }
    }

    override fun listIterator(index: Int): MutableListIterator<T> {
        val delegate = delegate.listIterator()
        return object : ForwardingListIterator<T>() {
            override fun delegate() = delegate

            override fun set(element: T) {
                delegate.set(elementType.verifyMatches(element))
            }

            override fun add(element: T) {
                super.add(elementType.verifyMatches(element))
            }
        }
    }

    override fun set(index: Int, element: T): T {
        return delegate.set(index, elementType.verifyMatches(element))
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
        return super.standardSubList(fromIndex, toIndex) // mo-safety
    }
}