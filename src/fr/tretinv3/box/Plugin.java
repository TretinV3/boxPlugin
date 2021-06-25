package fr.tretinv3.box;

import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    @Override
    public void onEnable(){
        saveDefaultConfig();

        System.out.println("Box plugin activé !");
        //this.getCommand("chat").setExecutor(new CmdManger());
        getServer().getPluginManager().registerEvents(new Events(this), this);

        getCommand("key").setExecutor(new CommandKey(this));
        getCommand("jsonitem").setExecutor(new CmdManger(this));
    }

    @Override
    public void onDisable(){
        System.out.println("Box plugin désactivé !");
    }
}

