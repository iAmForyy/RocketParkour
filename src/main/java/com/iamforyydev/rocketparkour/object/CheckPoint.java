package com.iamforyydev.rocketparkour.object;

import org.bukkit.Location;

public class CheckPoint {

    private final int id;
    private final Location spawnPoint;
    public CheckPoint(
            int id,
            Location spawnPoint
    ) {
        this.id = id;
        this.spawnPoint = spawnPoint;
    }

    public int getId() {
        return id;
    }

    public Location getSpawnPoint() {
        return spawnPoint;
    }
}
