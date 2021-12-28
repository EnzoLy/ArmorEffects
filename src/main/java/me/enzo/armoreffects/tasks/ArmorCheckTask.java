package me.enzo.armoreffects.tasks;

import me.enzo.armoreffects.util.CC;
import me.enzo.armoreffects.util.NumberUtils;
import me.enzo.armoreffects.util.Version;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ArmorCheckTask implements Runnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            if(player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR){

                ItemStack item = player.getItemInHand();

                if(!item.hasItemMeta()) return;
                if(!item.getItemMeta().hasLore()) return;

                giveEffects(player, item);
            }

            if(Version.getServerVersion(Bukkit.getServer()).isNewerOrSameThan(Version.v1_9_R1)){
                if(player.getInventory().getItemInOffHand().getType() != Material.AIR){

                    ItemStack item = player.getInventory().getItemInOffHand();

                    if(!item.hasItemMeta()) return;
                    if(!item.getItemMeta().hasLore()) return;

                    giveEffects(player, item);
                }
            }

            if(player.getInventory().getArmorContents() != null && player.getInventory().getArmorContents().length != 0){
                ItemStack[] armor = player.getInventory().getArmorContents();

                for (ItemStack item : armor) {

                    if(item == null || item.getType() == Material.AIR) continue;
                    if(!item.hasItemMeta()) continue;
                    if(!item.getItemMeta().hasLore()) continue;

                    giveEffects(player, item);
                }
            }
        }
    }

    private void giveEffects(Player player, ItemStack item) {
        item.getItemMeta().getLore().forEach(lore -> {
            String[] split = lore.split(" ");

            if(split.length <= 1) return;

            String type = CC.strip(split[0]);
            int level = NumberUtils.romanToInt(split[1]);
            PotionEffectType potionEffectType = CC.getPotionEffectType(type);

            if(potionEffectType == null) return;

            if(player.getActivePotionEffects().stream().noneMatch(potionEffect ->  potionEffect.getType().equals(potionEffectType))){
                player.addPotionEffect(new PotionEffect(potionEffectType, 400, level - 1));
            }

            if (player.getActivePotionEffects().stream().anyMatch(potionEffect ->

                    potionEffect.getType().equals(potionEffectType) &&
                    potionEffect.getAmplifier() == level - 1 &&
                    potionEffect.getDuration() <= 400)) {

                player.removePotionEffect(potionEffectType);
                player.addPotionEffect(new PotionEffect(potionEffectType, 400, level - 1));
            }
        });
    }

}
