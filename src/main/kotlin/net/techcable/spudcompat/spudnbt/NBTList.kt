package net.techcable.spudcompat.spudnbt

import io.netty.buffer.ByteBuf
import java.io.DataOutput

interface NBTList<T : NBT> : NBT, List<T> {
    override fun snapshot(): NBTList<T>

    override fun clone(): NBTList<T>

    fun asMutable(): MutableNBTList<T>

    fun forEach(action: (element: T) -> Unit)

    override val type: NBTType
        get() = NBTType.LIST

    val elementType: NBTType

    override fun writeValue(out: DataOutput) {
        out.writeByte(NBTType.LIST.typeId)
        out.writeByte(elementType.typeId)
        out.writeInt(size)
        forEach { it.writeValue(out) }
    }

    override fun writeValue(buffer: ByteBuf) {
        buffer.writeByte(NBTType.LIST.typeId)
        buffer.writeByte(elementType.typeId)
        buffer.writeInt(size)
        forEach { it.writeValue(buffer) }
    }
}

fun verifyListType(list: List<NBT>, elementType: NBTType) {
    list.forEach {
        require(elementType.matches(it), { "The NBT is type ${it.type}, not the specified element type $elementType" })
    }
}