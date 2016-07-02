package net.techcable.spudcompat.spudnbt

interface MutableNBTList<T: NBT>: NBTList<T>, MutableNBT, MutableList<T> {
    override fun clone(): MutableNBTList<T>

    override fun asMutable() = this
}