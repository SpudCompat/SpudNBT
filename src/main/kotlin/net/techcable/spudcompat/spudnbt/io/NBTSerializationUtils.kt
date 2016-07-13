@file:JvmName("NBTSerializationUtils")

package net.techcable.spudcompat.spudnbt.io

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufUtil
import java.io.DataInput
import java.io.DataOutput

fun DataOutput.writeString(str: String) {
    assert('\u0000' !in str, { "String $str contains a null character!" })
    this.writeUTF(str)
}

fun ByteBuf.writeString(str: String) {
    require (str.length <= 0xFFFF, { "String is too long! It's ${str.length} bytes but it can only be 65535 bytes at most!" })
    this.writeShort(str.length.and(0xFFFF)) // write unsigned short
    ByteBufUtil.writeUtf8(this, str)
}

fun DataInput.readString(): String {
    return this.readUTF()
}

fun ByteBuf.readString(): String {
    val size = this.readUnsignedShort()
    val index = this.readerIndex()
    val str = this.toString(index, size, Charsets.UTF_8)
    this.readerIndex(index + size)
    return str
}