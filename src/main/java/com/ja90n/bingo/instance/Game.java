package com.ja90n.bingo.instance;

import com.ja90n.bingo.Bingo;
import com.ja90n.bingo.ConfigManager;
import com.ja90n.bingo.enums.GameState;
import com.ja90n.bingo.runable.AutoHostRunnable;
import com.ja90n.bingo.runable.CountDownRunnable;
import com.ja90n.bingo.util.UpdateJoinLeaveButton;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Game {

    private GameState state;
    private final Bingo bingo;
    private final HashMap <UUID,Boolean> players;
    private final HashMap <UUID,Card> cards;
    private final HashMap <Integer,Boolean> numbers;
    private final ConfigManager configManager;
    private AutoHostRunnable autoHostRunnable;
    private CountDownRunnable countDownRunnable;


    public Game(Bingo bingo){
        this.bingo = bingo;
        countDownRunnable = new CountDownRunnable(bingo);
        autoHostRunnable = new AutoHostRunnable(bingo,bingo.getConfigManager().getSecondsBetweenNumbersCalled());
        players = new HashMap<>();
        cards = new HashMap<>();
        numbers = new HashMap<>();
        configManager = bingo.getConfigManager();
        if (configManager.getIsAutoHosted()){
            this.state = GameState.RECRUITING;
        } else {
            this.state = GameState.OFF;
        }
    }

    public void startGame (){
        for (int i = 75; i >= 1; i--){
            numbers.put(i,false);
        }
        for (UUID playerUUID : players.keySet()){
            Card card = new Card(playerUUID,this,bingo);
            card.generateCard();
            cards.put(playerUUID,card);
            Bukkit.getPlayer(playerUUID).sendMessage(configManager.getChatColor() + configManager.getMessage("bingo-start-message"));
            card.openCard();
        }
        if (configManager.getIsAutoHosted()){
            autoHostRunnable.start();
        }
        state = GameState.LINE;
    }

    public void stopGame() {
        if (configManager.getIsAutoHosted()){
            autoHostRunnable.cancel();
            autoHostRunnable = new AutoHostRunnable(bingo, configManager.getSecondsBetweenNumbersCalled());
        }
        for (UUID playerUUID : players.keySet()){
            Bukkit.getPlayer(playerUUID).closeInventory();
            cards.remove(playerUUID);
        }
        cards.clear();
        numbers.clear();
        players.clear();
        if (configManager.getIsAutoHosted()){
            state = GameState.RECRUITING;
        } else {
            state = GameState.OFF;
        }
    }

    public void callNumber (int number){
        numbers.remove(number);
        numbers.put(number,true);
        for (UUID uuid : players.keySet()){
            ItemStack itemStack = new ItemStack(Material.MAP);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(configManager.getChatColor() + configManager.getMessage("called-number") +ChatColor.WHITE  + number + configManager.getChatColor() +"!");
            itemStack.setItemMeta(itemMeta);

            getCard(uuid).getInventory().setItem(28,itemStack);
            Bukkit.getPlayer(uuid).sendMessage(configManager.getChatColor() + configManager.getMessage("called-number") +ChatColor.WHITE  + number + configManager.getChatColor() +"!");
        }
    }

    public void sendMessage(String message){
        for (UUID uuid : players.keySet()){
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void sendActionBarMessage(String message){
        for (UUID uuid : players.keySet()){
            Bukkit.getPlayer(uuid).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        }
    }

    public Map<Integer, Boolean> getNumbers(){
        return numbers;
    }

    public Map<UUID,Card> getCards(){
        return cards;
    }

    public Card getCard (UUID uuid){
        return cards.getOrDefault(uuid, null);
    }

    public void addPlayer(UUID uuid){
        players.put(uuid,false);
        for (Player player : Bukkit.getOnlinePlayers()){
            if (player.getOpenInventory().getTitle().equals(configManager.getChatColor() + configManager.getMessage("main-menu"))){
                new  UpdateJoinLeaveButton(player.getOpenInventory(),player.getUniqueId(),this,bingo);
            }
        }
        Bukkit.getPlayer(uuid).sendMessage(configManager.getChatColor() + configManager.getMessage("join-message"));
        if (state == GameState.RECRUITING){
            if (configManager.getIsAutoHosted()){
                if (players.size() >= bingo.getConfigManager().getMinimumPlayers()){
                    if (!countDownRunnable.getIsStarted()){
                        countDownRunnable.start();
                    }
                }
            }
        } else {
            Card card = new Card(uuid,this,bingo);
            card.generateCard();
            cards.put(uuid,card);
            Bukkit.getPlayer(uuid).sendMessage(configManager.getChatColor() + configManager.getMessage("bingo-start-message"));
            card.openCard();
        }
    }

    public void removePlayer(UUID uuid){
        players.remove(uuid,false);
        for (Player player : Bukkit.getOnlinePlayers()){
            if (player.getOpenInventory().getTitle().equals(configManager.getChatColor() + configManager.getMessage("main-menu"))){
                new UpdateJoinLeaveButton(player.getOpenInventory(),player.getUniqueId(),this,bingo);
            }
        }
        Bukkit.getPlayer(uuid).sendMessage(ChatColor.RED + configManager.getMessage("leave-message"));
        if (state != GameState.RECRUITING){
            if (players.keySet().size() < 1){
                stopGame();
            }
        } else {
            if (configManager.getIsAutoHosted()){
                try {
                    countDownRunnable.cancel();
                } catch (Exception e){}
                sendActionBarMessage(ChatColor.RED + "Countdown canceled, too many players have left!");
                countDownRunnable = new CountDownRunnable(bingo);
            }
        }
    }

    public GameState getGameState () { return state; }
    public void setGameState (GameState gameState){
        state = gameState;
    }
    public HashMap<UUID,Boolean> getPlayers () { return players;}
}
