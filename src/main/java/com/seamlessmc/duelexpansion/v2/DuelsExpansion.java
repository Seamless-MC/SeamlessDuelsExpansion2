package com.seamlessmc.duelexpansion.v2;


import com.seamlessmc.duelexpansion.v2.papi.DuelTop;
import me.realized.duels.DuelsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class DuelsExpansion extends JavaPlugin {
    public static DuelsExpansion instance;
    public static DuelsPlugin duelAPI;
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
            duelAPI = ((DuelsPlugin) Bukkit.getServer().getPluginManager().getPlugin("Duels"));
            if (new DuelTop().register()) {
                System.out.println("Successfully hooked PlaceholderAPI!");
            } else {
                System.out.println("Failed to hook PlaceholderAPI!");
            }
        } else {
            getLogger().log(Level.INFO, "Could not find Duel plugin, duels functions will be disabled");
        }
    }
}
