package dev.blizzardlibrary.util;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Map;

public abstract class NMSExtension {

    public void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Class<?> getNMSClass(String name) throws ClassNotFoundException {
        final Map<String, Class<?>> nmsCacheMap = Maps.newConcurrentMap();
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        if (nmsCacheMap.containsKey(version)) {
            return nmsCacheMap.get(version);
        }

        Class<?> clazz = Class.forName("net.minecraft.server." + version + "." + name);
        nmsCacheMap.putIfAbsent(version, clazz);
        return clazz;
    }


    public Collection<? extends Player> getOnlinePlayers() {
        return Bukkit.getServer().getOnlinePlayers();
    }

    public boolean isEmpty() {
        return getOnlinePlayers().isEmpty();
    }
}
