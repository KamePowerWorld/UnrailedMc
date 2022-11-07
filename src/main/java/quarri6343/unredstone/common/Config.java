package quarri6343.unredstone.common;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import quarri6343.unredstone.UnRedstone;

import java.io.File;

public class Config {

    public Config() {
    }

    public void loadConfig() {
        JavaPlugin plugin = UnRedstone.getInstance();
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();
        UnRedstoneData data = UnRedstone.getInstance().data;

        data.clearTeam();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (config.getString("team.name." + i) == null) {
                break;
            }
            data.addTeam(config.getString("team.name." + i), config.getString("team.color." + i));
            data.getTeam(i).startLocation = config.getLocation("team.startLocation." + i);
            data.getTeam(i).endLocation = config.getLocation("team.endLocation." + i);
        }
    }

    public void saveConfig() {
        resetConfig();//reset to stop messing with old data
        
        JavaPlugin plugin = UnRedstone.getInstance();
        FileConfiguration config = plugin.getConfig();
        UnRedstoneData data = UnRedstone.getInstance().data;
        
        for (int i = 0; i < data.getTeamsLength(); i++) {
            config.set("team.name." + i, data.getTeam(i).name);
            config.set("team.color." + i, data.getTeam(i).color);
            config.set("team.startLocation." + i, data.getTeam(i).startLocation);
            config.set("team.endLocation." + i, data.getTeam(i).endLocation);
        }

        plugin.saveConfig();
    }
    
    public void resetConfig(){
        JavaPlugin plugin = UnRedstone.getInstance();
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        
        if(configFile.delete()){
            plugin.saveDefaultConfig();
            plugin.reloadConfig();
        }
    }
}