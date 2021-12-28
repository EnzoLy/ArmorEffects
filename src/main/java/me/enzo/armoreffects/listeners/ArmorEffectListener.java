package me.enzo.armoreffects.listeners;

import me.enzo.armoreffects.util.CC;
import me.enzo.armoreffects.util.NumberUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class ArmorEffectListener implements Listener {

    @EventHandler
    public void onItemHoldEvent(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItem(event.getPreviousSlot());

        checkAndRemoveEffect(player, item);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItemDrop().getItemStack();

        checkAndRemoveEffect(player, item);
    }

    @EventHandler
    public void onPlayerMoveItemInHand(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        //check if is armor slots

        if(event.getSlot() != player.getInventory().getHeldItemSlot() &&  event.getSlotType() != InventoryType.SlotType.ARMOR) return;

        ItemStack item = event.getCurrentItem();

        checkAndRemoveEffect(player, item);
    }

    private void checkAndRemoveEffect(Player player, ItemStack item){

        if (item == null || item.getType() == Material.AIR)  return;
        if (!item.hasItemMeta() && !item.getItemMeta().hasLore()) return;

        if (!item.getItemMeta().hasLore()) return;

        List<String> lore = item.getItemMeta().getLore();

        lore.forEach(line -> {
            String[] split = line.split(" ");

            if(split.length <= 1) return;

            String type = CC.strip(split[0]);
            int level = NumberUtils.romanToInt(split[1]);
            PotionEffectType potionEffectType = CC.getPotionEffectType(type);

            if(potionEffectType == null) return;

            if (player.getActivePotionEffects().stream().anyMatch(potionEffect ->

                potionEffect.getType().equals(potionEffectType) &&
                    potionEffect.getAmplifier() == level - 1 &&
                    potionEffect.getDuration() < 400)) {

                player.removePotionEffect(potionEffectType);
            }
        });
    }
}