package fr.tretinv3.box;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandKey implements CommandExecutor {
    private Plugin plugin;

    public CommandKey(Plugin plug){
        this.plugin = plug;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(args.length == 2 || args.length == 3) {
            Player target = Bukkit.getPlayer(args[0]);
            if(plugin.getConfig().contains("box."+args[1])){
                int amount = 1;
                if(args.length == 3){
                    try {
                        amount = Integer.valueOf(args[2]);
                    }catch (Exception e){

                    }
                }

                ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK,amount);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§5§l" + args[1] + "§r Key");
                meta.addEnchant(Enchantment.DURABILITY,1,true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                item.setItemMeta(meta);

                target.getInventory().addItem(item);
                target.updateInventory();

                sender.sendMessage("Vous venez de donner une clé §5§l" + args[1]+"§r à "+args[0]);

                //String message = ;
                //message.replace('&','§');

                //message.replace("{player}",target.getDisplayName());
                //message.replace("{key}",args[1]);

                if(plugin.getConfig().getStringList("say.key").contains(args[1])) Bukkit.getServer().broadcastMessage(plugin.getConfig().getString("say.broadcastWhenGetAKey").replace('&','§').replace("{player}",target.getDisplayName()).replace("{key}",args[1]));
                return true;

            }else{
                sender.sendMessage("Cette box n'exite pas : " + args[1]);
            }
        }else if(args[0].equalsIgnoreCase("list")) {
            return true;
        }else{
            sender.sendMessage("cette commande a besojn d'argument : /key <player> <box>");
        }
        return false;
    }

}
