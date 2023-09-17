package org.noble.mollyessentials.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class RepairSignListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (event.getLine(0).equalsIgnoreCase("[repair]") && event.getPlayer().hasPermission("mollyessentials.repairsign.create")) {
            event.setLine(0, "§f[§6§lRepair§f]");
            event.getPlayer().sendMessage("§aRepair sign created!");
        }
    }

    @EventHandler
    public void onSignInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block block = event.getClickedBlock();
        if (block.getState() instanceof Sign) {
            Sign sign = (Sign) event.getClickedBlock().getState();

            if (sign.getLine(0).equalsIgnoreCase("§f[§6§lRepair§f]")) {
                event.setCancelled(true);  // Cancel the event to prevent editing the sign

                if (!event.getPlayer().hasPermission("mollyessentials.repairsign.use")) {
                    event.getPlayer().sendMessage("§cYou do not have permission to use this sign.");
                    return;
                }

                Bukkit.dispatchCommand(event.getPlayer(), "essentials:repair");
            }
        }
    }
}
