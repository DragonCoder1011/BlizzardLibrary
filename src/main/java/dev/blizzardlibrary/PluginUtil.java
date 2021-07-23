package dev.blizzardlibrary;

import org.bukkit.plugin.java.JavaPlugin;

public class PluginUtil {

    private static JavaPlugin plugin = null;

    protected static void setPlugin(Class<?> clazz) {
        PluginUtil.plugin = JavaPlugin.getProvidingPlugin(clazz);
    }

    protected static JavaPlugin getPlugin() {
        return plugin;
    }
}