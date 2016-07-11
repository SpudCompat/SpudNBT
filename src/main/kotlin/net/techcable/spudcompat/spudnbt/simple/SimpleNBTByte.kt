package net.techcable.spudcompat.spudnbt.simple

import net.techcable.spudcompat.spudnbt.NBTNumber
import net.techcable.spudcompat.spudnbt.NBTType
import java.io.DataOutput

internal final class SimpleNBTByte(override val value: Byte) : NBTNumber<Byte> {
    override fun writeValue(out: DataOutput) {
        out.writeByte(value.toInt())
    }

    override val type: NBTType
        get() = NBTType.BYTE
}