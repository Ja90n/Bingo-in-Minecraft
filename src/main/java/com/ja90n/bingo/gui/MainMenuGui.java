package com.ja90n.bingo.gui;

import com.ja90n.bingo.Bingo;
import com.ja90n.bingo.ConfigManager;
import com.ja90n.bingo.enums.CustomSkull;
import com.ja90n.bingo.enums.GameState;
import com.ja90n.bingo.instance.Game;
import com.ja90n.bingo.util.CustomSkullCreator;
import com.ja90n.bingo.util.ItemStackGenerator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainMenuGui {

    private final Player player;
    private final Game game;
    private final ConfigManager configManager;
    private final Inventory menu;
    private final CustomSkullCreator customSkullCreator;
    private final UUID uuid;
    private final ItemStackGenerator itemStackGenerator;

    public MainMenuGui(UUID uuid, Bingo bingo) {
        player = Bukkit.getPlayer(uuid);
        this.uuid = uuid;
        configManager = bingo.getConfigManager();

        game = bingo.getGame();
        customSkullCreator = new CustomSkullCreator();
        itemStackGenerator = new ItemStackGenerator(bingo);
        menu = Bukkit.createInventory(player, 45, configManager.getChatColor() + configManager.getMessage("main-menu"));

        if (player == null){
            return;
        }

        itemStackGenerator.createFrame(menu);
        createButtons();

        player.openInventory(menu);
    }

    private void createButtons() {
        createStatusIcon();
        createPortalButton();
        createHostButton();
    }

    private void createStatusIcon(){
        //Status
        if (game.getGameState().equals(GameState.OFF)) {
            // Off status
            menu.setItem(20, itemStackGenerator.getItemStack(Material.RED_CONCRETE,
                    ChatColor.WHITE + configManager.getMessage("status") + ChatColor.RED + configManager.getMessage("inactive-status")));
        } else {
            ItemStack statusOn = new ItemStack(Material.GREEN_CONCRETE);
            ItemMeta statusOnMeta = statusOn.getItemMeta();
            if (game.getGameState().equals(GameState.RECRUITING)) {
                statusOnMeta.setDisplayName(ChatColor.WHITE + configManager.getMessage("status") + ChatColor.GREEN + configManager.getMessage("recruiting-status"));
            } else if (game.getGameState().equals(GameState.LINE)) {
                statusOnMeta.setDisplayName(ChatColor.WHITE + configManager.getMessage("status") + ChatColor.RED + configManager.getMessage("line-status"));
            } else {
                statusOnMeta.setDisplayName(ChatColor.WHITE + configManager.getMessage("status") + ChatColor.RED + configManager.getMessage("full-status"));
            }
            statusOn.setItemMeta(statusOnMeta);
            menu.setItem(20, statusOn);
        }
    }

    private void createPortalButton() {
        if (game.getPlayers().containsKey(uuid)) {
            if (game.getGameState().equals(GameState.OFF)) {
                // Broken button
                menu.setItem(24, getItemStack(Material.CHAIN,ChatColor.RED + "ERROR"));
            } else if (game.getGameState().equals(GameState.RECRUITING)) {
                // Leave button
                if (!game.getPlayers().isEmpty()) {
                    menu.setItem(24, getItemStack(Material.BARRIER,ChatColor.RED + configManager.getMessage("leave-button"),getPlayerList()));
                }
                menu.setItem(24, getItemStack(Material.BARRIER,ChatColor.RED + configManager.getMessage("leave-button")));
            } else {
                // Bingo card button
                menu.setItem(24, getItemStack(Material.PAPER,
                        configManager.getChatColor() + configManager.getMessage("bingo-card")));
            }
        } else {
            // Join button
            if (game.getGameState().equals(GameState.OFF)) {
                // Game is off
                menu.setItem(24, getItemStack(Material.BARRIER,
                        ChatColor.WHITE + configManager.getMessage("status") + ChatColor.RED + configManager.getMessage("inactive-status")));
            } else if (game.getGameState().equals(GameState.RECRUITING)) {
                // Game is recruiting
                menu.setItem(24, getItemStack(Material.PAPER,
                        ChatColor.GREEN + configManager.getMessage("join-button"),getPlayerList()));
            } else {
                // Game is already active
                menu.setItem(24, getItemStack(Material.MAP,
                        ChatColor.GREEN + configManager.getMessage("game-active-button"),getPlayerList()));
            }

        }
    }

    private void createHostButton() {
        // Host button
        if (player.hasPermission("bingo.host")) {
            if (configManager.getIsAutoHosted()){
                // Game is auto hosted
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.RED + "Auto host is enabled");
                menu.setItem(22, getItemStack
                        (Material.BARRIER,ChatColor.RED + configManager.getMessage("host-menu"),lore));
            } else {
                // Host menu is accessible
                menu.setItem(22, customSkullCreator.getSkull(CustomSkull.HOST_MENU,
                        configManager.getChatColor() + configManager.getMessage("host-menu")));
            }
        }
    }

    public List<String> getPlayerList(){
        List<String> lore = new ArrayList<>();
        lore.add(configManager.getChatColor() + configManager.getMessage("current-players"));
        for (UUID target : game.getPlayers().keySet()) {
            lore.add(ChatColor.WHITE + Bukkit.getPlayer(target).getDisplayName());
        }
        return lore;
    }

    private ItemStack getItemStack(Material material, String name, List<String> lore){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        if (lore != null){
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack getItemStack(Material material, String name){
        return getItemStack(material,name,null);
    }


}