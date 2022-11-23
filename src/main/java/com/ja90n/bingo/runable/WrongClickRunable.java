package com.ja90n.bingo.runable;

import com.ja90n.bingo.Bingo;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class WrongClickRunable extends BukkitRunnable {

    private Inventory inventory;
    private int slot;
    private String name;
    private String letter;

    public WrongClickRunable(Inventory inventory, int slot, String name, Bingo bingo){
        this.inventory = inventory;
        this.slot = slot;
        this.name = name;
        int number = Integer.parseInt(name);
        letter = null;
        if (number <= 15){
            letter = "B-";
        }
        if (number <= 30 && number >= 16){
            letter = "I-";
        }
        if (number <= 45 && number >= 31){
            letter = "N-";
        }
        if (number <= 60 && number >= 46){
            letter = "G-";
        }
        if (number <= 75 && number >= 61){
            letter = "O-";
        }
        runTaskTimer(bingo,0,10);
    }

    int timesRun = 0;
    @Override
    public void run() {
        try {
            if (timesRun == 0){
                ItemStack itemStack = new ItemStack(Material.RED_CONCRETE);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(letter + name);
                itemStack.setItemMeta(itemMeta);

                inventory.setItem(slot,itemStack);
            } else {
                ItemStack itemStack = new ItemStack(Material.PAPER);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(letter + name);
                itemStack.setItemMeta(itemMeta);

                inventory.setItem(slot,itemStack);
                cancel();
            }
            timesRun++;
        } catch (Exception e){
            cancel();
        }
    }
}
