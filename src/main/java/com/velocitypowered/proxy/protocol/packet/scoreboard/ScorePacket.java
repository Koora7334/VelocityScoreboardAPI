package com.velocitypowered.proxy.protocol.packet.scoreboard;

import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.proxy.connection.MinecraftSessionHandler;
import com.velocitypowered.proxy.protocol.MinecraftPacket;
import com.velocitypowered.proxy.protocol.ProtocolUtils;
import com.velocityscoreboardapi.internal.PacketHandler;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Packet for setting scores in objectives for players 1.20.2 and below.
 */
public class ScorePacket implements MinecraftPacket {

    /** Packet priority (higher value = higher priority) */
    private final int packetPriority;

    /** Packet action (0 = set, 1 = remove) */
    private ScoreAction action;

    /** Score holder who the score belongs to */
    private String scoreHolder;

    /** Objective from which the holder should be removed (null for all objectives ?) */
    private String objectiveName;

    /** Score value */
    private int value;

    /**
     * Constructs new instance for packet decoding.
     */
    public ScorePacket() {
        this.packetPriority = 0;
    }

    /**
     * Constructs new instance for packet sending.
     *
     * @param   packetPriority
     *          Packet priority
     * @param   action
     *          Packet action
     * @param   scoreHolder
     *          Score holder
     * @param   objectiveName
     *          Objective name
     * @param   value
     *          Score value
     */
    public ScorePacket(int packetPriority, @NotNull ScoreAction action, @NotNull String scoreHolder,
                       @Nullable String objectiveName, int value) {
        this.packetPriority = packetPriority;
        this.action = action;
        this.scoreHolder = scoreHolder;
        this.objectiveName = objectiveName;
        this.value = value;
    }

    @Override
    public void decode(ByteBuf buf, ProtocolUtils.Direction direction, ProtocolVersion protocolVersion) {
        scoreHolder = ProtocolUtils.readString(buf);
        action = ScoreAction.byId(buf.readByte());
        if (protocolVersion.noGreaterThan(ProtocolVersion.MINECRAFT_1_7_6)) {
            if (action == ScoreAction.SET) {
                objectiveName = ProtocolUtils.readString(buf);
                value = buf.readInt();
            }
            return;
        }
        objectiveName = ProtocolUtils.readString(buf);
        if (action == ScoreAction.SET) {
            value = ProtocolUtils.readVarInt(buf);
        }
    }

    @Override
    public void encode(ByteBuf buf, ProtocolUtils.Direction direction, ProtocolVersion protocolVersion) {
        ProtocolUtils.writeString(buf, scoreHolder);
        buf.writeByte(action.ordinal());
        if (protocolVersion.noGreaterThan(ProtocolVersion.MINECRAFT_1_7_6)) {
            if (action == ScoreAction.SET) {
                ProtocolUtils.writeString(buf, objectiveName);
                buf.writeInt(value);
            }
            return;
        }
        ProtocolUtils.writeString(buf, objectiveName);
        if (action == ScoreAction.SET) {
            ProtocolUtils.writeVarInt(buf, value);
        }
    }

    @Override
    public boolean handle(MinecraftSessionHandler minecraftSessionHandler) {
        return PacketHandler.handle(minecraftSessionHandler, this);
    }

    public int getPacketPriority() {
        return packetPriority;
    }

    @NotNull
    public ScoreAction getAction() {
        return action;
    }

    @NotNull
    public String getScoreHolder() {
        return scoreHolder;
    }

    @Nullable
    public String getObjectiveName() {
        return objectiveName;
    }

    public int getValue() {
        return value;
    }

    /**
     * Enum for score packet action.
     */
    public enum ScoreAction {

        /** Sets score (creates if it does not exist) */
        SET,

        /** Resets score */
        RESET;

        private static final ScoreAction[] values = values();

        /**
         * Returns action by ID
         *
         * @param   id
         *          Action ID
         * @return  Action by ID
         */
        @NotNull
        public static ScoreAction byId(int id) {
            return values[id];
        }
    }
}
