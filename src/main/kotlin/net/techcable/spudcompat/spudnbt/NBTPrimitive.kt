package net.techcable.spudcompat.spudnbt

interface NBTPrimitive<out T>: NBT {

    val value: T

    override fun snapshot(): NBTPrimitive<T>

    override fun clone(): NBTPrimitive<T>

    override fun asMutable() = throw UnsupportedOperationException("Primitives can't be mutable!")
}