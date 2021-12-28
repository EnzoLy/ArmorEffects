package me.enzo.armoreffects.command;

import com.google.common.collect.Lists;
import me.enzo.armoreffects.util.CC;
import me.enzo.armoreffects.util.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class EffectItemCommand implements CommandExecutor, TabCompleter {

    private List<String> autocomplete =  Lists.newArrayList(
        "fire", "water", "lightning", "poison", "heal", "haste", "strength",
        "jump", "regen", "nightvision", "invisibility", "resistance", "speed", "damage", "jumpboost");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(CC.translate("&cUsage: /effectitem <add> <effect> <level>"));
            return false;
        }

        if (args[0].equalsIgnoreCase("add")) {
            if (args.length < 3) {
                player.sendMessage(CC.translate("&cUsage: /effectitem <add> <effect> <level>"));
                return false;
            }

            String effect = CC.translate(capitalize(args[1]));

            if(!NumberUtils.isInteger(args[2])){
                player.sendMessage(CC.translate(args [2] + " is not a valid number."));
                return false;
            }

            int level = Integer.parseInt(args[2]);

            ItemStack item = player.getItemInHand();

            if(item == null || item.getType() == Material.AIR){
                player.sendMessage(CC.translate("&cYou must be holding an item to add an effect."));
                return false;
            }

            ItemMeta meta = item.getItemMeta();

            List<String> lore = Lists.newArrayList();

            if(meta.hasLore()){
                lore = meta.getLore();
            }

            if(lore.stream().anyMatch(line -> CC.strip(line).contains(CC.strip(effect + " " + NumberUtils.toRoman(level))))){
                player.sendMessage(CC.translate("&cThis item already has this effect."));
                return false;
            }

            if(lore.stream().anyMatch(line -> CC.strip(line).contains(CC.strip(effect)))){
                lore.removeIf(line -> CC.strip(line).contains(CC.strip(effect)));
            }

            lore.add(CC.translate("&7" + effect + " " + NumberUtils.toRoman(level)));

            meta.setLore(lore);

            item.setItemMeta(meta);

            player.sendMessage(CC.translate("&aAdded effect to item."));
        } else if (args[0].equalsIgnoreCase("remove")) {
            if (args.length < 2) {
                player.sendMessage(CC.translate("&cUsage: /effectitem <remove> <effect>"));
                return false;
            }

            String effect = capitalize(args[1]);

            ItemStack item = player.getItemInHand();

            if(item == null || item.getType() == Material.AIR){
                player.sendMessage(CC.translate("&cYou must be holding an item to remove an effect."));
                return false;
            }

            ItemMeta meta = item.getItemMeta();

            if(!meta.hasLore()){
                player.sendMessage(CC.translate("&cThis item does not have any effects."));
                return false;
            }

            List<String> lore = meta.getLore();

            if(lore.stream().noneMatch(line -> CC.strip(line).contains(CC.strip(effect)))){
                player.sendMessage(CC.translate("&cThis item does not have this effect."));
                return false;
            }

            if (effect.equalsIgnoreCase("all")) {
                lore.clear();
            }else {
                lore.removeIf(line -> CC.strip(line).contains(CC.strip(effect)));
            }

            meta.setLore(lore);

            item.setItemMeta(meta);

            player.sendMessage(CC.translate("&aRemoved effect from item."));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) return null;

        if (args.length == 0) {
            return Lists.newArrayList("add", "remove");
        }else if (args.length == 1) {
            return Lists.newArrayList("add", "remove");
        } else if (args.length == 2) {
            return autocomplete;
        }

        return null;
    }

    //Capitalize after chat color
    private String capitalize(String s){

        if (!s.contains("&")) {
            return StringUtils.capitalize(s);
        }

        return capitalizeAt(s, 2);
    }

    private String capitalizeAt(String s, int index){
        char[] split = s.toCharArray();
        char two = split[index];
        char before = split[index - 1];

        if(two == '&'){
            return capitalizeAt(s, index + 2);
        }

        if(before == '&'){
            return capitalizeAt(s, index + 1);
        }

        split[index] = Character.toUpperCase(two);

        return new String(split);
    }
}