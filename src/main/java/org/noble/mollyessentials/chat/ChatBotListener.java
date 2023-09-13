package org.noble.mollyessentials.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatBotListener implements Listener {

    private final JavaPlugin plugin;

    public ChatBotListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        // Extract the message the player sent
        String message = event.getMessage().toLowerCase();

        // Get the player object from the event
        Player player = event.getPlayer();

        // Check if the player has the permission to receive chatbot responses
        String requiredPermission = plugin.getConfig().getString("chatbot-permission");
        if (!player.hasPermission(requiredPermission)) {
            return; // Exit if the player doesn't have the required permission
        }

        ConfigurationSection responseConfig = plugin.getConfig().getConfigurationSection("chatbot-responses." + message);

        if (responseConfig == null) {
            return; // Exit if there's no configured response for the message
        }

        // Get the response from the config, replace placeholders, and colorize it
        String rawResponse = responseConfig.getString("message");
        if (rawResponse == null) {
            return; // Exit if the message is not set in the config
        }
        String replacedResponse = rawResponse.replace("%player_name%", player.getName());
        String coloredResponse = ChatColor.translateAlternateColorCodes('&', replacedResponse);

        // Get the delay from the config
        long delayTicks = responseConfig.getLong("delay", 0);

        // Schedule a task to run after the specified delay to send the response
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.sendMessage(coloredResponse);
        }, delayTicks);
    }


}
