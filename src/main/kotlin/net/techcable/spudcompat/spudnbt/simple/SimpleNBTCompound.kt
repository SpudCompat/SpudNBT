package net.techcable.spudcompat.spudnbt.simple

import com.google.common.collect.ForwardingMap
import com.google.common.collect.Maps
import net.techcable.spudcompat.spudnbt.MutableNBTCompound
import net.techcable.spudcompat.spudnbt.NBT
import net.techcable.spudcompat.spudnbt.immutable.ImmutableNBTCompound
import java.util.*

internal class SimpleNBTCompound : ForwardingMap<String, NBT>, MutableNBTCompound {

    override fun delegate() = map
    private val map: HashMap<String, NBT>

    @Suppress("UNCHECKED_CAST") // Its safe
    override fun clone() = SimpleNBTCompound(map.clone() as HashMap<String, NBT>)

    private constructor(hashMap: HashMap<String, NBT>) {
        this.map = hashMap
    }

    constructor(map: Map<String, NBT>): this(HashMap(map))

    override fun snapshot() = ImmutableNBTCompound(map)

    override fun forEach(action: (Map.Entry<String, NBT>) -> Unit) {
        map.forEach({ name, value -> action(Maps.immutableEntry(name, value)) })
    }
}