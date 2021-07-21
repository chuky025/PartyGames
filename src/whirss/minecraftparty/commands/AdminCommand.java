package whirss.minecraftparty.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import whirss.minecraftparty.Main;
import whirss.minecraftparty.Minigame;

public class AdminCommand implements CommandExecutor {
	
	private Main main;
	
	public AdminCommand(Main main) {
		this.main = main;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){    	
			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to run this command.");
				return true;
			}
			final Player p = (Player)sender;

			if(args.length > 0){
				if(args[0].equalsIgnoreCase("setup")){
					if(p.hasPermission("minecraftparty.admin.setup") || sender.hasPermission("minecraftparty.admin.*")){
						if (args.length > 1) {
							if (args[1].equalsIgnoreCase("colormatch")) {
								sender.sendMessage(ChatColor.GREEN + "Starting the creation of ColorMatch");
								Bukkit.getServer().getScheduler().runTask(main, new Runnable(){
									public void run(){
										main.setupColorMatch(p.getLocation());
									}
								});
								sender.sendMessage(ChatColor.GREEN + "Finished");
							}
							if (args[1].equalsIgnoreCase("spleef")) {
								Bukkit.getServer().getScheduler().runTask(main, new Runnable(){
									public void run(){
										sender.sendMessage(ChatColor.GREEN + "Starting the creation of Spleef");
										main.setupSpleef(p.getLocation());
										sender.sendMessage(ChatColor.GREEN + "Finished");
									}
								});
							}
							if (args[1].equalsIgnoreCase("minefield")) {
								Bukkit.getServer().getScheduler().runTask(main, new Runnable(){
									public void run(){
										sender.sendMessage(ChatColor.GREEN + "Starting the creation of MineField");
										main.setupMineField(p.getLocation());
										sender.sendMessage(ChatColor.GREEN + "Finished");
									}
								});
							}
							if (args[1].equalsIgnoreCase("jumpnrun")) {
								Bukkit.getServer().getScheduler().runTask(main, new Runnable(){
									public void run(){
										sender.sendMessage(ChatColor.GREEN + "Starting the creation of JumpnRun");
										main.setupJumpnRun(p.getLocation());
										sender.sendMessage(ChatColor.GREEN + "Finished");
									}
								});
							}
							if (args[1].equalsIgnoreCase("deadend")) {
								Bukkit.getServer().getScheduler().runTask(main, new Runnable(){
									public void run(){
										sender.sendMessage(ChatColor.GREEN + "Starting the creation of DeadEnd");
										main.setupDeadEnd(p.getLocation());
										sender.sendMessage(ChatColor.GREEN + "Finished");
									}
								});
							}
							if (args[1].equalsIgnoreCase("redalert")) {
								Bukkit.getServer().getScheduler().runTask(main, new Runnable(){
									public void run(){
										sender.sendMessage(ChatColor.GREEN + "Starting the creation of RedAlert");
										main.setupRedAlert(p.getLocation());
										sender.sendMessage(ChatColor.GREEN + "Finished");
									}
								});
							}
							if (args[1].equalsIgnoreCase("lastarcherstanding")) {
								Bukkit.getServer().getScheduler().runTask(main, new Runnable(){
									public void run(){
										sender.sendMessage(ChatColor.GREEN + "Starting the creation of LastArcherStanding");
										main.setupLastArcherStanding(p.getLocation());
										sender.sendMessage(ChatColor.GREEN + "Finished");
									}
								});
							}
							if (args[1].equalsIgnoreCase("sheepfreenzy")) {
								Bukkit.getServer().getScheduler().runTask(main, new Runnable(){
									public void run(){
										sender.sendMessage(ChatColor.GREEN + "Starting the creation of SheepFreenzy");
										main.setupSheepFreenzy(p.getLocation());
										sender.sendMessage(ChatColor.GREEN + "Finished");
									}
								});
							}
							if (args[1].equalsIgnoreCase("smokemonster")) {
								Bukkit.getServer().getScheduler().runTask(main, new Runnable(){
									public void run(){
										sender.sendMessage(ChatColor.GREEN + "Starting the creation of SmokeMonster");
										main.setupSmokeMonster(p.getLocation());
										sender.sendMessage(ChatColor.GREEN + "Finished");
									}
								});
							}
							if (args[1].equalsIgnoreCase("slapfight")) {
								Bukkit.getServer().getScheduler().runTask(main, new Runnable(){
									public void run(){
										sender.sendMessage(ChatColor.GREEN + "Starting the creation of SlapFight");
										main.setupSlapFight(p.getLocation());
										sender.sendMessage(ChatColor.GREEN + "Finished");
									}
								});
							}
						} else {
							sender.sendMessage(ChatColor.RED + "Use: /mpa setup [colormatch, spleef, minefield, jumpnrun, deadend, redalert, lastarcherstanding, sheepfreenzy, smokemonster, slapfight]");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
					}
				}else if(args[0].equalsIgnoreCase("setuppoint")){
					if(p.hasPermission("minecraftparty.admin.setup") || sender.hasPermission("minecraftparty.admin.*")){
						final Location l = main.getComponentForMinigame("ColorMatch", "spawn");
						if(l != null){
							l.add(0.5, -2, 0.5);
							Bukkit.getScheduler().runTaskLater(main, new Runnable(){
								public void run(){
									p.setAllowFlight(true);
									p.setFlying(true);
									p.teleport(l);
								}
							}, 5L);
						}
					} else {
						sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
					}
				
				}else if(args[0].equalsIgnoreCase("setlobby")){
					if(sender.hasPermission("minecraftparty.admin.setlobby")){
						main.getConfig().set("lobby.world", p.getLocation().getWorld().getName());
						main.getConfig().set("lobby.location.x", p.getLocation().getBlockX());
						main.getConfig().set("lobby.location.y", p.getLocation().getBlockY());
						main.getConfig().set("lobby.location.z", p.getLocation().getBlockZ());
						main.saveConfig();
						sender.sendMessage(ChatColor.GREEN + "Saved Main lobby.");	
					} else {
						sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
					}
				}else if(args[0].equalsIgnoreCase("setcomponent")){
					// /mp setcomponent [minigame] [component]
					if(sender.hasPermission("minecraftparty.admin.setup") || sender.hasPermission("minecraftparty.admin.*")){
						if(args.length > 2){
							main.saveComponentForMinigame(args[1], args[2], p.getLocation());
							sender.sendMessage(ChatColor.GREEN + "Saved component");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
					}
				}else if(args[0].equalsIgnoreCase("list")){
					if(sender.hasPermission("minecraftparty.admin.list") || sender.hasPermission("minecraftparty.admin.*")) {
						sender.sendMessage(ChatColor.DARK_AQUA + "-- " + ChatColor.GOLD + "Minigames:" + ChatColor.DARK_AQUA + "--");
						for(Minigame m : main.minigames) {
							if(m.isEnabled()) {
								sender.sendMessage(ChatColor.GREEN + m.name);
							} else {
								sender.sendMessage(ChatColor.RED + m.name);
							}
						}
					} else {
						sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
					}
				}else if(args[0].equalsIgnoreCase("reload")){
					if(sender.hasPermission("minecraftparty.admin.reload") || sender.hasPermission("minecraftparty.admin.*")) {
						main.reloadConfig();
						sender.sendMessage(ChatColor.GREEN + "Successfully reloaded config.");
					} else {
						sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
					}
				}else if(args[0].equalsIgnoreCase("enable")){
					if(sender.hasPermission("minecraftparty.admin.enable")) {
						if(args.length > 1) {
							main.enableMinigame(sender, args[1]);
						} else {
							sender.sendMessage(ChatColor.RED + "Use: /mpa setup [colormatch, spleef, minefield, jumpnrun, deadend, redalert, lastarcherstanding, sheepfreenzy, smokemonster, slapfight]");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
					}
				}else if(args[0].equalsIgnoreCase("disable")){
					if(sender.hasPermission("minecraftparty.admin.disable") || sender.hasPermission("minecraftparty.admin.*")) {
						if(args.length > 1) {
							main.disableMinigame(sender, args[1]);
						} else {
							sender.sendMessage(ChatColor.RED + "Use: /mp disable <colormatch/spleef/minefield/jumpnrun/deadend/redalert/lastarcherstanding/sheepfreenzy/smokemonster/slapfight>");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
					}
				}else{
					sender.sendMessage(ChatColor.RED + "Unknown command");
				}
			}else{
				if(sender.hasPermission("minecraftparty.admin.help") || sender.hasPermission("minecraftparty.admin.*")) {
					sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Minecraft" + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Party " + ChatColor.GRAY + "- " + ChatColor.WHITE + "Help");
					sender.sendMessage(ChatColor.GREEN + "/mpa setlobby " + ChatColor.WHITE + "Sets the server lobby.");
					sender.sendMessage(ChatColor.GREEN + "/mpa setup [game] " + ChatColor.WHITE + "Sets the mini-games ");
					sender.sendMessage(ChatColor.GREEN + "/mpa enable/disable [game] " + ChatColor.WHITE + "Activate or deactivate a mini-game");
					sender.sendMessage(ChatColor.GREEN + "/mpa list " + ChatColor.WHITE + "See the list of minigames");
					sender.sendMessage(ChatColor.GREEN + "/mpa setcomponent [game] [component] " + ChatColor.WHITE + "See a player statistics");
					sender.sendMessage(ChatColor.GREEN + "/mpa reload " + ChatColor.WHITE + "Reload the plugin configuration");
				} else {
					sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
				}
			}
			return true;
	}

}
