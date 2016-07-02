package net.techcable.spudcompat.spudnbt

enum class NBTType(val nbtType: Class<out NBT>, val primitiveType: Class<*>?) {
    BYTE(NBTNumber::class.java, Byte::class.java) {
        override fun matches(nbt: NBT) = nbt is NBTNumber<*> && nbt.value is Byte
    },
    SHORT(NBTNumber::class.java, Short::class.java) {
        override fun matches(nbt: NBT) = nbt is NBTNumber<*> && nbt.value is Short
    },
    INT(NBTNumber::class.java, Int::class.java) {
        override fun matches(nbt: NBT) = nbt is NBTNumber<*> && nbt.value is Int
    },
    LONG(NBTNumber::class.java, Long::class.java) {
        override fun matches(nbt: NBT) = nbt is NBTNumber<*> && nbt.value is Long
    },
    FLOAT(NBTNumber::class.java, Float::class.java) {
        override fun matches(nbt: NBT) = nbt is NBTNumber<*> && nbt.value is Float
    },
    DOUBLE(NBTNumber::class.java, Double::class.java) {
        override fun matches(nbt: NBT) = nbt is NBTNumber<*> && nbt.value is Double
    },
    BYTE_ARRAY(NBTByteList::class.java),
    STRING(NBTPrimitive::class.java, String::class.java) {
        override fun matches(nbt: NBT) = nbt is NBTPrimitive<*> && nbt.value is String
    },
    LIST(NBTList::class.java) {
        override fun matches(nbt: NBT): Boolean {
            return nbt is NBTList<*> && nbt !is NBTByteList && nbt !is NBTIntList
        }
    },
    COMPOUND(NBTCompound::class.java),
    INT_ARRAY(NBTIntList::class.java);

    val primitive: Boolean
        get() = primitiveType != null
    val typeId: Int
        get() = ordinal + 1

    open fun matches(nbt: NBT) = nbtType.isInstance(nbt)

    constructor(nbtType: Class<out NBT>): this(nbtType, null)

}
