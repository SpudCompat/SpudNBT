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
}