package org.noble.mollyessentials;

import com.earth2me.essentials.Essentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.noble.mollyessentials.book.BookHandler;
import org.noble.mollyessentials.chat.ChatBotListener;
import org.noble.mollyessentials.chat.ChatHandler;
import org.noble.mollyessentials.commands.ServerMessageCommand;
import org.noble.mollyessentials.listener.RepairSignListener;
import org.noble.mollyessentials.listener.VoteListener;

public class MollyEssentials extends JavaPlugin implements CommandExecutor {
    private final BookHandler bookHandler = new BookHandler(this);


    // On plugin enable
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        // Register the command
        this.getCommand("color").setExecutor(this);
        this.getServer().getPluginManager().registerEvents(new ChatHandler(), this);
        this.getCommand("servermsg").setExecutor(new ServerMessageCommand());
        getServer().getPluginManager().registerEvents(new RepairSignListener(), this);
        getServer().getPluginManager().registerEvents(new VoteListener(), this);
        getServer().getPluginManager().registerEvents(new ChatBotListener(this), this);
        Essentials essentials = (Essentials) getServer().getPluginManager().getPlugin("Essentials");


        // Load the default configuration
        this.saveDefaultConfig();
    }

    // Command handler
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player && cmd.getName().equalsIgnoreCase("color")) {
            Player player = (Player) sender;

            if(args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                reloadConfig();
                player.sendMessage("§aConfig reloaded!");
                return true;
            }

            bookHandler.openColorCodesBook(player);

            return true;
        }
        return false;
    }

}
