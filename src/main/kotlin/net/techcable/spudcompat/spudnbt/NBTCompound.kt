package net.techcable.spudcompat.spudnbt

import com.google.common.collect.Maps
import io.netty.buffer.ByteBuf
import net.techcable.spudcompat.spudnbt.io.writeString
import java.io.DataOutput
import java.util.*
import java.util.function.BiConsumer

interface NBTCompound : NBT, MutableMap<String, NBT> {

    public override fun clone(): NBTCompound

    // Overrides standard kotlin Map.forEach
    fun forEach(action: (Map.Entry<String, NBT>) -> Unit)

    // Overrides java 8 Map.forEach (but kotlin doesn't know that)
    fun forEach(action: BiConsumer<in String, in NBT>) = forEach { action.accept(it.key, it.value) }

    override val type: NBTType
        get() = NBTType.COMPOUND

    override fun writeValue(out: DataOutput) {
        forEach {
            out.writeString(it.key)
            it.value.writeValue(out)
        }
        out.writeByte(0)
    }

    override fun writeValue(buffer: ByteBuf) {
        forEach {
            buffer.writeString(it.key)
            it.value.writeValue(buffer)
        }
        buffer.writeByte(0)
    }

    fun putIfAbsent(key: String, value: NBT): NBT? {
        val oldValue = get(key)
        if (oldValue == null) put(key, value)
        return oldValue
    }

    class Builder constructor(expectedSize: Int) {
        val entries: MutableList<Map.Entry<String, NBT>> = ArrayList(expectedSize)

        fun put(key: String, value: Byte) = put(key, createByte(value))

        fun put(key: String, value: Short) = put(key, createShort(value))

        fun put(key: String, value: Int) = put(key, createInt(value))

        fun put(key: String, value: Long) = put(key, createLong(value))

        fun put(key: String, value: Float) = put(key, createFloat(value))

        fun put(key: String, value: Double) = put(key, createDouble(value))

        fun put(key: String, value: String) = put(key, createString(value))

        fun put(key: String, value: NBT) = put(Maps.immutableEntry(key, value))

        fun put(entry: Map.Entry<String, NBT>): Builder {
            assert(!entries.any({ it.key == entry.key }), { "${entry.key} already exists" })
            entries.add(entry)
            return this
        }

        fun build(): NBTCompound {
            val result = createCompound(entries.size)
            entries.forEach {
                val oldValue = result.putIfAbsent(it.key, it.value)
                check(oldValue == null, { "${it.key} was inserted twice with both ${it.value} and $oldValue" })
            }
            return result
        }

        constructor(): this(8)
    }

}