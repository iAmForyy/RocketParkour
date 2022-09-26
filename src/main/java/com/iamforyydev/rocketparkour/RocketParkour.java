package com.iamforyydev.rocketparkour;

import com.iamforyydev.rocketparkour.commands.ParkourCommands;
import com.iamforyydev.rocketparkour.files.ParkourFile;
import com.iamforyydev.rocketparkour.handler.ParkourHandler;
import com.iamforyydev.rocketparkour.handler.ScoreboardHandler;
import com.iamforyydev.rocketparkour.listener.PlayerInteractListener;
import com.iamforyydev.rocketparkour.listener.PlayerJoinListener;
import com.iamforyydev.rocketparkour.listener.PlayerQuitListener;
import com.iamforyydev.rocketparkour.listener.WorldGuardListener;
import com.iamforyydev.rocketparkour.loader.Loader;
import com.iamforyydev.rocketparkour.storage.Storage;
import com.iamforyydev.rocketparkour.user.UserHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class RocketParkour extends JavaPlugin {

    private Storage storage;
    private UserHandler userHandler;
    private ScoreboardHandler scoreboardHandler;
    private ParkourHandler parkourHandler;
    private final FileConfiguration configuration = getConfig();
    private static RocketParkour instance;
    public static RocketParkour getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        ParkourFile.getInstance().loadFile("parkour.yml");
        ParkourFile.getInstance().save("parkour.yml");
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        this.parkourHandler = new ParkourHandler();
        this.storage = new Storage(
                configuration.getString("database.hostname"),
                configuration.getString("database.port"),
                configuration.getString("database.database"),
                configuration.getString("database.username"),
                configuration.getString("database.password")
        );

        this.storage.createTable();
        this.userHandler = new UserHandler();
        this.scoreboardHandler = new ScoreboardHandler(this);

        new Loader(this);
        commandHandler();
        eventHandler(
                new PlayerJoinListener(),
                new PlayerQuitListener(),
                new PlayerInteractListener(),
                new WorldGuardListener()
        );
    }

    @Override
    public void onDisable(
    ){
        getStorage().disconnect();
    }

    private void commandHandler(
    ) {
        getCommand("parkour").setExecutor(new ParkourCommands());

    }

    private void eventHandler(
            Listener... listeners
    ) {
        Arrays.stream(listeners).forEach(listener ->
                getServer().getPluginManager().registerEvents(listener, this)
        );
    }


    public Storage getStorage(
    ) {
        return this.storage;
    }

    public UserHandler getUserHandler(
    ) {
        return this.userHandler;
    }

    public ScoreboardHandler getScoreboardHandler() {
        return scoreboardHandler;
    }

    public ParkourHandler getParkourHandler() {
        return parkourHandler;
    }

    public void saveCustomConfig(
            String configName
    ) {
        ParkourFile.getInstance().save(configName);
    }

    public FileConfiguration getCustomConfig(
            String configName
    ) {
        return ParkourFile.getInstance().get(configName);
    }

}
