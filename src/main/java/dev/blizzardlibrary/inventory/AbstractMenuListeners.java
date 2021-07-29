package dev.blizzardlibrary.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class AbstractMenuListeners implements Listener {

    private JavaPlugin plugin;

    public AbstractMenuListeners(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getWhoClicked();
        UUID playerUUID = player.getUniqueId();

        UUID inventoryUUID = AbstractMenu.getInventoriesOpened().get(playerUUID);
        if (inventoryUUID != null) {
            e.setCancelled(true);
            AbstractMenu abstractMenu =  AbstractMenu.getInventoriesByUUID().get(inventoryUUID);
            IMenuClick action = abstractMenu.getActions().get(e.getSlot());

            if (action != null) {
                action.click(player);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if (!AbstractMenu.getInventoriesOpened().containsKey(playerUUID)) {
            return;
        }
        AbstractMenu.getInventoriesOpened().remove(playerUUID);
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if (!AbstractMenu.getInventoriesOpened().containsKey(playerUUID)) {
            return;
        }
        AbstractMenu.getInventoriesOpened().remove(playerUUID);
    }
}
