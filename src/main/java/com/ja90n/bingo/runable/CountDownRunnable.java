package com.ja90n.bingo.runable;

import com.ja90n.bingo.Bingo;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class CountDownRunnable extends BukkitRunnable {

    private Bingo bingo;
    private int countdownSeconds;
    private boolean isStarted;

    public CountDownRunnable(Bingo bingo){
        this.bingo = bingo;
        isStarted = false;
        countdownSeconds = bingo.getConfigManager().getCountdownSeconds();
    }

    public void start(){
        isStarted = true;
        runTaskTimer(bingo,0,20);
    }

    @Override
    public void run() {
        if (countdownSeconds == 0){
            cancel();
            bingo.getGame().startGame();
            return;
        }

        if (countdownSeconds <= 10 || countdownSeconds % 15 == 0){
            bingo.getGame().sendActionBarMessage(ChatColor.BLUE + "Game will start in " + countdownSeconds + " second" + (countdownSeconds == 1 ? "" : "s") + ".");
        }

        countdownSeconds--;
    }
    public boolean getIsStarted(){
        return isStarted;
    }

}
