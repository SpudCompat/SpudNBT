package net.techcable.spudcompat.spudnbt.immutable

import io.netty.buffer.ByteBuf
import net.techcable.spudcompat.spudnbt.NBTList
import net.techcable.spudcompat.spudnbt.NBTPrimitive
import net.techcable.spudcompat.spudnbt.NBTType
import java.io.DataOutput

internal final class ImmutableNBTInt(override val value: Int) : NBTPrimitive<Int> {
    override val type: NBTType
        get() = NBTType.INT

    override fun writeValue(buffer: ByteBuf) {
        buffer.writeInt(this.value)
    }

    override fun writeValue(out: DataOutput) {
        out.writeInt(this.value)
    }

    // Immutable
    override fun snapshot() = this
    override fun clone() = this
}