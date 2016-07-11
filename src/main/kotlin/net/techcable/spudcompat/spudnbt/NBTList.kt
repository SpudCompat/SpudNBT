package net.techcable.spudcompat.spudnbt

import io.netty.buffer.ByteBuf
import net.techcable.spudcompat.spudnbt.simple.SimpleNBTByteList
import net.techcable.spudcompat.spudnbt.simple.SimpleNBTList
import java.io.DataOutput
import java.io.IOException
import java.util.function.Consumer

interface NBTList<T : NBT> : NBT, MutableList<T> {

    public override fun clone(): NBTList<T>

    fun forEach(action: (T) -> Unit)

    override val type: NBTType
        get() = NBTType.LIST

    val elementType: NBTType

    @Throws(IOException::class)
    override fun writeValue(out: DataOutput) {
        out.writeByte(elementType.typeId)
        out.writeInt(size)
        for (element in this) {
            element.writeValue(out)
        }
    }

    override fun writeValue(buffer: ByteBuf) {
        buffer.writeByte(elementType.typeId)
        buffer.writeInt(size)
        forEach { element -> element.write(buffer) }
    }
}
