package net.techcable.spudcompat.spudnbt

interface MutableNBTCompound : MutableNBT, NBTCompound, MutableMap<String, NBT> {

    override fun clone(): MutableNBTCompound

    override fun asMutable() = this
}