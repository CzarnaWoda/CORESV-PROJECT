package pl.blackwater.hardcore.guilds.data;

import lombok.Data;
import lombok.NonNull;

import java.util.HashMap;

@Data
public class GuildPermission {

    @NonNull private String name;
    @NonNull private boolean status;
    @NonNull private String getBy;
    @NonNull private long getTime;

    public boolean toggle(String getBy, long time){
        this.getBy = getBy;
        this.getTime = time;
        return this.status=!this.status;
    }
    public String compare(){
        return (name + "-" + (status ? "1" : "0") + "-" + getBy + "-" + getTime);
    }
    public static GuildPermission getGuildPermission(String permission){
        final String[] array = permission.split("-");
        return new GuildPermission(array[0],array[1].equals("1"),array[2],Long.parseLong(array[3]));
    }
    public static HashMap<String,GuildPermission> generateStandardPermission(boolean isLider){
        final HashMap<String,GuildPermission> permissions = new HashMap<>();

        final GuildPermission buildPermission = new GuildPermission("buildPermission",isLider,"BRAK",System.currentTimeMillis());//
        final GuildPermission destroyPermission = new GuildPermission("destroyPermission",isLider,"BRAK",System.currentTimeMillis());//
        final GuildPermission openChestPermission = new GuildPermission("openChestPermission",isLider,"BRAK",System.currentTimeMillis());//
        final GuildPermission openFurnacesPermission = new GuildPermission("openFurnacesPermission",isLider,"BRAK",System.currentTimeMillis());//
        final GuildPermission destroyGeneratorPermission = new GuildPermission("destroyGeneratorPermission",isLider,"BRAK",System.currentTimeMillis());//
        final GuildPermission openDepositPermission = new GuildPermission("openDepositPermission",isLider,"BRAK",System.currentTimeMillis());
        final GuildPermission kickPlayersPermission = new GuildPermission("kickPlayersPermission",isLider,"BRAK",System.currentTimeMillis());//
        final GuildPermission togglePvPPermission = new GuildPermission("togglePvPPermission",isLider,"BRAK",System.currentTimeMillis());//
        final GuildPermission managePermission = new GuildPermission("managePermission",isLider,"BRAK",System.currentTimeMillis());//
        final GuildPermission manageProfilesPermission = new GuildPermission("manageProfilesPermission",isLider,"BRAK",System.currentTimeMillis());//
        final GuildPermission buildTnTPermission = new GuildPermission("buildTnTPermission",isLider,"BRAK",System.currentTimeMillis());//
        final GuildPermission destroyObsidianPermission = new GuildPermission("destroyObsidianPermission",isLider,"BRAK",System.currentTimeMillis());//


        permissions.put(buildPermission.getName(), buildPermission);
        permissions.put(destroyPermission.getName(),destroyPermission);
        permissions.put(openChestPermission.getName(), openChestPermission);
        permissions.put(openFurnacesPermission.getName(),openFurnacesPermission);
        permissions.put(destroyGeneratorPermission.getName(),destroyGeneratorPermission);
        permissions.put(openDepositPermission.getName(),openDepositPermission);
        permissions.put(kickPlayersPermission.getName(), kickPlayersPermission);
        permissions.put(togglePvPPermission.getName(), togglePvPPermission);
        permissions.put(managePermission.getName(), managePermission);
        permissions.put(manageProfilesPermission.getName(),manageProfilesPermission);

        return permissions;

    }
    public static HashMap<String,GuildPermission> getPermissionsFromString(String permissions){
        final HashMap<String,GuildPermission> p = new HashMap<>();
        for(String permission : permissions.split(";")){
            final GuildPermission perm = getGuildPermission(permission);
            p.put(perm.getName(),perm);
        }
        return p;
    }
    public static String getStringFromPermissions(HashMap<String,GuildPermission> permissions){
        StringBuilder s = new StringBuilder();
        for(GuildPermission permission : permissions.values()){
            if(s.toString().equals("")){
                s = new StringBuilder(permission.compare());
            }else{
                s.append(";").append(permission.compare());
            }
        }
        return s.toString();
    }
}
