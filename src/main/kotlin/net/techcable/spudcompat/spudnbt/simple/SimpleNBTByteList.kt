package net.techcable.spudcompat.spudnbt.simple

import com.google.common.base.Preconditions.checkElementIndex
import com.google.common.base.Preconditions.checkPositionIndex
import net.techcable.spudcompat.spudnbt.NBTByteList
import net.techcable.spudcompat.spudnbt.NBTNumber
import net.techcable.spudcompat.spudnbt.createByte
import java.util.*

val EMPTY_BYTE_ARRAY = byteArrayOf()

internal final class SimpleNBTByteList private constructor(var array: ByteArray, override var size: Int): AbstractList<NBTNumber<Byte>>(), NBTByteList {
    init {
        require(size <= array.size)
    }

    override fun clone() = SimpleNBTByteList(this.array.copyOf(size))

    override fun forEachByte(action: (Byte) -> Unit) {
        for (i in 0..(size - 1)) {
            action(array[i])
        }
    }

    override fun get(index: Int) = createByte(getByte(index))

    override fun getByte(index: Int) = array[index]

    override fun set(index: Int, element: NBTNumber<Byte>): NBTNumber<Byte> {
        val oldValue = get(index)
        set(index,  element.value)
        return oldValue
    }

    override fun set(index: Int, element: Byte) {
        array[index] = element
    }

    override fun contains(element: NBTNumber<Byte>) = array.contains(element.value)

    override fun byteIterator() = array.iterator()

    override fun add(element: Byte): Boolean {
        if (size + 1 >= array.size) array = array.copyOf(size + 10)
        array[size++] = element
        return true
    }

    override fun add(index: Int, element: NBTNumber<Byte>) {
        checkPositionIndex(index, size)
        if (index >= array.size) array = array.copyOf(index + 10)
        System.arraycopy(array, index, array, index + 1, size - index)
        array[index] = element.value
        size++
    }

    override fun removeAt(index: Int): NBTNumber<Byte> {
        checkElementIndex(index, size)
        val oldValue = array[index]
        removeAt0(index)
        return createByte(oldValue)
    }

    fun removeAt0(index: Int) {
        val numMoved = size - index - 1
        if (numMoved > 0) {
            System.arraycopy(array, index + 1, array, index, numMoved)
        }
    }

    private constructor() : this(EMPTY_BYTE_ARRAY)

    constructor(array: ByteArray) : this(if (array.size == 0) EMPTY_BYTE_ARRAY else array, array.size)

    override fun addAll(index: Int, elements: Collection<NBTNumber<Byte>>) = addAll(index, elements.map(NBTNumber<Byte>::value).toByteArray())

    fun addAll(index: Int, elements: ByteArray): Boolean {
        if (size + elements.size >= array.size) array.copyOf(size + elements.size + 10)
        if (size > index) {
            // Move stuff out of the way
            System.arraycopy(array, index, array, index + elements.size, size - index)
        }
        System.arraycopy(elements, 0, array, index, elements.size)
        return elements.size > 0
    }

    override fun addAll(elements: Collection<NBTNumber<Byte>>) = addAll(elements.map(NBTNumber<Byte>::value).toByteArray())

    fun addAll(elements: ByteArray): Boolean {
        if (size + elements.size >= array.size) array.copyOf(size + elements.size + 10)
        System.arraycopy(elements, 0, array, size, elements.size)
        return elements.size > 0
    }

    override fun clear() {
        size = 0
        array = EMPTY_BYTE_ARRAY
    }

    override fun remove(element: NBTNumber<Byte>): Boolean {
        val index = indexOf(element)
        if (index >= 0) {
            removeAt0(index)
            return true
        } else {
            return false
        }
    }

    override fun removeIf(predicate: (Byte) -> Boolean): Boolean {
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

    override fun toByteArray() = array.copyOf(size)

    override fun equals(other: Any?) =  other is NBTByteList && super.equals(other)
}
