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

package com.velocitypowered.api;

import net.kyori.adventure.text.Component;

public abstract class TextHolderProvider {

    private static TextHolderProvider provider;

    public static TextHolderProvider getProvider() {
        if (provider == null) {
            throw new IllegalStateException("TextHolderProvider is not set");
        }
        return provider;
    }

    protected TextHolderProvider() {
        if (provider != null) {
            throw new IllegalStateException("TextHolderProvider is already set");
        }
        provider = this;
    }

    /**
     * Returns an empty TextHolder object.
     *
     * @return An empty TextHolder object
     */
    public abstract TextHolder empty();

    /**
     * Constructs a TextHolder object with the given legacy text
     * or returns a cached instance if previously constructed with the same text.
     *
     * @param legacyText The legacy text to be used in the TextHolder
     * @return The constructed TextHolder object
     */
    public abstract TextHolder of(String legacyText);

    /**
     * Constructs a TextHolder object with the given Component modern text
     * or returns a cached instance if previously constructed with the same text.
     *
     * @param modernText The Component modern text to be used in the TextHolder
     * @return The constructed TextHolder object
     */
    public abstract TextHolder of(Component modernText);
}
