package net.techcable.spudcompat.spudnbt.simple

import com.google.common.base.Preconditions.checkElementIndex
import com.google.common.base.Preconditions.checkPositionIndex
import net.techcable.spudcompat.spudnbt.NBTIntList
import net.techcable.spudcompat.spudnbt.NBTNumber
import net.techcable.spudcompat.spudnbt.createInt
import java.util.*

val EMPTY_INT_ARRAY = intArrayOf()

internal final class SimpleNBTIntList private constructor(var array: IntArray, override var size: Int): AbstractList<NBTNumber<Int>>(), NBTIntList {
    init {
        require(size <= array.size)
    }

    override fun clone() = SimpleNBTIntList(this.array.copyOf(size))

    override fun forEachInt(action: (Int) -> Unit) {
        for (i in 0..(size - 1)) {
            action(array[i])
        }
    }

    override fun get(index: Int) = createInt(getInt(index))

    override fun getInt(index: Int) = array[index]

    override fun set(index: Int, element: NBTNumber<Int>): NBTNumber<Int> {
        val oldValue = get(index)
        set(index,  element.value)
        return oldValue
    }

    override fun set(index: Int, element: Int) {
        array[index] = element
    }

    override fun contains(element: NBTNumber<Int>) = array.contains(element.value)

    override fun intIterator() = array.iterator()

    override fun add(element: Int): Boolean {
        if (size + 1 >= array.size) array = array.copyOf(size + 10)
        array[size++] = element
        return true
    }

    override fun add(index: Int, element: NBTNumber<Int>) {
        checkPositionIndex(index, size)
        if (index >= array.size) array = array.copyOf(index + 10)
        System.arraycopy(array, index, array, index + 1, size - index)
        array[index] = element.value
        size++
    }

    override fun removeAt(index: Int): NBTNumber<Int> {
        checkElementIndex(index, size)
        val oldValue = array[index]
        removeAt0(index)
        return createInt(oldValue)
    }

    fun removeAt0(index: Int) {
        val numMoved = size - index - 1
        if (numMoved > 0) {
            System.arraycopy(array, index + 1, array, index, numMoved)
        }
    }

    private constructor() : this(EMPTY_INT_ARRAY, 0)

    constructor(array: IntArray) : this(if (array.size == 0) EMPTY_INT_ARRAY else array, array.size)

    override fun addAll(index: Int, elements: Collection<NBTNumber<Int>>) = addAll(index, elements.map(NBTNumber<Int>::value).toIntArray())

    fun addAll(index: Int, elements: IntArray): Boolean {
        if (size + elements.size >= array.size) array.copyOf(size + elements.size + 10)
        if (size > index) {
            // Move stuff out of the way
            System.arraycopy(array, index, array, index + elements.size, size - index)
        }
        System.arraycopy(elements, 0, array, index, elements.size)
        return elements.size > 0
    }

    override fun addAll(elements: Collection<NBTNumber<Int>>) = addAll(elements.map(NBTNumber<Int>::value).toIntArray())

    fun addAll(elements: IntArray): Boolean {
        if (size + elements.size >= array.size) array.copyOf(size + elements.size + 10)
        System.arraycopy(elements, 0, array, size, elements.size)
        return elements.size > 0
    }

    override fun clear() {
        size = 0
        array = EMPTY_INT_ARRAY
    }

    override fun remove(element: NBTNumber<Int>): Boolean {
        val index = indexOf(element)
        if (index >= 0) {
            removeAt0(index)
            return true
        } else {
            return false
        }
    }

    override fun removeIf(predicate: (Int) -> Boolean): Boolean {
        val array = array
        val modified: Boolean;
        var oldIndex = 0
        var newIndex = 0
        try {
            while (oldIndex < size) {
                val element = array[oldIndex]
                if (!predicate(element)) {
                    array[newIndex++] = element
                }
                oldIndex++
            }
        } finally {
            /*
             * Looks like the predicate threw an exception -_-
             * Copy whatever the predicate didn't handle and adjust the new index accordingly
             */
            if (oldIndex != size) {
                System.arraycopy(array, oldIndex, array, newIndex, size - oldIndex)
                newIndex += size - oldIndex
            }
            // Yay, stuff got removed!
            if (newIndex != size) {
                size = newIndex
                modified = true
            } else {
                modified = false
            }
        }
        return modified
    }

    override fun toIntArray() = array.copyOf(size)

    override fun equals(other: Any?) = other is NBTIntList && super.equals(other)
}