package net.techcable.spudcompat.spudnbt

import io.netty.buffer.ByteBuf
import net.techcable.spudcompat.spudnbt.simple.SimpleNBTByteList
import net.techcable.spudcompat.spudnbt.simple.SimpleNBTInt
import net.techcable.spudcompat.spudnbt.simple.SimpleNBTIntList
import java.io.DataOutput
import java.util.*
import java.util.function.Predicate

interface NBTIntList : NBTList<NBTNumber<Int>> {

    override fun writeValue(out: DataOutput) {
        out.writeInt(size)
        forEachInt { out.writeInt(it) }
    }

    override fun writeValue(buffer: ByteBuf) {
        buffer.writeInt(size)
        forEachInt { buffer.writeInt(it) }
    }

    override val elementType: NBTType
        get() = NBTType.INT

    override val type: NBTType
        get() = NBTType.INT_ARRAY

    // Primitive overloads

    override fun forEach(action: (NBTNumber<Int>) -> Unit) {
        forEachInt { action(createInt(it)) }
    }

    fun forEachInt(action: (Int) -> Unit)

    fun intIterator(): IntIterator = object : IntIterator() {
        var index = 0

        override fun nextInt(): Int {
            if (index >= size) throw NoSuchElementException()
            return getInt(index)
        }

        override fun hasNext() = index < size
    }

    fun getInt(index: Int): Int

    operator fun set(index: Int, element: Int)

    fun add(element: Int): Boolean

    // Should override java 8's List.removeIf
    fun removeIf(predicate: Predicate<in NBTNumber<Int>>) = removeIf { predicate.test(createInt(it)) }

    /**
     * Removes all of the elements of this collection that match the predicate.
     *
     * @return if any elements were removed
     */
    fun removeIf(predicate: (Int) -> Boolean): Boolean

}