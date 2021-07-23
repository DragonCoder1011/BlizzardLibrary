package dev.blizzardlibrary;

import org.bukkit.plugin.java.JavaPlugin;

public class PluginUtil {

    private static JavaPlugin plugin = null;

    protected static void setPlugin(JavaPlugin plugin) {
        PluginUtil.plugin = plugin;
    }

    protected static JavaPlugin getPlugin() {
        return plugin;
    }
}