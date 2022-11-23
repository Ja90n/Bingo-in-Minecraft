package com.ja90n.bingo.instance;

import com.ja90n.bingo.Bingo;
import com.ja90n.bingo.ConfigManager;
import com.ja90n.bingo.enums.CustomSkull;
import com.ja90n.bingo.enums.GameState;
import com.ja90n.bingo.util.CustomSkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.C;

import java.util.*;

public class Card {

    private UUID uuid;
    private Game game;
    private Bingo bingo;
    private Inventory inventory;
    private ConfigManager configManager;
/*
Like Map<Index, Number>
 */
    private Map<Integer,Integer> index;
    private Map<Integer, Boolean> numbersClicked;

    public Card (UUID uuid, Game game, Bingo bingo){
        this.uuid = uuid;
        this.game = game;
        this.bingo = bingo;
        index = new HashMap<>();
        numbersClicked = new HashMap<>();
        configManager = bingo.getConfigManager();
        inventory = Bukkit.createInventory(Bukkit.getPlayer(uuid), 54, configManager.getChatColor() + configManager.getMessage("bingo-card"));
    }

    public void openCard(){
        Bukkit.getPlayer(uuid).openInventory(inventory);
    }

    public void generateCard(){
        for (int i = 0; i <= 24; i++){
            numbersClicked.put(i,false);
            if (i == 0 || i == 5 | i == 10 || i == 15 || i == 20){
                boolean check = true;
                while (check){
                    int random = randomNumber(1,15);
                    if (!index.containsValue(random)){
                        index.put(i,random);
                        check = false;
                    }
                }
            } else if (i == 1 || i == 6 | i == 11 || i == 16 || i == 21){
                boolean check = true;
                while (check){
                    int random = randomNumber(16,30);
                    if (!index.containsValue(random)){
                        index.put(i,random);
                        check = false;
                    }
                }
            } else if (i == 2 || i == 7 | i == 12 || i == 17 || i == 22){
                boolean check = true;
                while (check){
                    int random = randomNumber(31,45);
                    if (!index.containsValue(random)){
                        index.put(i,random);
                        check = false;
                    }
                }
            } else if (i == 3 || i == 8 | i == 13 || i == 18 || i == 23){
                boolean check = true;
                while (check){
                    int random = randomNumber(46,60);
                    if (!index.containsValue(random)){
                        index.put(i,random);
                        check = false;
                    }
                }
            } else if (i == 4 || i == 9 || i == 14 || i == 19|| i == 24){
                boolean check = true;
                while (check){
                    int random = randomNumber(61,75);
                    if (!index.containsValue(random)){
                        index.put(i,random);
                        check = false;
                    }
                }
            }
        }

        // Creation of the inventory

        // Bingo numbers
        int timesRun = 0;
        for (int i : new int[]{11,12,13,14,15,20,21,22,23,24,29,30,31,32,33,38,39,40,41,42,47,48,49,50,51}) {
            ItemStack paper = new ItemStack(Material.PAPER);
            ItemMeta itemMeta = paper.getItemMeta();
            String letter = "C-";
            int num = index.get(timesRun);
            if (num <= 15){
                letter = "B-";
            }
            if (num <= 30 && num >= 16){
                letter = "I-";
            }
            if (num <= 45 && num >= 31){
                letter = "N-";
            }
            if (num <= 60 && num >= 46){
                letter = "G-";
            }
            if (num <= 75 && num >= 61){
                letter = "O-";
            }
            String number = String.valueOf(index.get(timesRun));
            itemMeta.setDisplayName(letter + number);
            paper.setItemMeta(itemMeta);
            inventory.setItem(i,paper);

            timesRun++;
        }

        // Frame
        ItemStack frame = configManager.getFrame();
        for (int i : new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10,16,17,18,25,26,27,28,34,35,36,37,43,44,45,46,52,53}) {
            inventory.setItem(i, frame);
        }

        // Back button
        ItemStack backButton = new ItemStack(Material.ARROW);
        ItemMeta backButtonMeta = backButton.getItemMeta();
        backButtonMeta.setDisplayName(ChatColor.WHITE + configManager.getMessage("back-to-main-menu-button"));
        backButton.setItemMeta(backButtonMeta);
        inventory.setItem(0,backButton);

        // text BINGO above the card
        CustomSkullCreator customSkullCreator = new CustomSkullCreator();
        inventory.setItem(2,customSkullCreator.getSkull(CustomSkull.B_BLACK,configManager.getChatColor() + "B"));
        inventory.setItem(3,customSkullCreator.getSkull(CustomSkull.I_BLACK,configManager.getChatColor() + "I"));
        inventory.setItem(4,customSkullCreator.getSkull(CustomSkull.N_BLACK,configManager.getChatColor() + "N"));
        inventory.setItem(5,customSkullCreator.getSkull(CustomSkull.G_BLACK,configManager.getChatColor() + "G"));
        inventory.setItem(6,customSkullCreator.getSkull(CustomSkull.O_BLACK,configManager.getChatColor() + "O"));

        // BINGO button
        inventory.setItem(19,customSkullCreator.getSkull(CustomSkull.BUTTON,configManager.getChatColor() + "BINGO"));
    }

    public boolean bingoCall(){
        if (game.getGameState().equals(GameState.LINE)){
            if (numbersClicked.get(0) && numbersClicked.get(1)&& numbersClicked.get(2)&& numbersClicked.get(3)&&numbersClicked.get(4)){
                return true;
            } else if (numbersClicked.get(5) && numbersClicked.get(6)&& numbersClicked.get(7)&& numbersClicked.get(8)&&numbersClicked.get(9)){
                return true;
            } else if (numbersClicked.get(10) && numbersClicked.get(11)&& numbersClicked.get(12)&& numbersClicked.get(13)&&numbersClicked.get(14)){
                return true;
            } else if (numbersClicked.get(15) && numbersClicked.get(16)&& numbersClicked.get(17)&& numbersClicked.get(18)&&numbersClicked.get(19)){
                return true;
            } else if (numbersClicked.get(20) && numbersClicked.get(21)&& numbersClicked.get(22)&& numbersClicked.get(23)&&numbersClicked.get(24)){
                return true;
            } else if (numbersClicked.get(0) && numbersClicked.get(5)&& numbersClicked.get(10)&& numbersClicked.get(15)&&numbersClicked.get(20)){
                return true;
            } else if (numbersClicked.get(1) && numbersClicked.get(6)&& numbersClicked.get(11)&& numbersClicked.get(16)&&numbersClicked.get(21)){
                return true;
            } else if (numbersClicked.get(2) && numbersClicked.get(7)&& numbersClicked.get(12)&& numbersClicked.get(17)&&numbersClicked.get(22)){
                return true;
            } else if (numbersClicked.get(3) && numbersClicked.get(8)&& numbersClicked.get(13)&& numbersClicked.get(18)&&numbersClicked.get(23)){
                return true;
            } else if (numbersClicked.get(4) && numbersClicked.get(9)&& numbersClicked.get(14)&& numbersClicked.get(19)&&numbersClicked.get(24)){
                return true;
            } else if (numbersClicked.get(0) && numbersClicked.get(6)&& numbersClicked.get(12)&& numbersClicked.get(18)&&numbersClicked.get(24)) {
                return true;
            } else if (numbersClicked.get(4) && numbersClicked.get(8)&& numbersClicked.get(12)&& numbersClicked.get(16)&&numbersClicked.get(20)){
                return true;
            } else {
                return false;
            }
        } else if (game.getGameState().equals(GameState.FULL)){
            for (int i = 0; i <= 24; i++){
                if (!numbersClicked.get(i)){
                    return false;
                } else if (i==24){
                    return true;
                }
            }
        }
        return false;
    }

    public int randomNumber(int up, int down){
        Random r = new Random();
        int low = up;
        int high = down + 1;
        int result = r.nextInt(high-low) + low;
        return result;
    }

    public int numberToIndex(int number){
        for (int i : index.keySet()){
            if (index.get(i) == number){
                return i;
            }
        }
        return -1;
    }

    public Inventory getInventory () {
        return inventory;
    }

    public Map<Integer,Boolean> getNumbersClicked () {return numbersClicked; }
}
