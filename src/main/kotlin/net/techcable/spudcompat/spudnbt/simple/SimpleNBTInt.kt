package net.techcable.spudcompat.spudnbt.simple

import net.techcable.spudcompat.spudnbt.NBTNumber
import net.techcable.spudcompat.spudnbt.NBTType
import java.io.DataOutput

internal final class SimpleNBTInt(override val value: Int) : NBTNumber<Int> {

    override fun writeValue(out: DataOutput) {
        out.writeInt(value)
    }

    override val type: NBTType
        get() = NBTType.INT

    override fun equals(other: Any?) = other is NBTNumber<*> && other.value is Int && other.value.equals(this.value)

    override fun hashCode() = value.hashCode()

    override fun toString() = value.toString()
}
