package dev.blizzardlibrary.string.title;

import dev.blizzardlibrary.string.chat.FormatUtils;
import dev.blizzardlibrary.thread.kronos.KronosChain;
import dev.blizzardlibrary.other.NMSExtension;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.Collection;

public class ActionBarAPI {

    public static class PacketBase extends NMSExtension {

        private static PacketBase instance = null;
        private String nmsVersion;
        private boolean useOldMethods = false;
        private Class<?> packetPlayOutChatClass;
        //Old versions
        private Class<?> chatSerializerClass;
        private Class<?> iChatBaseComponentClass;
        //New Versions
        private Class<?> chatComponentTextClass;
        //--------------
        private boolean isRegistered = false;
        private Object packet;

        public static PacketBase getInstance() {
            if (instance == null) {
                instance = new PacketBase();
            }

            return instance;
        }

        public void register() {
            nmsVersion = Bukkit.getBukkitVersion().getClass().getPackage().getName();
            nmsVersion = nmsVersion.substring(nmsVersion.lastIndexOf(".") + 1);
            if (nmsVersion.equalsIgnoreCase("v1_8_R1") || nmsVersion.startsWith("v1_7_")) {
                useOldMethods = true;
            }
            try {
                packetPlayOutChatClass = getNMSClass("PacketPlayOutChat");
                if (useOldMethods) {
                    chatSerializerClass = getNMSClass("ChatSerializer");
                    iChatBaseComponentClass = getNMSClass("IChatBaseComponent");
                } else {
                    chatComponentTextClass = getNMSClass("ChatComponentText");
                    iChatBaseComponentClass = getNMSClass("IChatBaseComponent");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            isRegistered = true;
        }

        private void send(Player player, String message) {
            if (!isRegistered) {
                throw new NullPointerException("Registry for Action Bar is false. Contact the Developer of this plugin to fix it!");
            }

            try {
                if (useOldMethods) {
                    Method method = chatSerializerClass.getDeclaredMethod("a", String.class);
                    Object cbc = iChatBaseComponentClass.cast(method.invoke(chatSerializerClass, "{\"text\": \"" + FormatUtils.color(message) + "\"}"));
                    packet = packetPlayOutChatClass.getConstructor(new Class[]{iChatBaseComponentClass, byte.class}).newInstance(cbc, (byte) 2);
                } else {
                    try {
                        Class<?> chatMessageTypeClass = getNMSClass("ChatMessageType");
                        Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
                        Object chatMessageType = null;
                        for (Object obj : chatMessageTypes) {
                            if (obj.toString().equals("GAME_INFO"))
                                chatMessageType = obj;
                        }
                        Object chatCompontentText = chatComponentTextClass.getConstructor(new Class[]{String.class}).newInstance(FormatUtils.color(message));
                        packet = packetPlayOutChatClass.getConstructor(new Class[]{iChatBaseComponentClass, chatMessageTypeClass}).newInstance(chatCompontentText, chatMessageType);
                    } catch (ClassNotFoundException cnfe) {
                        Object chatCompontentText = chatComponentTextClass.getConstructor(new Class[]{String.class}).newInstance(FormatUtils.color(message));
                        packet = packetPlayOutChatClass.getConstructor(new Class[]{iChatBaseComponentClass, byte.class}).newInstance(chatCompontentText, (byte) 2);
                    }
                }

                sendPacket(player, packet);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void sendActionBarToPlayer(Player player, String message) {
            send(player, message);
        }

        public void sendActionBarToPlayerAsync(Player player, String message) {
            KronosChain.createCompletedKronosChain().runAsync(() -> sendActionBarToPlayer(player, message));
        }

        public void sendActionBarToAll(String message) {
            if (isEmpty()) {
                return;
            }

            for (Player all : getOnlinePlayers()) {
                sendActionBarToPlayer(all, message);
            }
        }

        public void sendActionBarToAllAsync(String message) {
            KronosChain.createCompletedKronosChain().runAsync(() -> sendActionBarToAll(message));
        }
    }

    public static class ServerBase {

        //Only use this for 1.9+
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
            if (contains("1.9") || contains("1.10") || contains("1.11") || contains("1.12")
                    || contains("1.13") || contains("1.14") || contains("1.15") || contains("1.16")) {
                isRegistered = true;
            }
        }

        public void sendActionBarToPlayer(Player player, String message) {
            if (!isRegistered || player == null) {
                return;
            }
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(FormatUtils.color(message)));
        }

        public void sendActionBarToPlayerAsync(Player player, String message) {
            KronosChain.createCompletedKronosChain().runAsync(() -> sendActionBarToPlayer(player, message));
        }

        public void sendActionBarToAll(String message) {
            Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
            if (!isRegistered || onlinePlayers.isEmpty()) {
                return;
            }


            onlinePlayers.forEach(player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(FormatUtils.color(message))));
        }

        public void sendActionBarToAllAsync(String message) {
            KronosChain.createCompletedKronosChain().runAsync(() -> sendActionBarToAll(message));
        }

        private boolean contains(String text) {
            return version.contains(text);
        }
    }
}