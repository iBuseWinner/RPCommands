package ru.ibusewinner.spigot.free.rp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RPCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        RPCommands.getInstance().reloadConfiguration();
        sender.sendMessage(RPCommands.getInstance().getStr("messages.reloadcfg"));
        return true;
    }
}
