package net.techcable.spudcompat.spudnbt

import java.util.Objects.requireNonNull

enum class NBTType {
    BYTE,
    SHORT,
    INT,
    LONG,
    FLOAT,
    DOUBLE,
    BYTE_ARRAY,
    STRING,
    LIST,
    COMPOUND,
    INT_ARRAY;

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
}
