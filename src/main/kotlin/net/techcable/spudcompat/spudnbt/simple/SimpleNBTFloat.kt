package net.techcable.spudcompat.spudnbt.simple

import net.techcable.spudcompat.spudnbt.NBTNumber
import net.techcable.spudcompat.spudnbt.NBTType
import java.io.DataOutput

internal final class SimpleNBTFloat(override val value: Float): NBTNumber<Float> {

    override fun writeValue(out: DataOutput) {
        out.writeFloat(value)
    }

    override val type: NBTType
        get() = NBTType.FLOAT
}