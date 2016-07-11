package net.techcable.spudcompat.spudnbt

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufUtil
import net.techcable.spudcompat.spudnbt.simple.SimpleNBTString
import java.io.DataOutput

interface NBTPrimitive<out T>: NBT {

    val value: T

    override fun clone(): NBTPrimitive<T> = this // Primitives are immutable :p

}