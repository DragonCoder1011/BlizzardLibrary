package dev.blizzardlibrary;

import dev.blizzardlibrary.inventory.AbstractMenuListeners;
import dev.blizzardlibrary.builder.BuilderAccess;
import dev.blizzardlibrary.string.title.ActionBarAPI;
import dev.blizzardlibrary.string.title.TitleAPI;
import dev.blizzardlibrary.task.effort.EffortLoadTask;
import dev.blizzardlibrary.task.kronos.KronosRunMethods;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BlizzardLibraryAPI {

    private static BlizzardLibraryAPI libraryAPI = null;
    private BuilderAccess builderAccess;
    private EffortLoadTask effortLoadTask;
    private KronosRunMethods kronosRunMethods;

    public static BlizzardLibraryAPI getLibraryAPI() {
        if (libraryAPI == null) {
            libraryAPI = new BlizzardLibraryAPI();
        }

        return libraryAPI;
    }

    public void setPlugin(JavaPlugin plugin) {
        PluginUtil.setPlugin(plugin);
    }

    public JavaPlugin getPlugin() {
        return PluginUtil.getPlugin();
    }

    public void initValues() {
        builderAccess = new BuilderAccess();
        effortLoadTask = new EffortLoadTask();
        kronosRunMethods = new KronosRunMethods();
        registerListeners();
        registerActionBar();
        registerTitleAPI();
    }

    public BuilderAccess getBuilderAccess() {
        return builderAccess;
    }

    public EffortLoadTask getEffortLoadTask() {
        return effortLoadTask;
    }

    public KronosRunMethods getRunMethods() {
        return kronosRunMethods;
    }

    private void registerActionBar() {
        ActionBarAPI.ServerBase.getInstance().register();
        ActionBarAPI.PacketBase.getInstance().register();
    }

    private void registerTitleAPI() {
        TitleAPI.ServerBase.getInstance().register();
    }

    private void registerListeners() {
        addListener(new AbstractMenuListeners(getPlugin()));
    }

    private void addListener(Listener... listeners) {
        for (Listener allListeners : listeners) {
            final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
            pluginManager.registerEvents(allListeners, getPlugin());
        }
    }
}
