package net.techcable.spudcompat.spudnbt.immutable

import com.google.common.collect.ForwardingMap
import com.google.common.collect.ImmutableMap
import net.techcable.spudcompat.spudnbt.NBTCompound
import net.techcable.spudcompat.spudnbt.NBT
import net.techcable.spudcompat.spudnbt.simple.SimpleNBTCompound

internal final class ImmutableNBTCompound(val map: ImmutableMap<String, NBT>): ForwardingMap<String, NBT>(), NBTCompound {
    override fun delegate() = map

    override fun snapshot() = this

    override fun clone() = this

    override fun asMutable() = SimpleNBTCompound(map)

    override fun forEach(action: (Map.Entry<String, NBT>) -> Unit) {
        map.entries.forEach { entry -> action(entry) }
    }

    constructor(map: Map<String, NBT>): this(ImmutableMap.copyOf(map))
}