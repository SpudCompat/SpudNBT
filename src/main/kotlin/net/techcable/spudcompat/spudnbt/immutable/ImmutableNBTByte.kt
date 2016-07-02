package net.techcable.spudcompat.spudnbt.immutable

import io.netty.buffer.ByteBuf
import net.techcable.spudcompat.spudnbt.NBTPrimitive
import net.techcable.spudcompat.spudnbt.NBTType
import java.io.DataOutput

internal final class ImmutableNBTByte(override val value: Byte) : NBTPrimitive<Byte> {
    override val type: NBTType
        get() = NBTType.BYTE


    override fun writeValue(buffer: ByteBuf) {
        buffer.writeByte(this.value.toInt())
    }

    override fun writeValue(out: DataOutput) {
        out.writeByte(this.value.toInt())
    }

    // Immutable
    override fun snapshot() = this
    override fun clone() = this
}