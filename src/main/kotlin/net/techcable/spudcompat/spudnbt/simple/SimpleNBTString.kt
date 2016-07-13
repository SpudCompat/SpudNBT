package net.techcable.spudcompat.spudnbt.simple

import io.netty.buffer.ByteBuf
import net.techcable.spudcompat.spudnbt.NBTPrimitive
import net.techcable.spudcompat.spudnbt.NBTType
import net.techcable.spudcompat.spudnbt.io.writeString
import java.io.DataOutput

internal final class SimpleNBTString(override val value: String) : NBTPrimitive<String> {
    override fun writeValue(out: DataOutput) {
        out.writeString(value)
    }

    override fun writeValue(buffer: ByteBuf) {
        buffer.writeString(value)
    }

    override val type: NBTType
        get() = NBTType.BYTE

    override fun toString() = "\"$value\"";

    override fun equals(other: Any?) = other is NBTPrimitive<*> && other.value is String && this.value.equals(other.value)

    override fun hashCode() = value.hashCode()
}
