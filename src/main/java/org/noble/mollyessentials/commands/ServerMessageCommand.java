package org.noble.mollyessentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServerMessageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /servermsg <player> <message>");
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            sender.sendMessage("§cThe specified player is not online.");
            return true;
        }

        StringBuilder message = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            message.append(args[i]).append(" ");
        }

        String finalMessage = "§6[Server]: " + message.toString().trim();
        targetPlayer.sendMessage(finalMessage);

        // Confirmation message to sender
        sender.sendMessage("§aMessage sent to " + targetPlayer.getName() + ": " + finalMessage);

        return true;
    }
}
