package com.ja90n.bingo.gui;

import com.ja90n.bingo.Bingo;
import com.ja90n.bingo.ConfigManager;
import com.ja90n.bingo.enums.GameState;
import com.ja90n.bingo.instance.Game;
import com.ja90n.bingo.util.ItemStackGenerator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sun.jvm.hotspot.opto.CallJavaNode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HostMenuGui {

    private Player player;
    private Game game;
    private ConfigManager configManager;
    private final Inventory menu;
    private final ItemStackGenerator itemStackGenerator;

    public HostMenuGui(UUID uuid, Bingo bingo) {
        configManager = bingo.getConfigManager();
        player = Bukkit.getPlayer(uuid);
        game = bingo.getGame();

        itemStackGenerator = new ItemStackGenerator(bingo);

        menu = Bukkit.createInventory(player,45, configManager.getChatColor() + configManager.getMessage("host-menu"));

        if (!bingo.getConfigManager().getIsAutoHosted()){
            itemStackGenerator.createFrame(menu);
            createButtons();

            player.openInventory(menu);
        } else {
            player.sendMessage(ChatColor.RED + "You can't open the host menu, auto hosting is enabled");
        }
    }

    private void createButtons(){
        createStartStopButton();
        createNextButton();
        createBackButton();
    }

    private void createBackButton(){
        // Back button
        ItemStack backButton = new ItemStack(Material.ARROW);
        ItemMeta backButtonMeta = backButton.getItemMeta();
        backButtonMeta.setDisplayName(ChatColor.WHITE + configManager.getMessage("back-to-main-menu-button"));
        backButton.setItemMeta(backButtonMeta);
        menu.setItem(0,backButton);
    }

    private void createNextButton(){
        // Next number
        ItemStack number = new ItemStack(Material.PAPER);
        ItemMeta numberMeta = number.getItemMeta();
        numberMeta.setDisplayName(ChatColor.WHITE + configManager.getMessage("next-number-button"));
        number.setItemMeta(numberMeta);
        menu.setItem(24,number);
    }

    private void createStartStopButton(){
        // Start / stop button
        if (game.getGameState().equals(GameState.OFF)){
            // Off button

            // Creating lore
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.WHITE + configManager.getMessage("status") + ChatColor.RED + configManager.getMessage("inactive-status"));

            menu.setItem(20,itemStackGenerator.getItemStack(Material.RED_CONCRETE,ChatColor.GREEN + configManager.getMessage("activate-game-button"),lore));
        } else if (game.getGameState().equals(GameState.RECRUITING)){
            // Start button
            if (!game.getPlayers().isEmpty()){
                menu.setItem(20,itemStackGenerator.getItemStack(Material.GREEN_CONCRETE,ChatColor.GREEN + configManager.getMessage("start-game-button"),itemStackGenerator.getPlayerList()));
            } else {
                menu.setItem(20,itemStackGenerator.getItemStack(Material.GREEN_CONCRETE,ChatColor.GREEN + configManager.getMessage("start-game-button")));
            }
        } else {
            // Force stop game button

            // Setting lore
            List<String> lore = new ArrayList<>();
            if (game.getGameState().equals(GameState.LINE)){
                lore.add(ChatColor.WHITE + configManager.getMessage("status") + ChatColor.GREEN + configManager.getMessage("line-status"));
            } else {
                lore.add(ChatColor.WHITE + configManager.getMessage("status") + ChatColor.GREEN + configManager.getMessage("full-status"));
            }
            if (!game.getPlayers().isEmpty()){
                lore.add(configManager.getChatColor() + configManager.getMessage("current-players"));
                for (UUID target : game.getPlayers().keySet()){
                    lore.add(ChatColor.WHITE + Bukkit.getPlayer(target).getDisplayName());
                }
            }

            menu.setItem(20,itemStackGenerator.getItemStack(Material.BARRIER,ChatColor.RED + configManager.getMessage("force-stop-game-button"),lore));
        }
    }
}