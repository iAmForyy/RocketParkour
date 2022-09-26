package com.iamforyydev.rocketparkour.files;

import com.iamforyydev.rocketparkour.RocketParkour;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ParkourFile {
    
    private static ParkourFile instance;
    public static ParkourFile getInstance() {
        if (instance == null) {
            instance = new ParkourFile();
        }
        return instance;
    }

    private final RocketParkour PLUGIN = RocketParkour.getInstance();
    private final Map<String, FileConfiguration> fileConfigurationMap = new HashMap<>();

    public boolean isLoaded(String fileName){
        return getFileConfigurationMap().containsKey(fileName);
    }

    public void loadFile(String fileName) {
        if(getFileConfigurationMap().containsKey(fileName)) return;

        File file = new File(PLUGIN.getDataFolder(), fileName);
        if(!file.exists()){
            PLUGIN.saveResource(fileName, false);
        }

        if(!isLoaded(fileName)){
            getFileConfigurationMap().put(fileName, YamlConfiguration.loadConfiguration(file));
        }
    }

    public FileConfiguration get(String paramString){
        if (isLoaded(paramString)) {
            return getFileConfigurationMap().get(paramString);
        }
        return null;
    }

    public void save(String paramString) {
        File file = new File(PLUGIN.getDataFolder(), paramString);
        if (isLoaded(paramString)) {
            try {
                getFileConfigurationMap().get(paramString).save(file);
            }catch (Exception fileException){
                fileException.printStackTrace();
            }
        }
    }

    public Map<String, FileConfiguration> getFileConfigurationMap() {
        return fileConfigurationMap;
    }
}
