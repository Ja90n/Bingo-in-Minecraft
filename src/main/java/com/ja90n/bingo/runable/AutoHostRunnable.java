package com.ja90n.bingo.runable;

import com.ja90n.bingo.Bingo;
import com.ja90n.bingo.enums.GameState;
import com.ja90n.bingo.instance.Game;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AutoHostRunnable extends BukkitRunnable {

    private final Game game;
    private final Bingo bingo;
    private final int secondsBetweenCallingNumbers;

    public AutoHostRunnable(Bingo bingo,int secondsBetweenCallingNumbers){
        game = bingo.getGame();
        this.bingo = bingo;
        this.secondsBetweenCallingNumbers = secondsBetweenCallingNumbers;
    }

    public void start(){
        runTaskTimer(bingo,0, 20L * secondsBetweenCallingNumbers);
    }

    @Override
    public void run() {
        List<Integer> list = new ArrayList<>();
        for (int number : bingo.getGame().getNumbers().keySet()) {
            if (!bingo.getGame().getNumbers().get(number)) {
                list.add(number);
            }
        }

        if (list.isEmpty()) {
            cancel();
        } else {
            int random = list.get(randomNumber(0,list.size()));
            bingo.getGame().callNumber(random);
        }
    }


    public int randomNumber(int up, int down){
        Random r = new Random();
        int low = up;
        int high = down;
        int result = r.nextInt(high-low) + low;
        return result;
    }
}
