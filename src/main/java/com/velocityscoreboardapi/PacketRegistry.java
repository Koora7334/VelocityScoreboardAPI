package com.velocityscoreboardapi;

import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.proxy.protocol.MinecraftPacket;
import com.velocitypowered.proxy.protocol.StateRegistry;
import com.velocitypowered.proxy.protocol.packet.scoreboard.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public class PacketRegistry {

    public static void registerPackets() throws Exception {
        Field f = StateRegistry.class.getDeclaredField("clientbound");
        f.setAccessible(true);
        StateRegistry.PacketRegistry clientbound = (StateRegistry.PacketRegistry) f.get(StateRegistry.PLAY);

        register(clientbound,
                DisplayObjectivePacket.class,
                DisplayObjectivePacket::new,
                map(0x3D, ProtocolVersion.MINECRAFT_1_7_2),
                map(0x38, ProtocolVersion.MINECRAFT_1_9),
                map(0x3A, ProtocolVersion.MINECRAFT_1_12),
                map(0x3B, ProtocolVersion.MINECRAFT_1_12_1),
                map(0x3E, ProtocolVersion.MINECRAFT_1_13),
                map(0x42, ProtocolVersion.MINECRAFT_1_14),
                map(0x43, ProtocolVersion.MINECRAFT_1_15),
                map(0x4C, ProtocolVersion.MINECRAFT_1_17),
                map(0x4F, ProtocolVersion.MINECRAFT_1_19_1),
                map(0x4D, ProtocolVersion.MINECRAFT_1_19_3),
                map(0x51, ProtocolVersion.MINECRAFT_1_19_4),
                map(0x53, ProtocolVersion.MINECRAFT_1_20_2),
                map(0x55, ProtocolVersion.MINECRAFT_1_20_3),
                map(0x57, ProtocolVersion.MINECRAFT_1_20_5)
        );
        register(clientbound,
                ObjectivePacket.class,
                ObjectivePacket::new,
                map(0x3B, ProtocolVersion.MINECRAFT_1_7_2),
                map(0x3F, ProtocolVersion.MINECRAFT_1_9),
                map(0x41, ProtocolVersion.MINECRAFT_1_12),
                map(0x42, ProtocolVersion.MINECRAFT_1_12_1),
                map(0x45, ProtocolVersion.MINECRAFT_1_13),
                map(0x49, ProtocolVersion.MINECRAFT_1_14),
                map(0x4A, ProtocolVersion.MINECRAFT_1_15),
                map(0x53, ProtocolVersion.MINECRAFT_1_17),
                map(0x56, ProtocolVersion.MINECRAFT_1_19_1),
                map(0x54, ProtocolVersion.MINECRAFT_1_19_3),
                map(0x58, ProtocolVersion.MINECRAFT_1_19_4),
                map(0x5A, ProtocolVersion.MINECRAFT_1_20_2),
                map(0x5C, ProtocolVersion.MINECRAFT_1_20_3),
                map(0x5E, ProtocolVersion.MINECRAFT_1_20_5)
        );
        register(clientbound,
                ScorePacket.class,
                ScorePacket::new,
                map(0x3C, ProtocolVersion.MINECRAFT_1_7_2),
                map(0x42, ProtocolVersion.MINECRAFT_1_9),
                map(0x44, ProtocolVersion.MINECRAFT_1_12),
                map(0x45, ProtocolVersion.MINECRAFT_1_12_1),
                map(0x48, ProtocolVersion.MINECRAFT_1_13),
                map(0x4C, ProtocolVersion.MINECRAFT_1_14),
                map(0x4D, ProtocolVersion.MINECRAFT_1_15),
                map(0x56, ProtocolVersion.MINECRAFT_1_17),
                map(0x59, ProtocolVersion.MINECRAFT_1_19_1),
                map(0x57, ProtocolVersion.MINECRAFT_1_19_3),
                map(0x5B, ProtocolVersion.MINECRAFT_1_19_4),
                map(0x5D, ProtocolVersion.MINECRAFT_1_20_2, ProtocolVersion.MINECRAFT_1_20_2)
        );
        register(clientbound,
                ScoreSetPacket.class,
                ScoreSetPacket::new,
                map(0x5F, ProtocolVersion.MINECRAFT_1_20_3),
                map(0x61, ProtocolVersion.MINECRAFT_1_20_5)
        );
        register(clientbound,
                ScoreResetPacket.class,
                ScoreResetPacket::new,
                map(0x42, ProtocolVersion.MINECRAFT_1_20_3),
                map(0x44, ProtocolVersion.MINECRAFT_1_20_5)
        );
        register(clientbound,
                TeamPacket.class,
                TeamPacket::new,
                map(0x3E, ProtocolVersion.MINECRAFT_1_7_2),
                map(0x41, ProtocolVersion.MINECRAFT_1_9),
                map(0x43, ProtocolVersion.MINECRAFT_1_12),
                map(0x44, ProtocolVersion.MINECRAFT_1_12_1),
                map(0x47, ProtocolVersion.MINECRAFT_1_13),
                map(0x4B, ProtocolVersion.MINECRAFT_1_14),
                map(0x4C, ProtocolVersion.MINECRAFT_1_15),
                map(0x55, ProtocolVersion.MINECRAFT_1_17),
                map(0x58, ProtocolVersion.MINECRAFT_1_19_1),
                map(0x56, ProtocolVersion.MINECRAFT_1_19_3),
                map(0x5A, ProtocolVersion.MINECRAFT_1_19_4),
                map(0x5C, ProtocolVersion.MINECRAFT_1_20_2),
                map(0x5E, ProtocolVersion.MINECRAFT_1_20_3),
                map(0x60, ProtocolVersion.MINECRAFT_1_20_5)
        );
    }

    private static <P extends MinecraftPacket> void register(
            StateRegistry.PacketRegistry registry, Class<P> clazz, Supplier<P> packetSupplier, StateRegistry.PacketMapping... mappings) throws Exception {
        Method m = StateRegistry.PacketRegistry.class.getDeclaredMethod("register", Class.class, Supplier.class, StateRegistry.PacketMapping[].class);
        m.setAccessible(true);
        m.invoke(registry, clazz, packetSupplier, mappings);
    }

    private static StateRegistry.PacketMapping map(int id, ProtocolVersion version) throws Exception {
        Method m = StateRegistry.class.getDeclaredMethod("map", int.class, ProtocolVersion.class, boolean.class);
        m.setAccessible(true);
        return (StateRegistry.PacketMapping) m.invoke(null, id, version, false); // encodeOnly false
    }

    private static StateRegistry.PacketMapping map(int id, ProtocolVersion version, ProtocolVersion lastValidProtocolVersion) throws Exception {
        Method m = StateRegistry.class.getDeclaredMethod("map", int.class, ProtocolVersion.class, ProtocolVersion.class, boolean.class);
        m.setAccessible(true);
        return (StateRegistry.PacketMapping) m.invoke(null, id, version, lastValidProtocolVersion, false); // encodeOnly false
    }
}
