package pl.blackwater.hardcore.data;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import pl.blackwater.hardcore.Core;
import pl.blackwaterapi.utils.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public class EasyScoreboard {
    private final Player p;
    public final HashMap<Integer, String> scores = new HashMap<>();
    final private List<String> list = Arrays.asList("1","2","3","4","5","6","7","8","9","a","b","c","e","f","m","n");
    
    public EasyScoreboard(final Player p, final String title, final String... values) {
        this.p = p;
        
        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        
        final Objective objective = scoreboard.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(title);

        ArrayUtils.reverse(values);

        for (String value : values) {
            objective.getScore(value).setScore(scores.size());
            scores.put(scores.size(), value);
        }

        p.setScoreboard(scoreboard);
        Core.getEasyScoreboardManager().getScoreboards().put(p.getName(), this);
        
    }

    public EasyScoreboard(final Player p, final String title){
        this.p = p;

        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        final Objective objective = scoreboard.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(Util.fixColor(Util.replaceString(title)));

        p.setScoreboard(scoreboard);
        Core.getEasyScoreboardManager().getScoreboards().put(p.getName(), this);
    }

    public void addLine(final String value) {
    	final int size = scores.size();
        scores.put(size, Util.fixColor(value));
        p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(scores.get(size)).setScore(size);
    }

    public void updateLine(final int slot,final String value) {
        if (scores.get(slot) == null) {
            return;
        }
        p.getScoreboard().resetScores(scores.get(slot));
        p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(Util.fixColor(value)).setScore(slot);
        scores.put(slot, Util.fixColor(Util.replaceString(value)));
    }

    public void addBlankLine(){
    	final int size = scores.size();
        scores.put(size, Util.fixColor("&" + list.get(size)));
        p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(scores.get(size)).setScore(size);
    }

    public void setBlanked(final int slot) {
        if (scores.get(slot) == null) {
            return;
        }

        p.getScoreboard().resetScores(scores.get(slot));
        scores.put(slot, Util.fixColor("&" +slot));
        p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(scores.get(slot)).setScore(slot);
    }

    public void setTitle(final String value) {
        p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(value);
    }


}