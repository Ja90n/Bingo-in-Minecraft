package com.ja90n.bingo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

public class ConfigManager {

    private YamlConfiguration yamlConfiguration;
    private ChatColor chatColor;
    private ItemStack itemStack;

    private boolean isAutoHosted;
    private int secondsBetweenNumbersCalled, countdownSeconds;
    private boolean canJoinDuringGame;
    private int minimumPlayers;
    private final Bingo bingo;

    public ConfigManager(Bingo bingo){
        this.bingo = bingo;
        initiate();
    }

    public void initiate() {
        getFile();
        setValuesFromConfig();
        setColour();
    }

    private void setValuesFromConfig() {
        countdownSeconds = bingo.getConfig().getInt("countDownLength");
        secondsBetweenNumbersCalled = bingo.getConfig().getInt("timeBetweenCalls");
        minimumPlayers = bingo.getConfig().getInt("minimumPlayers");
        isAutoHosted = bingo.getConfig().getBoolean("autohosting");
        canJoinDuringGame = bingo.getConfig().getBoolean("canJoinDuringGame");
    }

    private void getFile() {
        File file = new File(bingo.getDataFolder(), bingo.getConfig().getString("language") + ".yml");
        if (file.exists()){
            yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Language file does not exist so the Bingo plugin could not start!");
            bingo.getPluginLoader().disablePlugin(bingo);
        }
    }

    public void setColour() {
        switch (bingo.getConfig().getString("colour")){
            case "red":
                chatColor = ChatColor.RED;
                setItemStack(Material.RED_STAINED_GLASS_PANE);
                break;
            case "orange":
                chatColor = ChatColor.GOLD;
                setItemStack(Material.ORANGE_STAINED_GLASS_PANE);
                break;
            case "lime":
                chatColor = ChatColor.GREEN;
                setItemStack(Material.LIME_STAINED_GLASS_PANE);
                break;
            case "green":
                chatColor = ChatColor.DARK_GREEN;
                setItemStack(Material.GREEN_STAINED_GLASS_PANE);
                break;
            case "light_blue":
                chatColor = ChatColor.BLUE;
                setItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
                break;
            case "blue":
                chatColor = ChatColor.BLUE;
                setItemStack(Material.BLUE_STAINED_GLASS_PANE);
                break;
            case "pink":
                chatColor = ChatColor.LIGHT_PURPLE;
                setItemStack(Material.PINK_STAINED_GLASS_PANE);
                break;
            case "purple":
                chatColor = ChatColor.DARK_PURPLE;
                setItemStack(Material.PURPLE_STAINED_GLASS_PANE);
                break;
            case "black":
                chatColor = ChatColor.DARK_GRAY;
                setItemStack(Material.BLACK_STAINED_GLASS_PANE);
                break;
            case "grey":
            case "gray":
                chatColor = ChatColor.GRAY;
                setItemStack(Material.GRAY_STAINED_GLASS_PANE);
                break;
            default:
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Color does not exist so the Bingo plugin could not start!");
                bingo.getPluginLoader().disablePlugin(bingo);
        }
    }

    private void setItemStack(Material material) {
        itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(" ");
        itemStack.setItemMeta(itemMeta);
    }

    public int getSecondsBetweenNumbersCalled(){
        return secondsBetweenNumbersCalled;
    }

    public boolean getIsAutoHosted() {
        return isAutoHosted;
    }

    public boolean getCanJoinDuringGame() { return canJoinDuringGame; }

    public int getMinimumPlayers() {
        return minimumPlayers;
    }

    public String getMessage(String type){
        return yamlConfiguration.getString(type);
    }

    public int getCountdownSeconds() {
        return countdownSeconds;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public ItemStack getFrame() {
        return itemStack;
    }
}