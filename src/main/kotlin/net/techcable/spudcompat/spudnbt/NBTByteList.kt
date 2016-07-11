package net.techcable.spudcompat.spudnbt

import io.netty.buffer.ByteBuf
import net.techcable.spudcompat.spudnbt.simple.SimpleNBTByteList
import java.io.DataOutput
import java.util.*
import java.util.function.Predicate

interface NBTByteList : NBTList<NBTNumber<Byte>> {

    override fun clone(): NBTByteList

    override fun writeValue(out: DataOutput) {
        out.writeInt(size)
        forEachByte { out.writeByte(it.toInt()) }
    }

    override fun writeValue(buffer: ByteBuf) {
        buffer.writeInt(size)
        forEachByte { buffer.writeByte(it.toInt()) }
    }

    override val elementType: NBTType
        get() = NBTType.BYTE

    override val type: NBTType
        get() = NBTType.BYTE_ARRAY

    // Primitive overloads

    override fun forEach(action: (NBTNumber<Byte>) -> Unit) {
        forEachByte { action(createByte(it)) }
    }

    fun forEachByte(action: (Byte) -> Unit)

    fun byteIterator(): ByteIterator = object : ByteIterator() {
        var index = 0

        override fun nextByte(): Byte {
            if (index >= size) throw NoSuchElementException()
            return getByte(index)
        }

        override fun hasNext() = index < size
    }

    fun getByte(index: Int): Byte

    operator fun set(index: Int, element: Byte)

    fun add(element: Byte): Boolean

    // Should override java 8's List.removeIf
    fun removeIf(predicate: Predicate<in NBTNumber<Byte>>) = removeIf { predicate.test(createByte(it)) }

    /**
     * Removes all of the elements of this collection that match the predicate.
     *
     * @return if any elements were removed
     */
    fun removeIf(predicate: (Byte) -> Boolean): Boolean

}