package com.arthuramorim.startscoreboard.hooks;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {

    private Economy economy;

    public Vault() {
        this.setupEconomy();
    }

    public double getPlayerBalance(Player p) {
        return this.economy.hasBankSupport() ? this.economy.bankBalance(p.getName()).balance : this.economy.getBalance(p);
    }

    private boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        } else {
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                return false;
            } else {
                this.economy = (Economy)rsp.getProvider();
                return this.economy != null;
            }
        }
    }
}
