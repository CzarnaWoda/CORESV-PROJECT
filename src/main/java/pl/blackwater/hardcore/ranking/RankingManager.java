package pl.blackwater.hardcore.ranking;

import lombok.Getter;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.guilds.data.Guild;

import java.util.ArrayList;
import java.util.List;

public class RankingManager
{
    @Getter
    private static List<User> rankings = new ArrayList<>();
    @Getter
    public static List<User> rankingsByCoins = new ArrayList<>();
    @Getter
    public static List<User> rankingsByDeaths = new ArrayList<>();
    @Getter
    public static List<User> rankingsByEatKox = new ArrayList<>();
    @Getter
    public static List<User> rankingsByEatRef = new ArrayList<>();
    @Getter
    public static List<User> rankingsByLevel = new ArrayList<>();
    @Getter
    public static List<User> rankingsByStoneBreak = new ArrayList<>();
    @Getter
    public static List<User> rankingsByTimePlay = new ArrayList<>();
    @Getter
    private static List<Guild> guildRankings = new ArrayList<>();

    public static void addRanking(User ranking)
    {
        rankings.add(ranking);
        rankingsByCoins.add(ranking);
        rankingsByDeaths.add(ranking);
        rankingsByEatKox.add(ranking);
        rankingsByEatRef.add(ranking);
        rankingsByLevel.add(ranking);
        rankingsByStoneBreak.add(ranking);
        rankingsByTimePlay.add(ranking);
        sortUserRankings();
    }

    public static void addRanking(Guild ranking)
    {
        guildRankings.add(ranking);
        sortGuildRankings();
    }

    public static void removeRanking(User ranking)
    {
        rankings.remove(ranking);
        rankingsByCoins.remove(ranking);
        rankingsByDeaths.remove(ranking);
        rankingsByEatKox.remove(ranking);
        rankingsByEatRef.remove(ranking);
        rankingsByLevel.remove(ranking);
        rankingsByStoneBreak.remove(ranking);
        rankingsByTimePlay.remove(ranking);
        sortUserRankings();
    }

    public static void removeRanking(Guild ranking)
    {
        guildRankings.remove(ranking);
        sortGuildRankings();
    }

    public static void sortUserRankings()
    {
        rankings.sort(new UserComparator.UserByKillsComparator());
        rankingsByCoins.sort(new UserComparator.UserByCoinsComparator());
        rankingsByDeaths.sort(new UserComparator.UserByDeathsComparator());
        rankingsByEatKox.sort(new UserComparator.UserByEatKoxComparator());
        rankingsByEatRef.sort(new UserComparator.UserByEatRefComparator());
        rankingsByLevel.sort(new UserComparator.UserByLevelComparator());
        rankingsByStoneBreak.sort(new UserComparator.UserByStoneBreakComparator());
        rankingsByTimePlay.sort(new UserComparator.UserByTimePlayComparator());
    }

    public static void sortGuildRankings()
    {
        guildRankings.sort(new GuildComparator());
    }

    public static int getPlaceByKillsUser(User user)
    {
        for (int num = 0; num < rankings.size(); num++) {
            if (rankings.get(num).equals(user)) {
                return num + 1;
            }
        }
        return 0;
    }

    public static int getPlaceByCoins(User user)
    {
        for (int num = 0; num < rankingsByCoins.size(); num++) {
            if (rankingsByCoins.get(num).equals(user)) {
                return num + 1;
            }
        }
        return 0;
    }
    public static int getPlaceByDeaths(User user)
    {
        for (int num = 0; num < rankingsByDeaths.size(); num++) {
            if (rankingsByDeaths.get(num).equals(user)) {
                return num + 1;
            }
        }
        return 0;
    }
    public static int getPlaceByEatKox(User user)
    {
        for (int num = 0; num < rankingsByEatKox.size(); num++) {
        if (rankingsByEatKox.get(num).equals(user)) {
            return num + 1;
        }
    }
        return 0;
    }
    public static int getPlaceByEatRef(User user)
    {
        for (int num = 0; num < rankingsByEatRef.size(); num++) {
        if (rankingsByEatRef.get(num).equals(user)) {
            return num + 1;
        }
    }
        return 0;
    }
    public static int getPlaceByLevel(User user)
    {
        for (int num = 0; num < rankingsByLevel.size(); num++) {
        if (rankingsByLevel.get(num).equals(user)) {
            return num + 1;
        }
    }
        return 0;
    }
    public static int getPlaceByStoneBreak(User user)
    {
        for (int num = 0; num < rankingsByStoneBreak.size(); num++) {
        if (rankingsByStoneBreak.get(num).equals(user)) {
            return num + 1;
        }
    }
        return 0;
    }
    public static int getPlaceByTimePlay(User user)
    {
        for (int num = 0; num < rankingsByTimePlay.size(); num++) {
        if (rankingsByTimePlay.get(num).equals(user)) {
            return num + 1;
        }
    }
        return 0;
    }
    public static int getPlaceGuild(Guild guild)
    {
        for (int num = 0; num < rankings.size(); num++) {
            if (guildRankings.get(num).equals(guild)) {
                return num + 1;
            }
        }
        return 0;
    }
}
