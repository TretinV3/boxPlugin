package fr.tretinv3.box;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Events implements Listener {
    Plugin plugin;
    JsonItemStack jsonItem = new JsonItemStack();

    public Events(Plugin plug) {
        this.plugin = plug;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) { //Quel est l'événement et comment s'appellera-t-il ? Dans notre cas, il s'appellera e.

        if(!plugin.getConfig().getBoolean("welcomeMessage")) return;

        Player player = e.getPlayer(); //Créer la variable p.
        player.sendMessage(ChatColor.DARK_GREEN + "Bienvenue à toi !"); //Envoyer un message privé au joueur
        Bukkit.broadcastMessage(ChatColor.GREEN + player.getName() + ChatColor.DARK_RED + "a rejoint le serveur !"); //Envoyer un message public qui dit que le joueur p a rejoint le serveur
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        if (e.getPlayer() instanceof Player) {
            Player player = e.getPlayer();
            //e.setCancelled(true);
            //player.sendMessage("Vous ne pouvez pas envoyer ce message :\n" + ChatColor.GREEN + message);
        }
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        InventoryAction action = e.getAction();
        Inventory inv = e.getClickedInventory();
        Player player = (Player) e.getWhoClicked();
        if (player.getOpenInventory().getTitle().equalsIgnoreCase("§5§lCasino")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        if (player.getOpenInventory().getTitle().equalsIgnoreCase("§5§lCasino")) {
            //e.setCancelled(true);
            //player.openInventory(e.getInventory());
        }
    }

    public boolean isSameLocation(Location l, String s) {
        String[] args = s.split(" ");
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int z = Integer.parseInt(args[2]);
        return ((l.getBlockX() == x) && (l.getBlockY() == y) && (l.getBlockZ() == z));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) throws ParseException, InterruptedException {
        Player player = e.getPlayer();
        Action action = e.getAction();
        ItemStack it = e.getItem();
        ArrayList<String> coffres = (ArrayList<String>) plugin.getConfig().getStringList("chest");

        Boolean inChest = false;

        for (int i = 0; i < coffres.size(); i++) {
            if (isSameLocation(e.getClickedBlock().getLocation(), coffres.get(i))) inChest = true;
        }

        if (inChest) {
            e.setCancelled(true);

            if (it != null && it.getType() == Material.TRIPWIRE_HOOK && action == Action.RIGHT_CLICK_BLOCK) {


                if (it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().contains("§5§l") && it.getItemMeta().getDisplayName().contains("§r Key")) {
                    if (e.getClickedBlock().getType() == Material.CHEST) {
                        String key = it.getItemMeta().getDisplayName().substring(4, it.getItemMeta().getDisplayName().length() - 6);
                        //player.sendMessage("GG");
                        //if(!player.hasPermission("box.open."+key)) return;
                        ItemStack newItem = new ItemStack(it.getType(), it.getAmount() - 1);
                        newItem.setItemMeta(it.getItemMeta());
                        player.getInventory().setItemInMainHand(newItem);
                        player.updateInventory();

                        guiCasino(player, key);

                    }
                }else{
                    player.sendMessage(plugin.getConfig().getString("say.messageWhenPlayerHaveNoKey").replace('&','§'));
                    player.playSound(player.getLocation(),Sound.ENTITY_VILLAGER_NO,10,1);
                }
            }else{
                player.sendMessage(plugin.getConfig().getString("say.messageWhenPlayerHaveNoKey").replace('&','§'));
                player.playSound(player.getLocation(),Sound.ENTITY_VILLAGER_NO,10,1);
            }
        }
    }

    public ItemStack createItem(Material m, String name, int count) {
        ItemStack it = new ItemStack(m, count);
        ItemMeta meta = it.getItemMeta();
        meta.setDisplayName(name);
        it.setItemMeta(meta);
        return (it);
    }

    private void guiCasino(Player player, String key) throws ParseException, InterruptedException {
        //player.sendMessage('<'+key+'>');
        Inventory inv = Bukkit.createInventory(null, 3 * 9, "§5§lCasino");

        inv.setItem(0, createItem(Material.ORANGE_STAINED_GLASS_PANE, "§r", 1));
        inv.setItem(2, createItem(Material.ORANGE_STAINED_GLASS_PANE, "§r", 1));
        inv.setItem(19, createItem(Material.ORANGE_STAINED_GLASS_PANE, "§r", 1));
        inv.setItem(21, createItem(Material.ORANGE_STAINED_GLASS_PANE, "§r", 1));
        inv.setItem(1, createItem(Material.LIME_STAINED_GLASS_PANE, "§r", 1));
        inv.setItem(3, createItem(Material.LIME_STAINED_GLASS_PANE, "§r", 1));
        inv.setItem(18, createItem(Material.LIME_STAINED_GLASS_PANE, "§r", 1));
        inv.setItem(20, createItem(Material.LIME_STAINED_GLASS_PANE, "§r", 1));
        inv.setItem(4, createItem(Material.BLACK_STAINED_GLASS_PANE, "§r", 1));
        inv.setItem(22, createItem(Material.BLACK_STAINED_GLASS_PANE, "§r", 1));
        inv.setItem(5, createItem(Material.PURPLE_STAINED_GLASS_PANE, "§r", 1));
        inv.setItem(7, createItem(Material.PURPLE_STAINED_GLASS_PANE, "§r", 1));
        inv.setItem(24, createItem(Material.PURPLE_STAINED_GLASS_PANE, "§r", 1));
        inv.setItem(26, createItem(Material.PURPLE_STAINED_GLASS_PANE, "§r", 1));
        inv.setItem(6, createItem(Material.RED_STAINED_GLASS_PANE, "§r", 1));
        inv.setItem(8, createItem(Material.RED_STAINED_GLASS_PANE, "§r", 1));
        inv.setItem(23, createItem(Material.RED_STAINED_GLASS_PANE, "§r", 1));
        inv.setItem(25, createItem(Material.RED_STAINED_GLASS_PANE, "§r", 1));

        player.openInventory(inv);


        List<String> stringConfig = plugin.getConfig().getStringList("box." + key);

        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        ArrayList<Boolean> display = new ArrayList<Boolean>();



        JsonParser parser = new JsonParser();


        for (int i = 0; i < stringConfig.size(); i++) {

            ItemStack item = jsonItem.fromJson(stringConfig.get(i));
            if (item != null) {
                items.add(item);

                JsonElement element = parser.parse(stringConfig.get(i));

                JsonObject itemJson = element.getAsJsonObject();

                JsonElement isDisplay = itemJson.get("display");

                if(isDisplay != null) {
                    display.add(isDisplay.getAsBoolean());
                }else{
                    display.add(false);
                }

            } else {
                System.out.println("JSON error in box " + key + " index " + i + ". Replaced to stone");
                items.add(new ItemStack(Material.STONE, 1));
                display.add(false);
            }
            //System.out.println("item : " + item);
            //System.out.println("isNull : " + (item == null));

        }
        //System.out.println("size : " + items.size());

        Random random = new Random();
        int max = random.nextInt(items.size() + 5) + 5;

        for (int i = 0; i < 9; i++) {
            inv.setItem(9 + i, items.get(1));
            items.add(items.get(items.size() - 1));
            items.remove(items.size() - 1);
        }
        boucle(inv, player, items, 0, max, display);
    }

    private void boucle(Inventory inv, Player player, ArrayList<ItemStack> items, int index, int max, ArrayList<Boolean> display) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                //System.out.println("max : " + max + "| index : " + index + "");

                for (int i = 8; i >= 0; i--) {
                    int j = (i + index) % items.size();
                    inv.setItem(9 + i, items.get(j));
                }
                //player.openInventory(inv);
                player.updateInventory();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 10, 1);
                if (index >= max) {
                    fin(inv, player, items, index, max, display);
                } else {
                    boucle(inv, player, items, index + 1, max, display);
                }
            }
        }, index);
    }

    private void fin(Inventory inv, Player player, ArrayList<ItemStack> items, int index, int max, ArrayList<Boolean> display) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                player.getInventory().addItem(inv.getItem(13));
                player.updateInventory();
                player.closeInventory();

                Location loc = player.getLocation().add(0,1,0);

                loc.getWorld().spawnParticle(Particle.VILLAGER_HAPPY,loc,20,1,1,1);

                boolean isAfficheOn = display.get(items.indexOf(inv.getItem(13)));

                if (isAfficheOn) {
                    String message = plugin.getConfig().getString("say.broadcastWhenWin");
                    message = message.replace('&', '§');

                    message = message.replace("{player}", player.getDisplayName());
                    message = message.replace("{item}", inv.getItem(13).getType().name() + "x" + inv.getItem(13).getAmount());

                    Bukkit.getServer().broadcastMessage(message);
                }
            }
        }, 40);
    }
}