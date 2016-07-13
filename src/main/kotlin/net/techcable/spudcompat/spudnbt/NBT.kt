package net.techcable.spudcompat.spudnbt

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufOutputStream
import net.techcable.spudcompat.spudnbt.io.readString
import java.io.DataInput
import java.io.DataOutput
import java.io.IOException

/**
 * A nbt tag, which is a binary format for representing data.
 *
 * Used by minecraft [to store its data](http://wiki.vg/NBT).
 */
interface NBT : Cloneable {

    /**
     * The type of the NBT
     */
    val type: NBTType


    /**
     * Write this NBT to the specified [DataOutput]
     *
     * The value _does_ include type information, unlike [writeValue]
     *
     * @throws IOException if the underlying [DataOutput] has an error and throws an [IOException]
     * @param out the output to write to
     */
    @Throws(IOException::class)
    fun write(out: DataOutput) {
        out.writeByte(type.typeId)
        this.writeValue(out)
    }


    /**
     * Write this NBT to the specified [ByteBuf]
     *
     * The value _does_ include type information, unlike [writeValue]
     *
     * @param out the output to write to
     */
    fun write(buffer: ByteBuf) {
        buffer.writeByte(type.typeId)
        this.writeValue(buffer)
    }

    /**
     * Write the _value of_ NBT to the specified [DataOutput]
     *
     * **The value doesn't include type information**, and thus is incorrect for the majority of use-cases
     * Normally you should use [write], unless you actually don't want type information
     *
     * @throws IOException if the underlying [DataOutput] has an error and throws an [IOException]
     * @param out the output to write to
     */
    @Throws(IOException::class)
    fun writeValue(out: DataOutput)

    /**
     * Write the _value of_ this NBT to the specified [ByteBuf]
     *
     * **The value doesn't include type information**, and thus is incorrect for the magority of use-cases
     * Normally  you should use [write], unless you actually don't want type information
     *
     * @param buffer the buffer to write to
     */
    fun writeValue(buffer: ByteBuf) {
        this.writeValue(ByteBufOutputStream(buffer))
    }

    /**
     * Clone this nbt tag
     */
    override fun clone(): NBT;

    // Helpful Converters

    fun asString(): String {
        if (type == NBTType.STRING) {
            return (this as NBTPrimitive<*>).value as String
        } else {
            throw ClassCastException("NBT is a $type, not a String!")
        }
    }
}
