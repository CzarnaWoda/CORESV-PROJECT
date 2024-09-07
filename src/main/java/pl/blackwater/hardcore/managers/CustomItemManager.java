package pl.blackwater.hardcore.managers;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import pl.blackwater.hardcore.settings.CustomItemConfig;
import pl.blackwater.hardcore.utils.CoreUtil;
import pl.blackwaterapi.utils.ItemBuilder;
import pl.blackwaterapi.utils.Logger;
import pl.blackwaterapi.utils.Util;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class CustomItemManager {
    private List<Character> characters;
    private ItemStack generator,sandfarmer,boyfarmer,airgenerator,specialstone,goldenapple,mysterybox;


    public CustomItemManager(){
        characters = Arrays.asList('A','B','C','D','E','F','G','H','I');

        generator = buildGenerator();
        sandfarmer = buildSandfarmer();
        boyfarmer = buildBoyfarmer();
        airgenerator = buildAirGenerator();
        specialstone = buildSpecialStone();
        goldenapple = buildGoldenApple();
        mysterybox = buildMysteryBox();
        if(CustomItemConfig.ITEMS_STONIARKA_ENABLE)
            registerGeneratorRecipe();
        if(CustomItemConfig.ITEMS_SANDFARMER_ENABLE)
            registerSandfarmerRecipe();
        if(CustomItemConfig.ITEMS_BOYFARMER_ENABLE)
            registerBoyfarmerRecipe();
        if(CustomItemConfig.ITEMS_AIRGENERATOR_ENABLE)
            registerAirgeneratorRecipe();
        if(CustomItemConfig.ITEMS_SPECIALSTONE_ENABLE)
            registerSpecialstoneRecipe();
        if(CustomItemConfig.ITEMS_GOLDENAPPLE_ENABLE)
            registerGoldenAppleRecipe();
    }
    public ItemStack buildGenerator(){
        ItemBuilder itemBuilder = new ItemBuilder(Material.matchMaterial(CustomItemConfig.ITEMS_STONIARKA_ITEM),1);
        itemBuilder.addEnchantment(Enchantment.DURABILITY,10);
        itemBuilder.setTitle(Util.fixColor(Util.replaceString(CustomItemConfig.ITEMS_STONIARKA_NAME)));
        CustomItemConfig.ITEMS_STONIARKA_LORE.forEach(s -> itemBuilder.addLore(Util.fixColor(Util.replaceString(s))));

        return itemBuilder.build();
    }

    public ItemStack buildBoyfarmer(){
        ItemBuilder itemBuilder = new ItemBuilder(Material.matchMaterial(CustomItemConfig.ITEMS_BOYFARMER_ITEM),1);
        itemBuilder.addEnchantment(Enchantment.DURABILITY,10);
        itemBuilder.setTitle(Util.fixColor(Util.replaceString(CustomItemConfig.ITEMS_BOYFARMER_NAME)));
        CustomItemConfig.ITEMS_BOYFARMER_LORE.forEach(s -> itemBuilder.addLore(Util.fixColor(Util.replaceString(s))));

        return itemBuilder.build();
    }

    public ItemStack buildMysteryBox(){
        ItemBuilder itemBuilder = new ItemBuilder(Material.matchMaterial(CustomItemConfig.ITEMS_MYSTERYBOX_ITEM),1);
        itemBuilder.addEnchantment(Enchantment.DURABILITY,10);
        itemBuilder.setTitle(Util.fixColor(Util.replaceString(CustomItemConfig.ITEMS_MYSTERYBOX_NAME)));
        CustomItemConfig.ITEMS_MYSTERYBOX_LORE.forEach(s -> itemBuilder.addLore(Util.fixColor(Util.replaceString(s))));

        return itemBuilder.build();
    }

    public ItemStack buildSandfarmer(){
        ItemBuilder itemBuilder = new ItemBuilder(Material.matchMaterial(CustomItemConfig.ITEMS_SANDFARMER_ITEM),1);
        itemBuilder.addEnchantment(Enchantment.DURABILITY,10);
        itemBuilder.setTitle(Util.fixColor(Util.replaceString(CustomItemConfig.ITEMS_SANDFARMER_NAME)));
        CustomItemConfig.ITEMS_SANDFARMER_LORE.forEach(s -> itemBuilder.addLore(Util.fixColor(Util.replaceString(s))));

        return itemBuilder.build();
    }

    public ItemStack buildAirGenerator(){
        ItemBuilder itemBuilder = new ItemBuilder(Material.matchMaterial(CustomItemConfig.ITEMS_AIRGENERATOR_ITEM),1);
        itemBuilder.addEnchantment(Enchantment.DURABILITY,10);
        itemBuilder.setTitle(Util.fixColor(Util.replaceString(CustomItemConfig.ITEMS_AIRGENERATOR_NAME)));
        CustomItemConfig.ITEMS_AIRGENERATOR_LORE.forEach(s -> itemBuilder.addLore(Util.fixColor(Util.replaceString(s))));

        return itemBuilder.build();
    }
    public ItemStack buildSpecialStone(){
        ItemBuilder itemBuilder = new ItemBuilder(Material.matchMaterial(CustomItemConfig.ITEMS_SPECIALSTONE_ITEM),1);
        itemBuilder.addEnchantment(Enchantment.DURABILITY,10);
        itemBuilder.setTitle(Util.fixColor(Util.replaceString(CustomItemConfig.ITEMS_SPECIALSTONE_NAME)));
        CustomItemConfig.ITEMS_SPECIALSTONE_LORE.forEach(s -> itemBuilder.addLore(Util.fixColor(Util.replaceString(s))));

        return itemBuilder.build();
    }
    public ItemStack buildGoldenApple(){
        ItemBuilder itemBuilder = new ItemBuilder(Material.matchMaterial(CustomItemConfig.ITEMS_GOLDENAPPLE_ITEM),1);
        itemBuilder.addEnchantment(Enchantment.DURABILITY,10);
        itemBuilder.setTitle(Util.fixColor(Util.replaceString(CustomItemConfig.ITEMS_GOLDENAPPLE_NAME)));
        CustomItemConfig.ITEMS_GOLDENAPPLE_LORE.forEach(s -> itemBuilder.addLore(Util.fixColor(Util.replaceString(s))));

        return itemBuilder.build();
    }
    public void registerGoldenAppleRecipe(){
        final List<Integer> crafting = CustomItemConfig.ITEMS_GOLDENAPPLE_CRAFTING;
        if(crafting.size() < 9){
            Logger.warning("Blad w configuracji ITEMS_GOLDENAPPLE_CRAFTING (items.yml), nie ma 9 materialow!");
            return;
        }
        ShapedRecipe recipe = new ShapedRecipe(getGenerator());
        recipe.shape("ABC","DEF","GHI");
        for(int i = 0; i < 9; i++){
            recipe.setIngredient(characters.get(i), Material.getMaterial(crafting.get(i)));
        }
        Bukkit.addRecipe(recipe);
        CoreUtil.sendConsoleMessage("GoldenApple crafting registered !");
    }

    public void registerGeneratorRecipe(){
        final List<Integer> crafting = CustomItemConfig.ITEMS_STONIARKA_CRAFTING;
        if(crafting.size() < 9){
            Logger.warning("Blad w configuracji ITEMS_STONIARKA_CRAFTING (items.yml), nie ma 9 materialow!");
            return;
        }
        ShapedRecipe recipe = new ShapedRecipe(getGenerator());
        recipe.shape("ABC","DEF","GHI");
        for(int i = 0; i < 9; i++){
            recipe.setIngredient(characters.get(i), Material.getMaterial(crafting.get(i)));
        }
        Bukkit.addRecipe(recipe);
        CoreUtil.sendConsoleMessage("Stoniarka crafting registered !");
    }

    public void registerBoyfarmerRecipe(){
        final List<Integer> crafting = CustomItemConfig.ITEMS_BOYFARMER_CRAFTING;
        if(crafting.size() < 9){
            Logger.warning("Blad w configuracji ITEMS_BOYFARMER_CRAFTING (items.yml), nie ma 9 materialow!");
            return;
        }
        ShapedRecipe recipe = new ShapedRecipe(getBoyfarmer());
        recipe.shape("ABC","DEF","GHI");
        for(int i = 0; i < 9; i++){
            recipe.setIngredient(characters.get(i), Material.getMaterial(crafting.get(i)));
        }
        Bukkit.addRecipe(recipe);
        CoreUtil.sendConsoleMessage("BOYFARMER crafting registered !");
    }
    public void registerSandfarmerRecipe(){
        final List<Integer> crafting = CustomItemConfig.ITEMS_SANDFARMER_CRAFTING;
        if(crafting.size() < 9){
            Logger.warning("Blad w configuracji ITEMS_SANDFARMER_CRAFTING (items.yml), nie ma 9 materialow!");
            return;
        }
        ShapedRecipe recipe = new ShapedRecipe(getSandfarmer());
        recipe.shape("ABC","DEF","GHI");
        for(int i = 0; i < 9; i++){
            recipe.setIngredient(characters.get(i), Material.getMaterial(crafting.get(i)));
        }
        Bukkit.addRecipe(recipe);
        CoreUtil.sendConsoleMessage("SANDFARMER crafting registered !");
    }
    public void registerAirgeneratorRecipe(){
        final List<Integer> crafting = CustomItemConfig.ITEMS_AIRGENERATOR_CRAFTING;
        if(crafting.size() < 9){
            Logger.warning("Blad w configuracji ITEMS_AIRGENERATOR_CRAFTING (items.yml), nie ma 9 materialow!");
            return;
        }
        ShapedRecipe recipe = new ShapedRecipe(getAirgenerator());
        recipe.shape("ABC","DEF","GHI");
        for(int i = 0; i < 9; i++){
            recipe.setIngredient(characters.get(i), Material.getMaterial(crafting.get(i)));
        }
        Bukkit.addRecipe(recipe);
        CoreUtil.sendConsoleMessage("AIRGENERATOR crafting registered !");
    }
    public void registerSpecialstoneRecipe(){
        final List<Integer> crafting = CustomItemConfig.ITEMS_SPECIALSTONE_CRAFTING;
        if(crafting.size() < 9){
            Logger.warning("Blad w configuracji ITEMS_SPECIALSTONE_CRAFTING (items.yml), nie ma 9 materialow!");
            return;
        }
        ShapedRecipe recipe = new ShapedRecipe(getSpecialstone());
        recipe.shape("ABC","DEF","GHI");
        for(int i = 0; i < 9; i++){
            recipe.setIngredient(characters.get(i), Material.getMaterial(crafting.get(i)));
        }
        Bukkit.addRecipe(recipe);
        CoreUtil.sendConsoleMessage("SPECIALSTONE crafting registered !");
    }
}
