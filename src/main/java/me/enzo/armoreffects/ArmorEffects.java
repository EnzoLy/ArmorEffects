package me.enzo.armoreffects;

import me.enzo.armoreffects.command.EffectItemCommand;
import me.enzo.armoreffects.listeners.ArmorEffectListener;
import me.enzo.armoreffects.tasks.ArmorCheckTask;
import me.enzo.armoreffects.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ArmorEffects extends JavaPlugin {
    
    @Override
    public void onEnable() {

        registerCommands();
        registerSchedule();
        registerListeners();

        getLogger().info(CC.translate("&aArmorEffects &7has been enabled!"));
    }

    @Override
    public void onDisable() {
        getLogger().info(CC.translate("&aArmorEffects &7has been disabled!"));
    }

    private void registerCommands() {
        EffectItemCommand effectItemCommand = new EffectItemCommand();
        getCommand("effectitem").setExecutor(effectItemCommand);
        getCommand("effectitem").setTabCompleter(effectItemCommand);
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ArmorEffectListener(), this);
    }

    private void registerSchedule() {
        Bukkit.getScheduler().runTaskTimer(this, new ArmorCheckTask(), 0L, 20L);
    }

}
