package ru.ibusewinner.spigot.free.rp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class CommandsListener implements Listener {

    protected static HashMap<UUID, Integer> cd = new HashMap<>();

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String cmd = e.getMessage();
        String[] args = cmd.split(" ");

        Set<String> keys = RPCommands.cfg.getConfigurationSection("commands").getKeys(false);
        for(String key : keys) {
            if(cmd.startsWith("/" + key)) {
                if(!p.hasPermission("commands."+key+".permission")) {
                    p.sendMessage(RPCommands.getInstance().getStr("messages.no-perm"));
                    e.setCancelled(true);
                    return;
                }

                int args_count = RPCommands.getInstance().getInt("commands."+key+".args-need");

                if(args.length != args_count+1) {
                    p.sendMessage(RPCommands.getInstance().getStr("messages.no-args")
                            .replaceAll("%args%", String.valueOf(args_count)));
                    e.setCancelled(true);
                    return;
                }

                if(cd.containsKey(p.getUniqueId())) {
                    p.sendMessage(RPCommands.getInstance().getStr("messages.cooldown")
                            .replaceAll("%seconds%", String.valueOf(cd.get(p.getUniqueId()))));
                    e.setCancelled(true);
                    return;
                }

                String name = p.getName();

                try {
                    Player target = Bukkit.getPlayer(args[1]);

                    if(target.hasPermission(RPCommands.getInstance().getStr("commands."+key+".cant-use-permission"))) {
                        p.sendMessage(RPCommands.getInstance().getStr("commands."+key+".cant-use"));
                        e.setCancelled(true);
                        return;
                    }

                    String target_name = target.getName();

                    int cooldown = RPCommands.getInstance().getInt("commands."+key+".cooldown");

                    if(!p.hasPermission(RPCommands.getInstance().getStr("commands."+key+".bypass-cooldown"))) {
                        cd.put(p.getUniqueId(), cooldown);
                    }

                    Bukkit.broadcastMessage(RPCommands.getInstance().getStr("settings."+key+".broadcast-message")
                            .replaceAll("%player%", name).replaceAll("%target%", target_name));
                } catch(Exception ex) {
                    p.sendMessage(RPCommands.getInstance().getStr("messages.player-cant-found"));
                }

                e.setCancelled(true);
            }
        }
    }

}
