package dev.blizzardlibrary;

import dev.blizzardlibrary.util.task.effort.EffortLoadTask;
import dev.blizzardlibrary.util.task.kronos.KronosRunMethods;
import org.bukkit.plugin.java.JavaPlugin;

public class BlizzardLibraryAPI {

    private static BlizzardLibraryAPI libraryAPI = null;
    private EffortLoadTask effortLoadTask;
    private KronosRunMethods kronosRunMethods;

    public static BlizzardLibraryAPI getLibraryAPI() {
        if (libraryAPI == null) {
            libraryAPI = new BlizzardLibraryAPI();
        }

        return libraryAPI;
    }

    public static void setPlugin(Class<?> clazz) {
        PluginUtil.setPlugin(clazz);
    }

    public static JavaPlugin getPlugin() {
        return PluginUtil.getPlugin();
    }

    public void initValues() {
        effortLoadTask = new EffortLoadTask();
        kronosRunMethods = new KronosRunMethods();
    }

    public EffortLoadTask getEffortLoadTask() {
        return effortLoadTask;
    }

    public KronosRunMethods getRunMethods() {
        return kronosRunMethods;
    }
}
