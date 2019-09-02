package ru.ibusewinner.spigot.free.rp;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class TimerRP {

    public static void timer() {
        new BukkitRunnable() {

            @Override
            public void run() {
                if(!CommandsListener.cd.isEmpty()) {
                    for(UUID uuid : CommandsListener.cd.keySet()) {
                        int time = CommandsListener.cd.get(uuid);

                        if(time <= 0) {
                            CommandsListener.cd.remove(uuid);
                        } else {
                            CommandsListener.cd.put(uuid, time-1);
                        }
                    }
                }
            }

        }.runTaskTimer(RPCommands.plugin, 0, 20);
    }

}
