package com.arthuramorim.scoreboardf.hooks;

import com.arthuramorim.scoreboardf.configuration.Configuration;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.entity.Player;

public class Factions {

    public Factions() {
    }

    public String getPlayerPower(Player p) {
        MPlayer player = MPlayer.get(p);
        return player.getPowerRounded() + "/" + player.getPowerMaxRounded();
    }

    public Faction getPlayerFaction(Player p) {
        MPlayer player = MPlayer.get(p);
        return player.getFaction();
    }

    public String getLocationName(Player p) {
        try {
            if (p.getWorld().getName().equalsIgnoreCase(Configuration.MINA_WORLD)) {
                return " §7Mundo de Mineração";
            } else if (p.getWorld().getName().equalsIgnoreCase(Configuration.VIP_WORLD)) {
                return " §7Mundo VIP";
            } else {
                return p.getWorld().getName().equalsIgnoreCase(Configuration.NORMAL_WORLD) ? BoardColl.get().getFactionAt(PS.valueOf(p.getLocation())).getName(MPlayer.get(p.getUniqueId())) : "  §cMundo desconhecido";
            }
        } catch (NullPointerException var3) {
            return "  §cMundo desconhecido";
        }
    }
}
