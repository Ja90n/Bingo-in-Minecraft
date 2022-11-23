package com.ja90n.bingo.util;

import com.ja90n.bingo.Bingo;
import com.ja90n.bingo.ConfigManager;
import com.ja90n.bingo.instance.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemStackGenerator {

    private final ConfigManager configManager;
    private final Bingo bingo;

    public ItemStackGenerator(Bingo bingo){
        configManager = bingo.getConfigManager();
        this.bingo = bingo;
    }

    public void createFrame(Inventory inventory){
        ItemStack frame = configManager.getFrame();
        ItemMeta frameMeta = frame.getItemMeta();
        frameMeta.setDisplayName(" ");
        frame.setItemMeta(frameMeta);
        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44}) {
            inventory.setItem(i, frame);
        }
    }

    public List<String> getPlayerList(){
        List<String> lore = new ArrayList<>();
        lore.add(configManager.getChatColor() + configManager.getMessage("current-players"));
        for (UUID target : bingo.getGame().getPlayers().keySet()) {
            lore.add(ChatColor.WHITE + Bukkit.getPlayer(target).getDisplayName());
        }
        return lore;
    }

    public ItemStack getItemStack(Material material, String name, List<String> lore){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        if (lore != null){
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack getItemStack(Material material, String name){
        return getItemStack(material,name,null);
    }

}
