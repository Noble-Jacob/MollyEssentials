package org.noble.mollyessentials.book;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class BookHandler {
    private final JavaPlugin plugin;

    public BookHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void openColorCodesBook(Player player) {
        // Create a new written book
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        // Generate pages with color codes
        List<String> colorPages = generateColorCodeList();
        for (String pageContent : colorPages) {
            meta.addPage(pageContent);
        }

        // Set author and title for the book
        meta.setAuthor("MollyEssentials");
        meta.setTitle("Color Codes");

        // Set the book's meta data
        book.setItemMeta(meta);

        // Open the book for the player
        player.openBook(book);
    }

    private List<String> generateColorCodeList() {
        List<String> pages = new ArrayList<>();
        StringBuilder pageContent = new StringBuilder();

        String[][] colorCodesAndNames = {
                {"&0", "BLACK"}, {"&1", "DARK_BLUE"}, {"&2", "DARK_GREEN"}, {"&3", "DARK_AQUA"},
                {"&4", "DARK_RED"}, {"&5", "DARK_PURPLE"}, {"&6", "GOLD"}, {"&7", "GRAY"},
                {"&8", "DARK_GRAY"}, {"&9", "BLUE"}, {"&a", "GREEN"}, {"&b", "AQUA"},
                {"&c", "RED"}, {"&d", "LIGHT_PURPLE"}, {"&e", "YELLOW"}, {"&f", "WHITE"}
        };

        String[][] formatCodesAndNames = {
                {"&k", "MAGIC"}, {"&l", "BOLD"}, {"&m", "STRIKETHROUGH"}, {"&n", "UNDERLINE"},
                {"&o", "ITALIC"}, {"&r", "RESET"}
        };

        for (String[] pair : colorCodesAndNames) {
            ChatColor color = ChatColor.valueOf(pair[1]);
            pageContent.append(color + pair[0] + " - " + pair[1] + "\n");

            // Check if a page is getting too long and should be split
            if (pageContent.length() > 200) {
                pages.add(pageContent.toString());
                pageContent = new StringBuilder();
            }
        }

        // Add formats in a style that represents what they are
        for (String[] pair : formatCodesAndNames) {
            ChatColor format = ChatColor.valueOf(pair[1]);

            switch(pair[1]) {
                case "MAGIC":
                    pageContent.append(ChatColor.BLACK + pair[0] + " - " + ChatColor.MAGIC + "MAGIC" + ChatColor.BLACK + "\n");
                    break;
                case "BOLD":
                    pageContent.append(ChatColor.BLACK + pair[0] + " - " + ChatColor.BOLD + "BOLD" + "\n");
                    break;
                case "STRIKETHROUGH":
                    pageContent.append(ChatColor.BLACK + pair[0] + " - " + ChatColor.STRIKETHROUGH + "STRIKETHROUGH" + "\n");
                    break;
                case "UNDERLINE":
                    pageContent.append(ChatColor.BLACK + pair[0] + " - " + ChatColor.UNDERLINE + "UNDERLINE" + "\n");
                    break;
                case "ITALIC":
                    pageContent.append(ChatColor.BLACK + pair[0] + " - " + ChatColor.ITALIC + "ITALIC" + "\n");
                    break;
                default:
                    pageContent.append(ChatColor.BLACK + pair[0] + " - " + pair[1] + "\n");
            }

            // Check if a page is getting too long and should be split
            if (pageContent.length() > 200) {
                pages.add(pageContent.toString());
                pageContent = new StringBuilder();
            }
        }

        // Add any remaining content to the pages
        if (pageContent.length() > 0) {
            pages.add(pageContent.toString());
        }

        return pages;
    }


}
