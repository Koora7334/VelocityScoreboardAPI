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

package com.velocitypowered.api.event.scoreboard;

import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;

public abstract class TeamEntryEvent extends TeamEvent {

    @NotNull
    private final String entry;

    public TeamEntryEvent(@NotNull Player player, @NotNull String team, @NotNull String entry) {
        super(player, team);
        this.entry = entry;
    }

    @NotNull
    public String getEntry() {
        return entry;
    }

    public static class Add extends TeamEntryEvent {
        public Add(@NotNull Player player, @NotNull String team, @NotNull String entry) {
            super(player, team, entry);
        }
    }

    public static class Remove extends TeamEntryEvent {
        public Remove(@NotNull Player player, @NotNull String team, @NotNull String entry) {
            super(player, team, entry);
        }
    }

}