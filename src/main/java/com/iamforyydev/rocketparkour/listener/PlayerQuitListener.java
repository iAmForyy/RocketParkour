package com.iamforyydev.rocketparkour.listener;

import com.iamforyydev.rocketparkour.RocketParkour;
import com.iamforyydev.rocketparkour.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener
        implements Listener {

    private final RocketParkour plugin = RocketParkour.getInstance();

    @EventHandler
    public void onPlayerQuit(
            PlayerQuitEvent quitEvent
    ) {
        quitEvent.setQuitMessage(null);

        Player player = quitEvent.getPlayer();
        User user = plugin.getUserHandler().getUserByUuid(player.getUniqueId());
        plugin.getStorage().saveUser(
                user /* SAVE TIME TO USER */
        );

        plugin.getScoreboardHandler().unloadScoreboard(
                user
        );

        plugin.getUserHandler().deleteUser(user);
    }


}
