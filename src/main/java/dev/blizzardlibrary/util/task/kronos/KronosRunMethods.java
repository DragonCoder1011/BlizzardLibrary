package dev.blizzardlibrary.util.task.kronos;

import dev.blizzardlibrary.BlizzardLibraryAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class KronosRunMethods {

    private final JavaPlugin plugin = BlizzardLibraryAPI.getPlugin();

    public void runTask(Runnable runnable, ThreadContext context, long delay) {
        if (delay > 0) {
            runTaskLater(runnable, context, delay);
        } else {
            switch (context) {
                case SYNC:
                    if (ThreadContext.getContext(Thread.currentThread()) == ThreadContext.SYNC) {
                        runnable.run();
                    } else {
                        plugin.getServer().getScheduler().runTask(plugin, runnable);
                    }
                    break;
                case ASYNC:
                    plugin.getServer().getScheduler().runTaskAsynchronously(plugin, runnable);
                    break;
                default:
                    throw new IllegalStateException("Context Issue: " + context);
            }
        }
    }

    private void runTaskLater(Runnable runnable, ThreadContext context, long delay) {
        switch (context) {
            case SYNC:
                plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay);
                break;
            case ASYNC:
                plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
                break;
            default:
                throw new IllegalStateException("Context Issue: " + context);
        }
    }
}

