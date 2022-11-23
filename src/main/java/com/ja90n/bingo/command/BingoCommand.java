package com.ja90n.bingo.command;

import com.ja90n.bingo.Bingo;
import com.ja90n.bingo.enums.GameState;
import com.ja90n.bingo.gui.MainMenuGui;
import com.ja90n.bingo.instance.Game;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BingoCommand implements CommandExecutor {

    private Bingo bingo;
    private Game game;

    public BingoCommand(Bingo bingo){
        this.bingo = bingo;
        game = bingo.getGame();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command");
            return false;
        } else {
            Player player = (Player) sender;
            if (bingo.getGame().getGameState().equals(GameState.LINE) || bingo.getGame().getGameState().equals(GameState.FULL)){
                if (bingo.getGame().getPlayers().containsKey(player.getUniqueId())){
                    if (bingo.getGame().getCard(player.getUniqueId()) != null){
                        bingo.getGame().getCard(player.getUniqueId()).openCard();
                        return false;
                    }
                }
            }
            new MainMenuGui(player.getUniqueId(),bingo);
        }
        return false;
    }
}
