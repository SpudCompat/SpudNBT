package net.techcable.spudcompat.spudnbt.simple

import net.techcable.spudcompat.spudnbt.NBTNumber
import net.techcable.spudcompat.spudnbt.NBTType
import java.io.DataOutput

internal final class SimpleNBTShort(override val value: Short) : NBTNumber<Short> {

    override fun writeValue(out: DataOutput) {
        out.writeShort(value.toInt())
    }

    override val type: NBTType
        get() = NBTType.SHORT

    override fun equals(other: Any?) = other is NBTNumber<*> && other.value is Short && other.value.equals(this.value)

    override fun hashCode() = value.hashCode()

    override fun toString() = "${value.toString()}s"
}
