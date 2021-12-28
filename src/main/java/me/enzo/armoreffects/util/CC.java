package me.enzo.armoreffects.util;

import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffectType;

public class CC {

    public static String translate(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String strip(String msg){
        return ChatColor.stripColor(msg);
    }

    public static  PotionEffectType getPotionEffectType(String type){

        switch (type.toLowerCase()){
            case "fire" : return PotionEffectType.FIRE_RESISTANCE;
            case"water": return PotionEffectType.WATER_BREATHING;
            case"lightning":
            case"speed":
                return PotionEffectType.SPEED;
            case"poison": return PotionEffectType.POISON;
            case"heal": return PotionEffectType.HEAL;
            case"haste": return PotionEffectType.FAST_DIGGING;
            case"strength":
            case"damage":
                return PotionEffectType.INCREASE_DAMAGE;
            case"jump":
            case"jumpboost":
                return PotionEffectType.JUMP;
            case"regen":
            case"regeneration":
                return PotionEffectType.REGENERATION;
            case"nightvision": return PotionEffectType.NIGHT_VISION;
            case"invisibility": return PotionEffectType.INVISIBILITY;
            case"resistance": return PotionEffectType.DAMAGE_RESISTANCE;
        }

        return PotionEffectType.getByName(type);
    }
}