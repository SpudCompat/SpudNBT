package net.techcable.spudcompat.spudnbt.io

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufUtil
import java.io.DataOutput

internal fun DataOutput.writeString(str: String) {
    this.writeShort(str.length.and(0xFFFF))
    this.writeUTF(str)
}

internal fun ByteBuf.writeString(str: String) {
    this.writeShort(str.length.and(0xFFFF)) // write unsigned short
    assert('\u0000' !in str, { "String $str contains a null character!" })
    ByteBufUtil.writeUtf8(this, str)
}