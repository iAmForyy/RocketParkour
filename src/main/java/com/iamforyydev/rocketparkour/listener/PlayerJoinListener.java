package com.iamforyydev.rocketparkour.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener
        implements Listener {

    @EventHandler
    public void onPlayerJoin(
            PlayerJoinEvent joinEvent
    ) {
        joinEvent.setJoinMessage(null);

    }

}
