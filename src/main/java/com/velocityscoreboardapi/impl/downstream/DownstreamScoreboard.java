package com.velocityscoreboardapi.impl.downstream;

import com.velocitypowered.proxy.protocol.packet.chat.ComponentHolder;
import com.velocitypowered.proxy.protocol.packet.scoreboard.*;
import com.velocitypowered.proxy.protocol.packet.scoreboard.ScorePacket.ScoreAction;
import com.velocityscoreboardapi.api.NumberFormat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DownstreamScoreboard {

    private final Map<String, DownstreamObjective> objectives = new HashMap<>();
    private final Map<String, DownstreamTeam> teams = new HashMap<>();

    public void handle(@NotNull ObjectivePacket packet) {
        switch (packet.getAction()) {
            case REGISTER:
                if (objectives.containsKey(packet.getObjectiveName())) {
                    System.out.println("This scoreboard already contains objective called " + packet.getObjectiveName());
                } else {
                    objectives.put(packet.getObjectiveName(), new DownstreamObjective(
                            packet.getObjectiveName(),
                            packet.getTitleLegacy(),
                            packet.getTitleModern(),
                            packet.getHealthDisplay(),
                            packet.getNumberFormat(),
                            null
                    ));
                }
                return;
            case UNREGISTER:
                if (objectives.remove(packet.getObjectiveName()) == null) {
                    System.out.println("This scoreboard does not contain objective called " + packet.getObjectiveName() + ", cannot unregister");
                }
                return;
            case UPDATE:
                DownstreamObjective objective = objectives.get(packet.getObjectiveName());
                if (objective == null) {
                    System.out.println("This scoreboard does not contain objective called " + packet.getObjectiveName() + ", cannot update");
                } else {
                    objective.update(packet);
                }
        }
    }

    public void handle(@NotNull DisplayObjectivePacket packet) {
        DownstreamObjective objective = objectives.get(packet.getObjectiveName());
        if (objective == null) {
            System.out.println("Cannot set display slot of unknown objective " + packet.getObjectiveName());
        } else {
            objective.setDisplaySlot(packet.getPosition());
        }
    }

    public void handle(@NotNull ScorePacket packet) {
        if (packet.getAction() == ScoreAction.SET) {
            if (packet.getObjectiveName() == null) return; // Invalid packet
            handleSet(packet.getObjectiveName(), packet.getScoreHolder(), packet.getValue(), null, null);
        } else {
            handleReset(packet.getObjectiveName(), packet.getScoreHolder());
        }
    }

    public void handle(@NotNull ScoreSetPacket packet) {
        handleSet(packet.getObjectiveName(), packet.getScoreHolder(), packet.getValue(), packet.getDisplayName(), packet.getNumberFormat());
    }

    public void handle(@NotNull ScoreResetPacket packet) {
        handleReset(packet.getObjectiveName(), packet.getScoreHolder());
    }

    private void handleSet(@NotNull String objectiveName, @NotNull String holder, int value, @Nullable ComponentHolder displayName, @Nullable NumberFormat numberFormat) {
        DownstreamObjective objective = objectives.get(objectiveName);
        if (objective == null) {
            System.out.println("Cannot set score for unknown objective " + objectiveName);
        } else {
            objective.setScore(holder, value, displayName, numberFormat);
        }
    }

    private void handleReset(@Nullable String objectiveName, @NotNull String holder) {
        if (objectiveName == null) {
            for (DownstreamObjective objective : objectives.values()) {
                objective.removeScore(holder);
            }
        } else {
            DownstreamObjective objective = objectives.get(objectiveName);
            if (objective == null) {
                System.out.println("Cannot reset score for unknown objective " + objectiveName);
            } else {
                objective.removeScore(holder);
            }
        }
    }

    public void handle(@NotNull TeamPacket packet) {
        switch (packet.getAction()) {
            case REGISTER:
                if (teams.containsKey(packet.getName())) {
                    System.out.println("This scoreboard already contains team called " + packet.getName());
                } else {
                    teams.put(packet.getName(), DownstreamTeam.create(packet));
                }
                return;
            case UNREGISTER:
                if (teams.remove(packet.getName()) == null) {
                    System.out.println("This scoreboard does not contain team called " + packet.getName() + ", cannot unregister");
                }
                return;
            case UPDATE:
                DownstreamTeam team = teams.get(packet.getName());
                if (team == null) {
                    System.out.println("This scoreboard does not contain team called " + packet.getName() + ", cannot update");
                } else {
                    team.update(packet);
                }
                return;
            case ADD_PLAYER:
                DownstreamTeam team2 = teams.get(packet.getName());
                if (team2 == null) {
                    System.out.println("This scoreboard does not contain team called " + packet.getName() + ", cannot add entries");
                } else {
                    team2.addEntries(packet.getEntries());
                }
                return;
            case REMOVE_PLAYER:
                DownstreamTeam team3 = teams.get(packet.getName());
                if (team3 == null) {
                    System.out.println("This scoreboard does not contain team called " + packet.getName() + ", cannot remove entries");
                } else {
                    team3.removeEntries(packet.getEntries());
                }
        }
    }
}
