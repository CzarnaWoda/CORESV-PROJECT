package pl.blackwater.hardcore.managers;

import lombok.Getter;
import org.bukkit.Material;
import pl.blackwater.hardcore.data.Kit;

import java.util.HashMap;

public class KitManager {

    @Getter private HashMap<String, Kit> kits = new HashMap<>();

    public Kit getKit(String id){
        return kits.get(id);
    }

    public Kit getKitByMaterial(Material material){
        for(Kit kit : kits.values()){
            if(kit.getMenuItem().equals(material))
                return kit;
        }
        return null;
    }
}
