package com.iamforyydev.rocketparkour.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtility {

    public static String write(
            Location location
    ) {
        return location.getWorld().getName()+";"+
                location.getX()+";"+
                location.getY()+";"+
                location.getZ()+";"+
                location.getYaw()+";"+
                location.getPitch();
    }

    public static Location read(
            String location
    ) {
        return new Location(
                Bukkit.getWorld(location.split(";")[0]),
                Double.parseDouble(location.split(";")[1]),
                Double.parseDouble(location.split(";")[2]),
                Double.parseDouble(location.split(";")[3]),
                Float.parseFloat(location.split(";")[4]),
                Float.parseFloat(location.split(";")[5]));
    }

}
