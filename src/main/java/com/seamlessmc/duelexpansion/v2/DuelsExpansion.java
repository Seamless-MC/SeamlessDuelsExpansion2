package com.seamlessmc.duelexpansion.v2;


import com.seamlessmc.duelexpansion.v2.papi.DuelTop;
import me.clip.placeholderapi.PlaceholderAPI;
import me.realized.duels.api.Duels;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Level;

public class DuelsExpansion extends JavaPlugin {
    public static DuelsExpansion instance;
    public static Duels duelAPI;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        if (instance == null) instance = this;
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            /*
             * We register the EventListener here, when PlaceholderAPI is installed.
             * Since all events are in the main class (this class), we simply use "this"
             */
        } else {
            /*
             * We inform about the fact that PlaceholderAPI isn't installed and then
             * disable this plugin to prevent issues.
             */
            getLogger().log(Level.WARNING, "Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if (Bukkit.getPluginManager().getPlugin("Duels") != null) {
            duelAPI = (Duels) Bukkit.getServer().getPluginManager().getPlugin("Duels");
            PlaceholderAPI.registerExpansion(new DuelTop());
        } else {
            getLogger().log(Level.INFO, "Could not find Duel plugin, duels functions will be disabled");
        }
    }
}
