package com.velocityscoreboardapi.impl;

import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.proxy.protocol.ProtocolUtils;
import com.velocitypowered.proxy.protocol.packet.chat.ComponentHolder;
import com.velocityscoreboardapi.api.NumberFormat;
import io.netty.buffer.ByteBuf;
import lombok.NonNull;
import net.kyori.adventure.text.Component;

public class FixedFormat implements NumberFormat {

    private ComponentHolder holder;
    private Component component;

    public FixedFormat(ComponentHolder holder) {
        this.holder = holder;
    }

    public FixedFormat(Component component) {
        this.component = component;
    }

    @Override
    public void write(@NonNull ByteBuf buf, @NonNull ProtocolVersion protocolVersion) {
        ProtocolUtils.writeVarInt(buf, 2);
        if (holder == null) holder = new ComponentHolder(protocolVersion, component);
        holder.write(buf);
    }
}
