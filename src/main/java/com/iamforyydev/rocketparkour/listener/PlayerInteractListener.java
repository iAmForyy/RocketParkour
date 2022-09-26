package com.iamforyydev.rocketparkour.listener;

import com.iamforyydev.rocketparkour.RocketParkour;
import com.iamforyydev.rocketparkour.object.Parkour;
import com.iamforyydev.rocketparkour.user.User;
import com.iamforyydev.rocketparkour.utils.RocketUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener
        implements Listener {

    private final RocketParkour plugin = RocketParkour.getInstance();

    @EventHandler
    public void onPlayerInteract(
            PlayerInteractEvent interactEvent
    ) {

        if (!interactEvent.getAction().equals(Action.PHYSICAL)) {
            return;
        }

        Player player = interactEvent.getPlayer();
        User user = plugin.getUserHandler().getUserByUuid(player.getUniqueId());
        Location interactLocation = interactEvent.getClickedBlock().getLocation();
        if (user.isInParkour()) {
            if (plugin.getParkourHandler().isCheckPoint(interactLocation)) {
                int checkPointId = plugin.getParkourHandler().getCheckpointIdByLocation(interactLocation);
                if(user.getCheckpoint() == checkPointId) {
                    return;
                }

                user.setCheckpoint(
                        checkPointId
                );

                player.sendMessage(
                        RocketUtils.toFormat("&aCongratulations! You have reached CheckPoint #"+checkPointId)
                );
                return;
            }

            Parkour parkour = plugin.getParkourHandler().getParkourFromEndLocation(interactLocation);
            if (parkour != null) {
                parkour.endParkour(user);
            }

            return;
        }

        Parkour parkour = plugin.getParkourHandler().getParkourFromStartLocation(interactLocation);
        if (parkour != null) {
            parkour.startParkour(user);
        }

    }
}
