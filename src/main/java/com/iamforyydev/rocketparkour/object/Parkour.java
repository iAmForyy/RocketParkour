package com.iamforyydev.rocketparkour.object;

import com.iamforyydev.rocketparkour.RocketParkour;
import com.iamforyydev.rocketparkour.user.User;
import com.iamforyydev.rocketparkour.utils.RocketUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Parkour
        implements ParkourInterace {

    private final RocketParkour plugin = RocketParkour.getInstance();
    private final String name;

    private final Location startPoint;
    private final List<CheckPoint> checkPointList;
    private final Location endPoint;

    public Parkour(
            final String name,
            final Location startPoint,
            final List<CheckPoint> checkPointList,
            final Location endPoint
    ) {
        this.name = name;
        this.startPoint = startPoint;
        this.checkPointList = checkPointList;
        this.endPoint = endPoint;
    }

    @Override
    public void startParkour(
            User user
    ) {

        user.setInParkour(true);
        Player player = user.getPlayer();
        plugin.getParkourHandler().getUserIntegerMap().put(
                user, new BukkitRunnable() {
                    @Override
                    public void run() {
                        user.increaseTime();
                    }
                }.runTaskTimer(plugin, 0L, 20L).getTaskId());
        player.sendMessage(
                RocketUtils.toFormat("&aYou have started parkour! Good luck :)")
        );
    }

    @Override
    public void endParkour(
            User user
    ) {

        Bukkit.getScheduler().cancelTask(
                plugin.getParkourHandler().getTaskIDFromUser(user)
        );

        plugin.getStorage().saveUser(user);
        user.setInParkour(false);
        user.getPlayer().sendMessage(
                RocketUtils.toFormat("&aYou have finished parkour in "+user.getTime()+" seconds")
        );

        plugin.getUserHandler().deleteUser(user);
    }

    public String getName() {
        return name;
    }

    public Location getStartPoint() {
        return startPoint;
    }

    public List<CheckPoint> getCheckPointList() {
        return checkPointList;
    }

    public Location getEndPoint() {
        return endPoint;
    }
}
