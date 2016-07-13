package net.techcable.spudcompat;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.GZIPInputStream;

import com.google.common.primitives.Bytes;

import net.techcable.spudcompat.spudnbt.NBTCompound;
import net.techcable.spudcompat.spudnbt.NBTFactory;
import net.techcable.spudcompat.spudnbt.NBTIO;
import net.techcable.spudcompat.spudnbt.NBTType;

import org.junit.Assert;
import org.junit.Test;

public class ParsingTest {
    public static final NBTCompound HELLO_WORLD_NBT = new NBTCompound.Builder()
            .put("name", "Bananrama")
            .build();
    public static final NBTCompound COMPLEX_NBT = new NBTCompound.Builder()
            .put(
                    "nested compound test",
                    new NBTCompound.Builder()
                            .put(
                                    "egg",
                                    new NBTCompound.Builder()
                                            .put("name", "Eggbert")
                                            .put("value", 0.5f)
                                            .build()
                            )
                            .put(
                                    "ham",
                                    new NBTCompound.Builder()
                                            .put("name", "Hampus")
                                            .put("value", 0.75f)
                                            .build()
                            )
                            .build()
            )
            .put("intTest", 2147483647)
            .put("byteTest", (byte) 127)
            .put("stringTest", "HELLO WORLD THIS IS A TEST STRING \u00c5\u00c4\u00d6!")
            .put("listTest (long)", NBTFactory.createLongList(11, 12, 13, 14, 15))
            .put("doubleTest", 0.49312871321823148)
            .put("floatTest", 0.49823147058486938f)
            .put("longTest", 9223372036854775807L)
            .put(
                    "listTest (compound)",
                    NBTFactory.createList(
                            NBTType.COMPOUND,
                            new NBTCompound.Builder()
                                    .put("created-on", 1264099775885L)
                                    .put("name", "Compound tag #0")
                                    .build(),
                            new NBTCompound.Builder()
                                    .put("created-on", 1264099775885L)
                                    .put("name", "Compound tag #1")
                                    .build()
                    )
            )
            .put(
                    "byteArrayTest (the first 1000 values of (n*n*255+n*7)%100, starting with n=0 (0, 62, 34, 16, 8, ...))",
                    NBTFactory.createByteList(Bytes.toArray(IntStream.iterate(0, (b) -> (b + 1))
                            .mapToObj((i) -> (byte) ((i * i * 255 + i * 7) % 100))
                            .limit(1000)
                            .collect(Collectors.toList())))
            )
            .put("shortTest", (short) 32767)
            .build();

    @Test
    public void testHelloWorld() throws IOException  {
        NBTCompound helloWorld = NBTIO.readNBT(new DataInputStream(getClass().getResourceAsStream("/hello_world.nbt")));
        Assert.assertEquals(HELLO_WORLD_NBT, helloWorld);
    }

    @Test
    public void testComplex() throws IOException {
        NBTCompound complex = NBTIO.readNBT(new DataInputStream(new GZIPInputStream(getClass().getResourceAsStream("/complex.nbt"))));
        Assert.assertEquals(COMPLEX_NBT, complex);
    }
}
