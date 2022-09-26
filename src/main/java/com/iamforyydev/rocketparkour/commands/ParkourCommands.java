package com.iamforyydev.rocketparkour.commands;

import com.iamforyydev.rocketparkour.RocketParkour;
import com.iamforyydev.rocketparkour.object.Parkour;
import com.iamforyydev.rocketparkour.utils.LocationUtility;
import com.iamforyydev.rocketparkour.utils.RocketUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ParkourCommands
        implements CommandExecutor {

    private final RocketParkour plugin = RocketParkour.getInstance();

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (sender instanceof ConsoleCommandSender) {
            return false;
        }

        if(args.length == 0){
            return true;
        }

        Player player = (Player) sender;
        if (args[0].equalsIgnoreCase("create")) {

            FileConfiguration configuration = plugin.getCustomConfig("parkour.yml");
            ConfigurationSection configurationSection =
                    configuration.createSection("Parkour."+args[1]);
            configurationSection.set("startPoint", "world;0.0;0.0;0.0;0.0;0.0");
            configurationSection.set("endPoint", "world;0.0;0.0;0.0;0.0;0.0");
            configurationSection.set("checkPoints",
                    new ArrayList<>()
                    /*Arrays.asList("world;0.0;0.0;0.0;0.0;0.0:1", "world;0.0;0.0;0.0;0.0;0.0:2")*/);
            plugin.saveCustomConfig("parkour.yml");

            player.sendMessage(
                    RocketUtils.toFormat("&aYou have created the parkour &2"+args[1])
            );
            return true;
        }

        if (args[0].equalsIgnoreCase("setstartpoint")) {

            FileConfiguration configuration = plugin.getCustomConfig("parkour.yml");
            Parkour parkour = plugin.getParkourHandler().getParkourFromName(args[1]);
            if(parkour != null){
                Location playerLocation = player.getLocation().getBlock().getLocation();
                configuration.set(
                        "Parkour." + args[1] + ".startPoint",
                        LocationUtility.write(playerLocation)
                );

                plugin.saveCustomConfig("parkour.yml");
                player.sendMessage(
                        RocketUtils.toFormat("&aYou have set start spawn successfully!")
                );
                return true;
            }

            player.sendMessage(
                    RocketUtils.toFormat("&cThat parkour does not exist")
            );
            return true;
        }

        if (args[0].equalsIgnoreCase("setendpoint")) {

            FileConfiguration configuration = plugin.getCustomConfig("parkour.yml");
            Parkour parkour = plugin.getParkourHandler().getParkourFromName(args[1]);
            if(parkour != null){
                Location playerLocation = player.getLocation().getBlock().getLocation();
                configuration.set(
                        "Parkour." + args[1] + ".endPoint",
                        LocationUtility.write(playerLocation)
                );

                plugin.saveCustomConfig("parkour.yml");
                player.sendMessage(
                        RocketUtils.toFormat("&aYou have set end spawn successfully!")
                );
                return true;
            }

            player.sendMessage(
                    RocketUtils.toFormat("&cThat parkour does not exist")
            );

            return true;
        }

        if (args[0].equalsIgnoreCase("addcheckpoint")) {

            Parkour parkour = plugin.getParkourHandler().getParkourFromName(args[1]);
            if(parkour != null){

                FileConfiguration configuration = plugin.getCustomConfig("parkour.yml");
                Location location = player.getLocation().getBlock().getLocation();
                List<String> strings = configuration.getStringList("Parkour." + args[1] + ".checkPoints");
                strings.add(LocationUtility.write(location)+":"+(strings.size()+1));

                configuration.set(
                        "Parkour." + args[1] + ".checkPoints",
                        strings
                );

                plugin.saveCustomConfig("parkour.yml");
                player.sendMessage(
                        RocketUtils.toFormat("&aCheckPoint add!")
                );
                return true;
            }

            player.sendMessage(
                    RocketUtils.toFormat("&cThat parkour does not exist")
            );
        }

        return true;
    }
}
