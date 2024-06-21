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

import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.scoreboard.Objective;
import org.jetbrains.annotations.NotNull;

public class ObjectiveUnregisterEvent extends ObjectiveEvent implements ResultedEvent<ResultedEvent.GenericResult> {

    private GenericResult result = GenericResult.allowed();

    public ObjectiveUnregisterEvent(@NotNull Objective objective) {
        super(objective);
    }

    @Override
    public GenericResult getResult() {
        return result;
    }

    @Override
    public void setResult(@NotNull GenericResult genericResult) {
        this.result = genericResult;
    }

}