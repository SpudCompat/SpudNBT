package net.techcable.spudcompat.spudnbt.simple

import net.techcable.spudcompat.spudnbt.NBTNumber
import net.techcable.spudcompat.spudnbt.NBTType
import java.io.DataOutput

internal final class SimpleNBTDouble(override val value: Double): NBTNumber<Double> {

    override fun writeValue(out: DataOutput) {
        out.writeDouble(value)
    }

    override val type: NBTType
        get() = NBTType.DOUBLE

    override fun equals(other: Any?) = other is NBTNumber<*> && other.value is Double && other.value.equals(this.value)

    override fun hashCode() = value.hashCode()

    override fun toString() = value.toString()
}
