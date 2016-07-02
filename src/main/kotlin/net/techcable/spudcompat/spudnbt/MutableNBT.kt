package net.techcable.spudcompat.spudnbt

/**
 * A mutable NBT tag
 */
interface MutableNBT : NBT {
    override fun clone(): MutableNBT
}