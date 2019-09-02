package ru.ibusewinner.spigot.free.rp;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class RPCommands extends JavaPlugin {

    protected static FileConfiguration cfg;
    protected static RPCommands plugin;

    @Override
    public void onEnable() {
        cfg = getConfig();
        plugin = this;
        loadCfg();
        Bukkit.getPluginCommand("rpcommands").setExecutor(new RPCmd());
        Bukkit.getPluginManager().registerEvents(new CommandsListener(), this);
        TimerRP.timer();
    }

    public String getStr(String path) {
        return cfg.getString(path).replaceAll("&", "§")
                .replaceAll("%prefix%", cfg.getString("messages.prefix").replaceAll("&", "§"));
    }

    public int getInt(String path) {
        return cfg.getInt(path);
    }

    protected static RPCommands getInstance() { return plugin; }

    @Override
    public void onDisable() {

    }

    public FileConfiguration loadCfg() {
        cfg = super.getConfig();
        cfg.options().copyDefaults(true);
        saveConfig();
        System.out.println(getStr("messages.loadcfg"));
        return cfg;
    }

    @Override
    public void saveConfig() {
        try {
            cfg.save(new File(getDataFolder()+ "/config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileConfiguration getConfig() {
        return cfg;
    }

    public void reloadConfiguration() {
        cfg = super.getConfig();

        try {
            cfg.load(new File(getDataFolder()+ "/config.yml"));
            saveConfig();
        } catch (Exception e) {
            loadCfg();
        }
    }

}
