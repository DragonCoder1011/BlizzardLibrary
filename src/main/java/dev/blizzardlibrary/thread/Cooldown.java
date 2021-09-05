package dev.blizzardlibrary.thread;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import dev.blizzardlibrary.string.chat.MessageAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class Cooldown {

    private static Cooldown instance = null;
    private final Table<String, String, Long> cooldowns = HashBasedTable.create();

    public static Cooldown getInstance() {
        if (instance == null) {
            instance = new Cooldown();
        }

        return instance;
    }

    private boolean containsCooldown(String player, String key) {
        return cooldowns.contains(player, key);
    }

    private long getCooldown(String player, String key) {
        return cooldowns.get(player, key);
    }


    private void setCooldown(String player, String key, long delay) {
        cooldowns.put(player, key, BlizzardTimeUnit.currentMillis() + BlizzardTimeUnit.MILLIS.toSeconds(delay));
    }


    public void removeCooldown(String player, String key) {
        cooldowns.remove(player, key);
    }

    public void removeCooldowns(String player) {
        cooldowns.row(player).clear();
    }

    public void addToCoolDownForListener(String player, Player p, String cooldownKey, String message, Cancellable event, long coolDown) {
        if (containsCooldown(player, cooldownKey) && getCooldown(player, cooldownKey) > BlizzardTimeUnit.currentMillis()) {
            MessageAPI.sendFormattedMessage(message, p);
            if (event == null) {
                return;
            }
            event.setCancelled(true);
        } else {
            removeCooldown(player, cooldownKey);
            setCooldown(player, cooldownKey, coolDown);
        }
    }

    public long getRemainingTime(String player, String cooldownKey) {
        return getCooldown(player, cooldownKey) - BlizzardTimeUnit.currentMillis();
    }

    public void addCoolDownForOther(String player, Player p, String cooldownKey, String message, long coolDown) {
        addToCoolDownForListener(player, p, cooldownKey, message, null, coolDown);
    }
}
