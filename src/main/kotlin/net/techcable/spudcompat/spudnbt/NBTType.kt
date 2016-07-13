package net.techcable.spudcompat.spudnbt

import io.netty.buffer.ByteBuf
import net.techcable.spudcompat.spudnbt.io.readString
import java.io.DataInput
import java.util.*
import java.util.Objects.requireNonNull

enum class NBTType {
    BYTE {
        override fun readValue(buffer: ByteBuf) = createByte(buffer.readByte())

        override fun readValue(input: DataInput) = createByte(input.readByte())
    },
    SHORT {
        override fun readValue(buffer: ByteBuf) = createShort(buffer.readShort())

        override fun readValue(input: DataInput) = createShort(input.readShort())
    },
    INT {
        override fun readValue(buffer: ByteBuf) = createInt(buffer.readInt())

        override fun readValue(input: DataInput) = createInt(input.readInt())
    },
    LONG {
        override fun readValue(buffer: ByteBuf) = createLong(buffer.readLong())

        override fun readValue(input: DataInput) = createLong(input.readLong())
    },
    FLOAT {
        override fun readValue(buffer: ByteBuf) = createFloat(buffer.readFloat())

        override fun readValue(input: DataInput) = createFloat(input.readFloat())
    },
    DOUBLE {
        override fun readValue(buffer: ByteBuf) = createDouble(buffer.readDouble())

        override fun readValue(input: DataInput) = createDouble(input.readDouble())
    },
    BYTE_ARRAY {
        override fun readValue(buffer: ByteBuf): NBTByteList {
            val size = buffer.readInt()
            val bytes = ByteArray(size)
            buffer.readBytes(bytes, 0, size)
            return createByteList(*bytes)
        }

        override fun readValue(input: DataInput): NBTByteList {
            val size = input.readInt()
            val bytes = ByteArray(size)
            input.readFully(bytes, 0, size)
            return createByteList(*bytes)
        }
    },
    STRING {
        override fun readValue(buffer: ByteBuf) = createString(buffer.readString())

        override fun readValue(input: DataInput) = createString(input.readString())
    },
    LIST {
        override fun readValue(buffer: ByteBuf): NBTList<*> {
            val elementType = fromId(buffer.readByte().toInt())
            val size = buffer.readInt()
            val list = createList<NBT>(elementType, size)
            for (i in 0..size) {
                list.add(elementType.readValue(buffer))
            }
            return list
        }

        override fun readValue(input: DataInput): NBTList<*> {
            val elementType = fromId(input.readByte().toInt())
            val size = input.readInt()
            val list = createList<NBT>(elementType, size)
            for (i in 0..(size - 1)) {
                list.add(elementType.readValue(input))
            }
            return list
        }
    },
    COMPOUND {
        override fun readValue(buffer: ByteBuf): NBTCompound {
            val compound = createCompound()
            while (true) {
                val elementTypeId = buffer.readByte().toInt().and(0xFF)
                val elementType: NBTType
                if (elementTypeId == 0) {
                    // a 0 tag indicates of the compound
                    return compound
                } else {
                    elementType = fromId(elementTypeId)
                }
                val name = buffer.readString()
                val element = elementType.readValue(buffer)
                compound.put(name, element)
            }
        }

        override fun readValue(input: DataInput): NBTCompound {
            val compound = createCompound()
            while (true) {
                val elementTypeId = input.readByte().toInt().and(0xFF)
                val elementType: NBTType
                if (elementTypeId == 0) {
                    // a 0 tag indicates of the compound
                    return compound
                } else {
                    elementType = fromId(elementTypeId)
                }
                val name = input.readString()
                val element = elementType.readValue(input)
                compound.put(name, element)
            }
        }
    },
    INT_ARRAY {
        override fun readValue(buffer: ByteBuf): NBTIntList {
            val size = buffer.readInt()
            val ints = IntArray(size)
            for (i in 0..ints.size) {
                ints[i] = buffer.readInt()
            }
            return createIntList(*ints)
        }

        override fun readValue(input: DataInput): NBTIntList {
            val size = input.readInt()
            val ints = IntArray(size)
            for (i in 0..ints.size) {
                ints[i] = input.readInt()
            }
            return createIntList(*ints)
        }
    };

    val typeId: Int
        get() = ordinal + 1

    fun <T : NBT> verifyMatches(element: T): T {
        require(typeId == requireNonNull(element, "Null element").type.typeId, { "Element's type ${element.type} doesn't match the specified element type $this" })
        return element
    }

    fun <T : Collection<NBT>> verifyMatchesAll(collection: T): T {
        requireNonNull(collection, "Null collection").forEach({ verifyMatches(it) })
        return collection
    }

    abstract fun readValue(buffer: ByteBuf): NBT

    abstract fun readValue(input: DataInput): NBT

    companion object {
        @JvmStatic
        fun fromId(id: Int): NBTType {
            val index = id - 1
            val values = values()
            if (index >= 0 && index < values.size) {
                return values[index]
            } else {
                throw IllegalArgumentException(invalidId(id))
            }
        }

        @JvmStatic
        private fun invalidId(id: Int) = if (id < 0) "Negative id: $id" else "Unknown type with id: $id"
    }

}
