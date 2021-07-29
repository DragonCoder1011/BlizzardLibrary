package dev.blizzardlibrary.task.lambda;

import dev.blizzardlibrary.BlizzardLibraryAPI;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

public interface IBukkitRunnable {


    default BukkitRunnable runTaskTimer(IThreadContext threadContext, Consumer<BukkitRunnable> block, long l1, long l2) {
        final JavaPlugin plugin = BlizzardLibraryAPI.getLibraryAPI().getPlugin();
        final BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                block.accept(this);
            }
        };
        if (threadContext == IThreadContext.ASYNC) {
            runnable.runTaskTimerAsynchronously(plugin, l1, l2);
            return runnable;
        } else if (threadContext == IThreadContext.SYNC) {
            runnable.runTaskTimer(plugin, l1, l2);
            return runnable;
        } else {
            return null;
        }
    }

    default BukkitRunnable runTaskLater(IThreadContext threadContext, Consumer<BukkitRunnable> block, long l1) {
        final JavaPlugin plugin = BlizzardLibraryAPI.getLibraryAPI().getPlugin();
        final BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                block.accept(this);
            }
        };
        if (threadContext == IThreadContext.ASYNC) {
            runnable.runTaskLaterAsynchronously(plugin, l1);
            return runnable;
        } else if (threadContext == IThreadContext.SYNC) {
            runnable.runTaskLater(plugin, l1);
            return runnable;
        } else {
            return null;
        }
    }
}
