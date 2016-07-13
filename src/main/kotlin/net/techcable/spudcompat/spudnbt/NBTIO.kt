@file:JvmName("NBTIO")
package net.techcable.spudcompat.spudnbt

import io.netty.buffer.ByteBuf
import net.techcable.spudcompat.spudnbt.io.readString
import java.io.DataInput
import java.io.DataOutput

/**
 * Read the nbt from the buffer, returning null if nbt is not present
 *
 * @throws IllegalArgumentException if the nbt is not in a valid format
 * @return the nbt, or null if none
 */
fun ByteBuf.readNBT(): NBT? {
    val typeId = this.readByte().toInt()
    when (typeId) {
        0 -> return null
        NBTType.COMPOUND.typeId -> {}
        else -> throw IllegalArgumentException("Expected a root compound tag but got type id $typeId")
    }
    this.readString() // Read (but ignore) the name of the root tag
    return NBTType.COMPOUND.readValue(this)
}

/**
 * Read the nbt from the buffer, or throw an exception if the nbt is not present
 *
 * @throws IllegalArgumentException if the nbt isn't present
 * @throws IllegalArgumentException if the nbt is not in a valid format
 * @return the nbt
 */
fun ByteBuf.readNBTOrThrow(): NBT = readNBT() ?: throw IllegalArgumentException("NBT isn't present!")


/**
 * Read the nbt from the input, returning null if nbt is not present
 *
 * @throws IllegalArgumentException if the nbt is not in a valid format
 * @return the nbt, or null if not present
 */
fun DataInput.readNBTIfPresent(): NBTCompound? {
    val typeId = this.readByte().toInt()
    when (typeId) {
        0 -> return null
        NBTType.COMPOUND.typeId -> {} // Hooray its a compound like we expected!
        else -> throw IllegalArgumentException("Expected a root compound tag but got type id $typeId")
    }
    this.readString() // Read (but ignore) the name of the root tag
    return NBTType.COMPOUND.readValue(this) as NBTCompound
}

/**
 * Read the nbt from the input, or throw an exception if the nbt is not present
 *
 * @throws IllegalArgumentException if the nbt isn't present
 * @throws IllegalArgumentException if the nbt is not in a valid format
 * @return the nbt
 */
fun DataInput.readNBT(): NBTCompound = readNBTIfPresent() ?: throw IllegalArgumentException("NBT isn't present!")