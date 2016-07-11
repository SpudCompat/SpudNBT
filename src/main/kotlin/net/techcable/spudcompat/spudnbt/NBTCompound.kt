package net.techcable.spudcompat.spudnbt

import io.netty.buffer.ByteBuf
import net.techcable.spudcompat.spudnbt.io.writeString
import net.techcable.spudcompat.spudnbt.simple.SimpleNBTCompound
import java.io.DataOutput
import java.util.function.BiConsumer

interface NBTCompound : NBT, MutableMap<String, NBT> {

    public override fun clone(): NBTCompound

    // Overrides standard kotlin Map.forEach
    fun forEach(action: (Map.Entry<String, NBT>) -> Unit)

    // Overrides java 8 Map.forEach (but kotlin doesn't know that)
    fun forEach(action: BiConsumer<in String, in NBT>) = forEach { action.accept(it.key, it.value) }

    override val type: NBTType
        get() = NBTType.COMPOUND

    override fun writeValue(out: DataOutput) {
        forEach {
            out.writeString(it.key)
            it.value.writeValue(out)
        }
        out.writeByte(0)
    }

    override fun writeValue(buffer: ByteBuf) {
        forEach {
            buffer.writeString(it.key)
            it.value.writeValue(buffer)
        }
        buffer.writeByte(0)
    }
}