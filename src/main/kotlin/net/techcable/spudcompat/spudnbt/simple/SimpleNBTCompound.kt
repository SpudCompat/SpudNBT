package net.techcable.spudcompat.spudnbt.simple

import com.google.common.collect.ForwardingMap
import com.google.common.collect.Maps
import net.techcable.spudcompat.spudnbt.NBT
import net.techcable.spudcompat.spudnbt.NBTCompound
import java.util.*

internal final class SimpleNBTCompound private constructor(val map: LinkedHashMap<String, NBT>): ForwardingMap<String, NBT>(), NBTCompound {
    override fun delegate() = map

    @Suppress("UNCHECKED_CAST") // Its safe
    override fun clone() = SimpleNBTCompound(LinkedHashMap(map))

    constructor(initialCapacity: Int): this(LinkedHashMap(initialCapacity))

    constructor(): this(LinkedHashMap())

    constructor(map: Map<String, NBT>): this(LinkedHashMap(map))

    override fun putIfAbsent(key: String, value: NBT): NBT? = map.putIfAbsent(key, value)

    override fun forEach(action: (Map.Entry<String, NBT>) -> Unit) {
        map.forEach({ name, value -> action(Maps.immutableEntry(name, value)) })
    }

    override fun hashCode() = super.standardHashCode()

    override fun equals(other: Any?) = other is NBTCompound && super.standardEquals(other)
}