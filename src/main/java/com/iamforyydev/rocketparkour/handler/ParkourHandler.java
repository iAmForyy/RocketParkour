package com.iamforyydev.rocketparkour.handler;

import com.iamforyydev.rocketparkour.object.CheckPoint;
import com.iamforyydev.rocketparkour.object.Parkour;
import com.iamforyydev.rocketparkour.user.User;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class ParkourHandler {

    private final Map<String, Parkour> parkourMap = new HashMap<>();
    private final Map<User, Integer> userIntegerMap = new HashMap<>();

    public ParkourHandler(
    ) {
    }

    public Parkour getParkourFromStartLocation(
            Location location
    ) {
        for(Parkour parkour : getParkourMap().values()){
            if (parkour.getStartPoint().equals(location)) {
                return parkour;
            }
        }
        return null;
    }

    public Parkour getParkourFromEndLocation(
            Location location
    ) {
        for(Parkour parkour : getParkourMap().values()){
            if (parkour.getEndPoint().equals(location)) {
                return parkour;
            }
        }
        return null;
    }

    public boolean isCheckPoint(
            Location location
    ) {

        for(Parkour parkour : getParkourMap().values()){
            for(CheckPoint checkPoint : parkour.getCheckPointList()){
                return checkPoint.getSpawnPoint().equals(location);
            }
        }

        return false;
    }

    public int getCheckpointIdByLocation(
            Location location
    ) {
        for(Parkour parkour : getParkourMap().values()){
            for(CheckPoint checkPoint : parkour.getCheckPointList()){
                if (checkPoint.getSpawnPoint().equals(location)) {
                    return checkPoint.getId();
                }
            }
        }
        return -1;
    }

    public int getTaskIDFromUser(
            User user
    ) {
        return getUserIntegerMap().get(user);
    }

    public Parkour getParkourFromName(
            String parkourName
    ) {
        return getParkourMap().get(parkourName);
    }

    public Map<String, Parkour> getParkourMap() {
        return parkourMap;
    }

    public Map<User, Integer> getUserIntegerMap() {
        return userIntegerMap;
    }
}
