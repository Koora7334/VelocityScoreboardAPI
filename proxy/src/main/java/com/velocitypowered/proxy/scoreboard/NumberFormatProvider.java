/*
 * This file is part of VelocityScoreboardAPI, licensed under the Apache License 2.0.
 *
 *  Copyright (c) William278 <will27528@gmail.com>
 *  Copyright (c) NEZNAMY <n.e.z.n.a.m.y@azet.sk>
 *  Copyright (c) contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.velocitypowered.proxy.scoreboard;

import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.scoreboard.NumberFormat;
import com.velocitypowered.proxy.protocol.ProtocolUtils;
import com.velocitypowered.proxy.protocol.packet.chat.ComponentHolder;
import com.velocitypowered.proxy.scoreboard.numbers.BlankFormat;
import com.velocitypowered.proxy.scoreboard.numbers.FixedFormat;
import com.velocitypowered.proxy.scoreboard.numbers.StyledFormat;
import io.netty.buffer.ByteBuf;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class NumberFormatProvider {

    @NotNull
    public static NumberFormat read(@NotNull ByteBuf buf, @NotNull ProtocolVersion ver) {
        int format = ProtocolUtils.readVarInt(buf);
        return switch (format) {
            case 0 -> BlankFormat.INSTANCE;
            case 1 -> BlankFormat.INSTANCE;
            //return new NumberFormat(Type.STYLED, readComponentStyle(buf, protocolVersion)); //TODO
            case 2 -> new FixedFormat(ComponentHolder.read(buf, ver));
            default -> throw new IllegalArgumentException("Unknown number format " + format);
        };
    }

    public abstract static class Builder implements NumberFormat.Builder {

        @Nullable protected NumberFormat numberFormat;

        @Override
        @NotNull
        public NumberFormat.Builder fixedNumberFormat(@NotNull Component component) {
            this.numberFormat = new FixedFormat(component);
            return this;
        }

        @Override
        public NumberFormat.@NotNull Builder styledNumberFormat(@NotNull Style style) {
            this.numberFormat = new StyledFormat(style);
            return this;
        }

        @Override
        public NumberFormat.@NotNull Builder blankNumberFormat() {
            this.numberFormat = BlankFormat.INSTANCE;
            return this;
        }
    }

}