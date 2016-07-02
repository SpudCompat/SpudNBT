package net.techcable.spudcompat.spudnbt

interface NBTIntList : NBTList<NBTPrimitive<Int>> {
    override fun forEach(action: (NBTPrimitive<Int>) -> Unit) {
        forEachInt { action(createNbtInt(it)) }
    }

    fun forEachInt(action: (Int) -> Unit)

    override val type: NBTType
        get() = NBTType.INT_ARRAY
}