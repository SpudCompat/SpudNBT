package net.techcable.spudcompat.spudnbt.simple

import net.techcable.spudcompat.spudnbt.NBTNumber
import net.techcable.spudcompat.spudnbt.NBTType
import java.io.DataOutput

internal final class SimpleNBTLong(override val value: Long) : NBTNumber<Long> {

    override fun writeValue(out: DataOutput) {
        out.writeLong(value)
    }

    override val type: NBTType
        get() = NBTType.LONG

    override fun equals(other: Any?) = other is NBTNumber<*> && other.value is Long && other.value.equals(this.value)

    override fun hashCode() = value.hashCode()

    override fun toString() = "${value.toString()}l"
}
