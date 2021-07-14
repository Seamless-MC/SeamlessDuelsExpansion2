package com.seamlessmc.duelexpansion.v2.papi;

import com.seamlessmc.duelexpansion.v2.DuelsExpansion;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.realized.duels.api.kit.Kit;
import me.realized.duels.api.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import javax.swing.*;

public class DuelTop extends PlaceholderExpansion {
    public static String format = "N/A";
    /**
     * This method should always return true unless we
     * have a dependency we need to make sure is on the server
     * for our placeholders to work!
     *
     * @return always true since we do not have any dependencies.
     */
    @Override
    public boolean canRegister(){
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     *
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor(){
        return "DroppingAnvil";
    }

    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest
     * method to obtain a value if a placeholder starts with our
     * identifier.
     * <br>The identifier has to be lowercase and can't contain _ or %
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier(){
        return "duelleaderboard";
    }

    /**
     * This is the version of this expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * @return The version as a String.
     */
    @Override
    public String getVersion(){
        return "1.0.0";
    }

    /**
     * This is the method called when a placeholder with our identifier
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param  player
     *         A {@link org.bukkit.OfflinePlayer OfflinePlayer}.
     * @param  identifier
     *         A String containing the identifier/value.
     *
     * @return Possibly-null String of the requested identifier.
     */
    @Override
    public String onRequest(OfflinePlayer player, String identifier){
        if (DuelsExpansion.duelAPI != null) {
            if (format.equalsIgnoreCase("N/A")) {
                format = DuelsExpansion.instance.getConfig().getString("DuelTop.format", "N/A");
            }
            String sf = format;
            String sff = "";
            String s = identifier.split("_")[0];
            int i = Integer.parseInt(identifier.replace("_", "").replace(s, ""));
            if (s.equalsIgnoreCase("all")) {
                try {
                    UserManager.TopData data = DuelsExpansion.duelAPI.getUserManager().getTopRatings().getData().get(i);
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(data.getUuid());
                    sf = PlaceholderAPI.setBracketPlaceholders(offlinePlayer, format);
                    sf = sf.replace("&", "§");
                    sff = sf;
                    for (String si : sf.split(" ")) {
                        if (si.equalsIgnoreCase("%name%")) {
                            sff.replace(si, data.getName());
                        }
                        if (si.equalsIgnoreCase("%uuid%")) {
                            sff.replace(si, data.getUuid().toString());
                        }
                        if (si.equalsIgnoreCase("%elo%")) {
                            sff.replace(si, String.valueOf(data.getValue()));
                        }
                    }
                    return sff;
                } catch (IndexOutOfBoundsException e) {
                    return "N/A";
                }
            } else {
                Kit k = DuelsExpansion.duelAPI.getKitManager().get(s);
                try {
                    UserManager.TopData data = DuelsExpansion.duelAPI.getUserManager().getTopRatings(k).getData().get(i);
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(data.getUuid());
                    sf = PlaceholderAPI.setBracketPlaceholders(offlinePlayer, format);
                    sf = sf.replace("&", "§");
                    sff = sf;
                    for (String si : sf.split(" ")) {
                        if (si.equalsIgnoreCase("%name%")) {
                            sff.replace(si, data.getName());
                        }
                        if (si.equalsIgnoreCase("%uuid%")) {
                            sff.replace(si, data.getUuid().toString());
                        }
                        if (si.equalsIgnoreCase("%elo%")) {
                            sff.replace(si, String.valueOf(data.getValue()));
                        }
                    }
                    return sff;
                } catch (IndexOutOfBoundsException e) {
                    return "N/A";
                }
            }
        }
        return null;
    }
}
