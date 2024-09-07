package pl.blackwater.hardcore.tablist;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TablistImpl extends AbstractTablist
{
    private static final Class<?> PLAYER_INFO_CLASS;
    private static final Class<?> PLAYER_LIST_HEADER_FOOTER_CLASS;
    private static final Class<?> PLAYER_INFO_DATA_CLASS;
    private static final Class<?> GAME_PROFILE_CLASS;
    private static final Class<?> ENUM_GAMEMODE_CLASS;
    private static final Class<?> BASE_COMPONENT_CLASS;
    private static final Field ACTION_ENUM_FIELD;
    private static final Field LIST_FIELD;
    private static final Field HEADER_FIELD;
    private static final Field FOOTER_FIELD;
    private static final Enum<?> ADD_PLAYER;
    private static final Enum<?> UPDATE_PLAYER;
    private static final String UUID_PATTERN = "00000000-0000-%s-0000-000000000000";
    private static final String TOKEN = "!@#$^*";
    private static Constructor<?> playerInfoDataConstructor;
    private static Constructor<?> gameProfileConstructor;
    private final Object[] profileCache;

    public TablistImpl(final Map<Integer, String> tablistPattern, final String header, final String footer, final int ping, final Player player) {
        super(tablistPattern, header, footer, ping, player);
        this.profileCache = new Object[80];
    }

    @Override
    public void send() {
        final List<Object> packets = Lists.newArrayList();
        final List<Object> addPlayerList = Lists.newArrayList();
        final List<Object> updatePlayerList = Lists.newArrayList();
        try {
            final Object addPlayerPacket = TablistImpl.PLAYER_INFO_CLASS.newInstance();
            final Object updatePlayerPacket = TablistImpl.PLAYER_INFO_CLASS.newInstance();
            for (int i = 0; i < 80; ++i) {
                if (this.profileCache[i] == null) {
                    this.profileCache[i] = TablistImpl.gameProfileConstructor.newInstance(UUID.fromString(String.format("00000000-0000-%s-0000-000000000000", StringUtils.appendDigit(i))), "!@#$^*" + StringUtils.appendDigit(i));
                }
                final String text = this.putVars(this.tablistPattern.getOrDefault(i + 1, ""));
                Object gameProfile = this.profileCache[i];
                ((GameProfile)gameProfile).getProperties().put("textures" , new Property("textures","eyJ0aW1lc3RhbXAiOjE1Nzg5NTg1ODQ5MDMsInByb2ZpbGVJZCI6ImU0MTNlZGNhOGVmODRkZTQ5N2U2ZWQyM2Y4NGJmMGE3IiwicHJvZmlsZU5hbWUiOiIwMTAwTUFUUklYMTEwMSIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDE3OGFlMDNkODQyMjdmMjFmYTIzNjgyZDljNjIwMTMxMDAwZmZiNzY3MzM5NTUzM2IyNzQwZmE4M2FiMTAzNyJ9fX0=", "peBo/XDYUG6emdiU2z6ooYNe+WpAHCZuzAh1ThOE78h2TZzwHirO5XTl++U/OaBWDwRImdkOt8Hz/68h+1SL3DayruTfS8bgj4dVI5wZ5wj1C5+0TijgSIY+z2cZVaOzzzn3XXXsTxRvMsi65iizVvDN410wuKkWtY676i3QW0xY7cxrpsE+P958UPb0H35Rv6jXu6VqjB9imUd4WxwYqL7SRJHzTvDnMqqxKSzmurItXWdmlHdyXLcBlffjXLrE3p07OS2lqD+1cSVSimqPRlVds1hmOOlK3z/5rrPtonfsfxdPnhNsSwb9MYzfIJ/kfpJsap/yzLUaMthSkil8oWnEZB7xXWUudk9m+CwVM9jRvPAYrxtZVM2N/vv2jGtEkVYCP2/CEf/tbMTWrMXDhD1DFXa1a636fJmqK0nGTP2FnJZKZbFTGxNeoLjOGJNIv4bcziMP5PffvxUXknMe5K6P8bagZ7aZXAjdP5rsbadpAalJ66+fxzoq+zExw7Br0vYoZ/bsj0WkP+coMFILeWzLDyzNZxq7PPZHsHgbWqgBZO+fKMGnO1i3hgoIbnC0FXbMZFl7R7lRAJGuk3Tgt4841lMaVQgfooVPdy1YdxoWJZLqFHaMj/5k5w38y5Enb/dXxfswwSWXu0ZdI5maDblqhLoMxK7LW+Xmed5n66Y="));
                final Object gameMode = TablistImpl.ENUM_GAMEMODE_CLASS.getEnumConstants()[1];
                final Object component = this.createBaseComponent(text, false);
                final Object playerInfoData = TablistImpl.playerInfoDataConstructor.newInstance(null, gameProfile, this.ping, gameMode, component);
                if (this.firstPacket) {
                    addPlayerList.add(playerInfoData);
                }
                updatePlayerList.add(playerInfoData);
            }
            if (this.firstPacket) {
                this.firstPacket = false;
            }
            packets.add(addPlayerPacket);
            packets.add(updatePlayerPacket);
            TablistImpl.ACTION_ENUM_FIELD.setAccessible(true);
            TablistImpl.LIST_FIELD.setAccessible(true);
            TablistImpl.HEADER_FIELD.setAccessible(true);
            TablistImpl.FOOTER_FIELD.setAccessible(true);
            TablistImpl.ACTION_ENUM_FIELD.set(addPlayerPacket, TablistImpl.ADD_PLAYER);
            TablistImpl.LIST_FIELD.set(addPlayerPacket, addPlayerList);
            TablistImpl.ACTION_ENUM_FIELD.set(updatePlayerPacket, TablistImpl.UPDATE_PLAYER);
            TablistImpl.LIST_FIELD.set(updatePlayerPacket, updatePlayerList);
            final Object header = this.createBaseComponent(this.putVars(super.header), true);
            final Object footer = this.createBaseComponent(this.putVars(super.footer), true);
            if (this.shouldUseHeaderAndFooter()) {
                final Object headerFooterPacket = TablistImpl.PLAYER_LIST_HEADER_FOOTER_CLASS.newInstance();
                TablistImpl.HEADER_FIELD.set(headerFooterPacket, header);
                TablistImpl.FOOTER_FIELD.set(headerFooterPacket, footer);
                packets.add(headerFooterPacket);
            }
            this.sendPackets(packets);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException ex3) {
            final ReflectiveOperationException ex2;
            final ReflectiveOperationException ex = ex3;
            ex.printStackTrace();
        }
    }

    static {
        PLAYER_INFO_CLASS = Reflections.getNMSClass("PacketPlayOutPlayerInfo");
        PLAYER_LIST_HEADER_FOOTER_CLASS = Reflections.getNMSClass("PacketPlayOutPlayerListHeaderFooter");
        PLAYER_INFO_DATA_CLASS = Reflections.getNMSClass("PacketPlayOutPlayerInfo$PlayerInfoData");
        GAME_PROFILE_CLASS = Reflections.getClass("com.mojang.authlib.GameProfile");
        ENUM_GAMEMODE_CLASS = Reflections.getNMSClass("WorldSettings$EnumGamemode");
        BASE_COMPONENT_CLASS = Reflections.getNMSClass("IChatBaseComponent");
        ACTION_ENUM_FIELD = Reflections.getField(TablistImpl.PLAYER_INFO_CLASS, "a");
        LIST_FIELD = Reflections.getField(TablistImpl.PLAYER_INFO_CLASS, "b");
        HEADER_FIELD = Reflections.getField(TablistImpl.PLAYER_LIST_HEADER_FOOTER_CLASS, "a");
        FOOTER_FIELD = Reflections.getField(TablistImpl.PLAYER_LIST_HEADER_FOOTER_CLASS, "b");
        ADD_PLAYER = (Enum)Reflections.getNMSClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction").getEnumConstants()[0];
        UPDATE_PLAYER = (Enum)Reflections.getNMSClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction").getEnumConstants()[3];
        try {
            TablistImpl.playerInfoDataConstructor = TablistImpl.PLAYER_INFO_DATA_CLASS.getConstructor(TablistImpl.PLAYER_INFO_CLASS, TablistImpl.GAME_PROFILE_CLASS, Integer.TYPE, TablistImpl.ENUM_GAMEMODE_CLASS, TablistImpl.BASE_COMPONENT_CLASS);
            TablistImpl.gameProfileConstructor = TablistImpl.GAME_PROFILE_CLASS.getConstructor(UUID.class, String.class);
        }
        catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }
}

