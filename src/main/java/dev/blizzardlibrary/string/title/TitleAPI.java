package dev.blizzardlibrary.string.title;

import dev.blizzardlibrary.string.chat.FormatUtils;
import dev.blizzardlibrary.thread.kronos.KronosChain;
import dev.blizzardlibrary.other.NMSExtension;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.util.Collection;

public class TitleAPI {

    public static class PacketBase extends NMSExtension {

        private static PacketBase instance = null;

        public static PacketBase getInstance() {
            if (instance == null) {
                instance = new PacketBase();
            }

            return instance;
        }

        private void send(Player player, int fadeInTime, int showTime, int fadeOutTime, String title, String subtitle) {
            try {
                Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                        .invoke(null, "{\"text\": \"" + FormatUtils.color(title) + "\"}");
                Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                        getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
                        int.class, int.class, int.class);
                Object packet = titleConstructor.newInstance(
                        getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle,
                        fadeInTime, showTime, fadeOutTime);

                Object chatsTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                        .invoke(null, "{\"text\": \"" + FormatUtils.color(subtitle) + "\"}");
                Constructor<?> stitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                        getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
                        int.class, int.class, int.class);
                Object spacket = stitleConstructor.newInstance(
                        getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatsTitle,
                        fadeInTime, showTime, fadeOutTime);

                sendPacket(player, packet);
                sendPacket(player, spacket);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void sendTitleToPlayer(Player player, int fadeInTime, int showTime, int fadeOutTime, String title, String subtitle) {
            send(player, fadeInTime, showTime, fadeOutTime, title, subtitle);
        }

        public void sendTitleToPlayerAsync(Player player, int fadeInTime, int showTime, int fadeOutTime, String title, String subtitle) {
            KronosChain.createCompletedKronosChain().runAsync(() -> sendTitleToPlayer(player, fadeInTime, showTime, fadeOutTime, title, subtitle));
        }

        public void sendTitleToAll(int fadeInTime, int showTime, int fadeOutTime, String title, String subTitle) {
            if (isEmpty()) {
                return;
            }

            for (Player all : getOnlinePlayers()) {
                sendTitleToPlayer(all, fadeInTime, showTime, fadeOutTime, title, subTitle);
            }
        }

        public void sendTitleToAllAsync(int fadeInTime, int showTime, int fadeOutTime, String title, String subTitle) {
            KronosChain.createCompletedKronosChain().runAsync(() -> sendTitleToAll(fadeInTime, showTime, fadeOutTime, title, subTitle));
        }
    }

    public static class ServerBase {

        //Use this for versions 1.11+
        private static ServerBase instance = null;
        private String version;
        private boolean isRegistered = false;

        public static ServerBase getInstance() {
            if (instance == null) {
                instance = new ServerBase();
            }

            return instance;
        }

        public void register() {
            version = Bukkit.getVersion();
            if (contains("1.11") || contains("1.12") || contains("1.13") || contains("1.14") || contains("1.15") || contains("1.16")) {
                isRegistered = true;
            }
        }

        public void sendTitleToPlayer(Player player, String title, String subTitle, int fadeInTime, int showTime, int fadeOutTime) {
            if (!isRegistered) {
                return;
            }

            player.sendTitle(FormatUtils.color(title), FormatUtils.color(subTitle), fadeInTime, showTime, fadeOutTime);
        }

        public void sendTitleToPlayerAsync(Player player, String title, String subTitle, int fadeInTime, int showTime, int fadeOutTime) {
            KronosChain.createCompletedKronosChain().runAsync(() -> sendTitleToPlayer(player, title, subTitle, fadeInTime, showTime, fadeOutTime));
        }

        public void sendTitleToAll(String title, String subTitle, int fadeInTime, int showTime, int fadeOutTime) {
            Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
            if (!isRegistered || onlinePlayers.isEmpty()) {
                return;
            }

            onlinePlayers.forEach(player -> player.sendTitle(FormatUtils.color(title), FormatUtils.color(subTitle), fadeInTime, showTime, fadeOutTime));
        }

        public void sendTitleToAllAsync(String title, String subTitle, int fadeInTime, int showTime, int fadeOutTime) {
            KronosChain.createCompletedKronosChain().runAsync(() -> sendTitleToAll(title, subTitle, fadeInTime, showTime, fadeOutTime));
        }

        private boolean contains(String text) {
            return version.contains(text);
        }
    }
}
