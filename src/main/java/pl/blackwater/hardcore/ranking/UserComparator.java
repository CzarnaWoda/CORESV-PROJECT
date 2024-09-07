package pl.blackwater.hardcore.ranking;

import pl.blackwater.hardcore.data.User;

import java.util.Comparator;

public class UserComparator {

    static class UserByKillsComparator implements Comparator<User> {
        @Override
        public int compare(User g0, User g1) {
            final Integer user1 = g0.getKills();
            final Integer user2 = g1.getKills();
            return user2.compareTo(user1);
        }
    }
    static class UserByCoinsComparator implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            final Integer user1 = o1.getCoins();
            final Integer user2 = o2.getCoins();
            return user2.compareTo(user1);
        }
    }
    static class UserByDeathsComparator implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            final Integer user1 = o1.getDeaths();
            final Integer user2 = o2.getDeaths();
            return user2.compareTo(user1);
        }
    }
    static class UserByEatKoxComparator implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            final Integer user1 = o1.getEatedKox();
            final Integer user2 = o2.getEatedKox();
            return user2.compareTo(user1);
        }
    }
    static class UserByEatRefComparator implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            final Integer user1 = o1.getEatedRef();
            final Integer user2 = o2.getEatedRef();
            return user2.compareTo(user1);
        }
    }
    static class UserByLevelComparator implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            final Integer user1 = o1.getLevel();
            final Integer user2 = o2.getLevel();
            return user2.compareTo(user1);
        }
    }
    static class UserByStoneBreakComparator implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            final Integer user1 = o1.getDropStones();
            final Integer user2 = o2.getDropStones();
            return user2.compareTo(user1);
        }
    }
    static class UserByTimePlayComparator implements Comparator<User> {
        @Override
        public int compare(User o1, User o2) {
            final Integer user1 = o1.getTimePlay();
            final Integer user2 = o2.getTimePlay();
            return user2.compareTo(user1);
        }
    }
}
