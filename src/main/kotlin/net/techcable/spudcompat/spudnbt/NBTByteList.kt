package net.techcable.spudcompat.spudnbt

interface NBTByteList : NBTList<NBTPrimitive<Byte>> {
    override fun forEach(action: (NBTPrimitive<Byte>) -> Unit) {
        forEachByte { action(createNbtByte(it)) }
    }

    fun forEachByte(action: (Byte) -> Unit)

    override val type: NBTType
        get() = NBTType.BYTE_ARRAY
}