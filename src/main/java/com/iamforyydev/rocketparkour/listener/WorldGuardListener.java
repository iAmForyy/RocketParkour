package com.iamforyydev.rocketparkour.listener;

import com.iamforyydev.rocketparkour.RocketParkour;
import com.iamforyydev.rocketparkour.user.User;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashSet;
import java.util.Set;

public class WorldGuardListener
        implements Listener {

    private final RocketParkour plugin = RocketParkour.getInstance();

    @EventHandler
    public void onPlayerMove(
            PlayerMoveEvent moveEvent
    ) {

        Location
                moveTo = moveEvent.getTo(),
                moveFrom = moveEvent.getFrom();

        if (moveTo.getX() != moveFrom.getX() ||
                moveTo.getY() != moveFrom.getY() ||
                moveTo.getZ() != moveFrom.getZ()) {

            Player player = moveEvent.getPlayer();
            User user = plugin.getUserHandler().getUserByUuid(player.getUniqueId());
            if (isRegion(player, "parkour")) {
                plugin.getScoreboardHandler().loadScoreboard(user);
            } else {
                plugin.getScoreboardHandler().unloadScoreboard(user);
            }
        }
    }

    public boolean isRegion(
            Player player,
            String regionName
    ) {

        Location playerLocation = player.getLocation();

        WorldGuardPlugin worldGuardPlugin = WorldGuardPlugin.inst();
        RegionManager regionManager = worldGuardPlugin.getRegionManager(playerLocation.getWorld());

        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(playerLocation);
        Set<ProtectedRegion> protectedRegionSet = new HashSet<>(applicableRegionSet.getRegions());
        if(protectedRegionSet.size() == 0) return false;
        for(ProtectedRegion protectedRegion : protectedRegionSet){
            return protectedRegion.getId().equals(regionName);
        }
        return false;
    }

}
