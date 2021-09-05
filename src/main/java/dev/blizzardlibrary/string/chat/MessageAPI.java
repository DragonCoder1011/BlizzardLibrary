package dev.blizzardlibrary.string.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageAPI {

    public static void sendFormattedMessage(String text, Player player) {
        if (player != null) {
            player.sendMessage(FormatUtils.color(text));
        } else {
            Bukkit.broadcastMessage(FormatUtils.color(text));
        }
    }

    public static void sendFormattedMessage(String text) {
        sendFormattedMessage(text, null);
    }

    public static void sendCenteredFormattedMessage(String text, Player player) {
        if (player != null) {
            player.sendMessage(FormatUtils.centered(text));
        } else {
            Bukkit.broadcastMessage(FormatUtils.centered(text));
        }
    }

    public static void sendCenteredFormattedMessage(String text) {
        sendCenteredFormattedMessage(text, null);
    }

    public static void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(FormatUtils.color(message));
    }
}
