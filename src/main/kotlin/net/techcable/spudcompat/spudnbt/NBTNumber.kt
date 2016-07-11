package net.techcable.spudcompat.spudnbt

interface NBTNumber<out T : Number> : NBTPrimitive<T> {
    fun toByte() = value.toByte()
    fun toShort() = value.toShort()
    fun toInt() = value.toInt()
    fun toLong() = value.toLong()
    fun toFloat() = value.toFloat()
    fun toDouble() = value.toDouble()

}