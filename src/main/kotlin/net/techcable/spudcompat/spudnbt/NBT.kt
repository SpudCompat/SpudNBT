package net.techcable.spudcompat.spudnbt

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufOutputStream
import java.io.DataOutput

/**
 * A nbt tag, which is a binary format for representing data.
 *s
 * Used by minecraft [to store it's data](http://wiki.vg/NBT).
 *
 * This NBT may be immutable if it doesn't implement [MutableNBT]
 */
interface NBT : Cloneable {

    /**
     * The type of the NBT
     */
    val type: NBTType

    /**
     * Write this NBT to the specified [DataOutput]
     *
     * @param out the output to write to
     */
    fun writeValue(out: DataOutput)

    /**
     * Write this NBT to the specified [ByteBuf]
     *
     * @param buffer the buffer to write to
     */
    fun writeValue(buffer: ByteBuf) {
        this.writeValue(ByteBufOutputStream(buffer))
    }

    /**
     * Take an immutable snapshot of this NBT
     */
    fun snapshot(): NBT

    /**
     * Clone this object
     */
    override fun clone(): NBT;

    // Converters

    fun asString(): String {
        if (type == NBTType.STRING) {
            return (this as NBTPrimitive<*>).value as String
        } else {
            throw ClassCastException("NBT is a $type, not a String!")
        }
    }
}

fun ByteBuf.writeNBT(nbt: NBT) = nbt.writeValue(this)

fun DataOutput.writeNBT(nbt: NBT) = nbt.writeValue(this)
