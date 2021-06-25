package fr.tretinv3.box;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdManger implements CommandExecutor {
    Plugin plugin;

    JsonItemStack jsonItem = new JsonItemStack();
    public CmdManger(Plugin plug) {
        this.plugin = plug;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if (command.getName().equalsIgnoreCase("jsonitem")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                ItemStack it = p.getInventory().getItemInMainHand();
                String json = jsonItem.toJson(it);

                //String commandToSend = "tellraw ${player} {\"text\":\"${json}\",\"clickEvent\":{\"action\":\"copy_to_clipboard\",\"value\":\"${json}\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Clique pour copier\"}}"
                //        .replace("${player}",p.getName())
                //        .replace("${json}",json);

                //Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), commandToSend);
                p.sendMessage(json);
            }else{
                sender.sendMessage("§4Cette commande est seulement utilisable par un joueur !");
            }
        }
        return false;

    }
}
