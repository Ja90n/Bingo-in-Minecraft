package com.ja90n.bingo.util;

import com.ja90n.bingo.enums.CustomSkull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomSkullCreator {

    public ItemStack getSkull(CustomSkull customSkull, String name){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        Bukkit.getUnsafe().modifyItemStack(skull, customSkull.getCustomData());
        return skull;
    }
}
