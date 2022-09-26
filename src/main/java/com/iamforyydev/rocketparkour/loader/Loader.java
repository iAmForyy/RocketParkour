package com.iamforyydev.rocketparkour.loader;

import com.iamforyydev.rocketparkour.RocketParkour;
import com.iamforyydev.rocketparkour.object.CheckPoint;
import com.iamforyydev.rocketparkour.object.Parkour;
import com.iamforyydev.rocketparkour.utils.LocationUtility;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Loader {

    private final RocketParkour plugin;
    private final FileConfiguration configuration;
    public Loader(
            RocketParkour plugin
    ) {
        this.plugin = plugin;
        this.configuration = plugin.getCustomConfig("parkour.yml");
        load();
    }

    private void load(){
        ConfigurationSection configurationSection = configuration.getConfigurationSection("Parkour");
        if(configurationSection == null){
            return;
        }

        configurationSection.getKeys(false).forEach(name -> {

            ConfigurationSection parkourSection = configurationSection.getConfigurationSection(name);

            List<CheckPoint> checkPointList = new ArrayList<>();
            parkourSection.getStringList("checkPoints").forEach(index -> {
                String[] split = index.split(":");
                CheckPoint checkPoint = new CheckPoint(
                        Integer.parseInt(split[1]),
                        LocationUtility.read(split[0])
                );

                checkPointList.add(checkPoint);
            });

            Parkour parkour = new Parkour(
                    name,
                    LocationUtility.read(
                            parkourSection.getString("startPoint")
                    ),
                    checkPointList,
                    LocationUtility.read(
                            parkourSection.getString("endPoint")
                    )

            );
            plugin.getParkourHandler().getParkourMap().put(name, parkour);}
        );







    }




}
