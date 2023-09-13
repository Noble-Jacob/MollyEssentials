package org.noble.mollyessentials.chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatHandler implements Listener {

    // Regex pattern to match URLs
    private final Pattern urlPattern = Pattern.compile("(https?://[\\S]+)");

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Matcher matcher = urlPattern.matcher(event.getMessage());
        if (matcher.find()) {
            String url = matcher.group(1);

            TextComponent message = new TextComponent(event.getPlayer().getName() + ": " + event.getMessage().replace(url, ""));
            TextComponent linkComponent = new TextComponent(url);
            linkComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
            linkComponent.setColor(ChatColor.GRAY);
            linkComponent.setUnderlined(true);

            message.addExtra(linkComponent);

            event.setCancelled(true);
            event.getPlayer().getServer().spigot().broadcast(message);
        }
    }
}
