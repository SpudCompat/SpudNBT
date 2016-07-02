package net.techcable.spudcompat.spudnbt

import io.netty.buffer.ByteBuf
import net.techcable.spudcompat.spudnbt.io.writeString
import java.io.DataOutput

interface NBTCompound : NBT, Map<String, NBT> {
    override fun snapshot(): NBTCompound

    override fun clone(): NBTCompound

    fun asMutable(): MutableNBTCompound

    fun forEach(action: (entry: Map.Entry<String, NBT>) -> Unit)

    override val type: NBTType
        get() = NBTType.COMPOUND

    override fun writeValue(out: DataOutput) {
        out.writeByte(NBTType.COMPOUND.typeId)
        this.forEach {
            out.writeString(it.key)
            it.value.writeValue(out)
        }
        out.writeByte(0)
    }

    override fun writeValue(buffer: ByteBuf) {
        buffer.writeByte(NBTType.COMPOUND.typeId)
        this.forEach {
            buffer.writeString(it.key)
            it.value.writeValue(buffer)
        }
        buffer.writeByte(0)
    }
}