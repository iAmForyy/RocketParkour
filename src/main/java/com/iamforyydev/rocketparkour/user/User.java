package com.iamforyydev.rocketparkour.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    private final UUID uuid;
    private boolean inParkour;

    private int checkpoint;
    private int time;

    public User(
            UUID uuid
    ){
        this.uuid = uuid;
        this.checkpoint = 0;
        this.time = 0;
    }

    public Player getPlayer(
    ) {
        return Bukkit.getPlayer(getUuid());
    }

    public boolean isInParkour() {
        return inParkour;
    }

    public void setInParkour(boolean inParkour) {
        this.inParkour = inParkour;
    }

    public int getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(int checkpoint) {
        this.checkpoint = checkpoint;
    }

    public void increaseTime(
    ) {
        this.time++;
    }

    public int getTime(
    ) {
        return this.time;
    }

    public UUID getUuid(
    ) {
        return this.uuid;
    }




}
