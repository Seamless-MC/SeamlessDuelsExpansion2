package com.seamlessmc.duelexpansion.v2.papi;

import com.seamlessmc.duelexpansion.v2.DuelsExpansion;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.realized.duels.api.kit.Kit;
import me.realized.duels.api.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.lang.reflect.Method;

public class DuelTop extends PlaceholderExpansion {
    public static String format = "&8Not Available";
    public static Method trueELO;
    @Override
    public boolean canRegister(){
        return true;
    }
    @Override
    public String getAuthor(){
        return "DroppingAnvil";
    }
    @Override
    public String getIdentifier(){
        return "duelleaderboard";
    }
    @Override
    public String getVersion(){
        return "1.0.0";
    }
    @Override
    public String onRequest(OfflinePlayer player, String identifier){
        if (DuelsExpansion.duelAPI != null) {
            if (format.equalsIgnoreCase("&8Not Available")) {
                format = DuelsExpansion.instance.getConfig().getString("DuelTop.format", "&8Not Available");
            }
            String sf = format;
            String sff = "";
            String s = identifier.split("_")[0];
            int i = Integer.parseInt(identifier.replace("_", "").replace(s, ""));
            if (s.equalsIgnoreCase("all")) {
                try {
                    UserManager.TopData data = DuelsExpansion.duelAPI.getUserManager().getTopRatings().getData().get(i);
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(data.getUuid());
                    sf = PlaceholderAPI.setPlaceholders(offlinePlayer, format);
                    sf = sf.replace("&", "§");
                    return sf.replace("%name%", data.getName()).replace("%uuid%", data.getUuid().toString()).replace("%elo%", String.valueOf(data.getValue()));
                } catch (IndexOutOfBoundsException e) {
                    return "&8Not Available";
                }
            } else {
                Kit k = DuelsExpansion.duelAPI.getKitManager().get(s);
                try {
                    UserManager.TopData data = DuelsExpansion.duelAPI.getUserManager().getTopRatings(k).getData().get(i);
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(data.getUuid());
                    sf = PlaceholderAPI.setPlaceholders(offlinePlayer, format);
                    sf = sf.replace("&", "§");
                    return sf.replace("%name%", data.getName()).replace("%uuid%", data.getUuid().toString()).replace("%elo%", String.valueOf(data.getValue()));
                } catch (IndexOutOfBoundsException e) {
                    return "&8Not Available";
                }
            }
        }
        return "&8Duels Not Available";
    }
}
