package whirss.minecraftparty.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import whirss.minecraftparty.Main;
import whirss.minecraftparty.Minigame;
import whirss.minecraftparty.nms.NMSEffectManager;

public class OnEntityDamage implements Listener {
	
	private Main main;
	
	public OnEntityDamage(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event){
		if(event.getEntity() instanceof Player){
			final Player p = (Player)event.getEntity();
			if(main.players.contains(p.getName())){
				if(main.currentmg > -1 && main.currentmg < main.minigames.size()){
					if(main.minigames.get(main.currentmg).name.equalsIgnoreCase("slapfight")){
						// current minigame is SlapFight, enable all damage (except for fall damage)
						if(main.ingame_started){
							// enable damage only when cooldown finished
							if(event.getCause() == DamageCause.FALL){
								event.setCancelled(true);
							}
							p.setHealth(20D);
						}else{
							event.setCancelled(true);
						}
					}else if(main.minigames.get(main.currentmg).name.equalsIgnoreCase("chickentag")){
						// current minigame is chickentag, enable all damage again
						if(main.ingame_started){
							// enable damage only when cooldown finished
							if(event.getCause() == DamageCause.FALL){
								event.setCancelled(true);
							}
							// TODO pass the chicken
							if(main.hasChicken.containsKey(p.getName())){
								if(main.hasChicken.get(p.getName())){
									// if the player already has the chicken, don't allow passing to him
									return;
								}	
							}
							if(event instanceof EntityDamageByEntityEvent){
								EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
								if(e.getDamager() instanceof Player){
									Player p2 = (Player) e.getDamager();
									if(main.hasChicken.containsKey(p2.getName())){
										main.hasChicken.put(p2.getName(), false);
										Entity t = p2.getPassenger();
										p2.eject();
										t.remove();
									}
									NMSEffectManager.createBloodEffect(main, p.getLocation().add(0, 0.5, 0));
									p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + p2.getName() + " passed his Chicken to you! Try to get rid of it!");
								}
							}
							main.hasChicken.put(p.getName(), true);
							final Chicken c = (Chicken) p.getWorld().spawnEntity(p.getLocation(), EntityType.CHICKEN);
							Bukkit.getScheduler().runTaskLater(main, new Runnable(){
								public void run(){
									p.setPassenger(c);
								}
							}, 2L);
							p.setHealth(20D);
						}else{
							event.setCancelled(true);
						}
					}else{
						event.setCancelled(true);
					}
				}
			}
		}
		
		// last archer standing
		if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent event_ = (EntityDamageByEntityEvent) event;
			if (event_.getDamager() instanceof Arrow) {
				final Arrow arrow = (Arrow) event_.getDamager();
				if (arrow.getShooter() instanceof Player && event.getEntity() instanceof Player) {
					Player p = (Player) event.getEntity();
					Player damager = (Player) arrow.getShooter();
					if(main.players.contains(p.getName()) && main.players.contains(damager.getName())){
						if(main.currentmg > -1){
							final Minigame current = main.minigames.get(main.currentmg);
							
							if(!current.lost.contains(p)){
								if(main.started && main.ingame_started){
									current.lost.add(p);
									NMSEffectManager.createBloodEffect(main, p.getLocation().add(0, 0.5, 0));
									int count = 0;
									for(String pl : main.players){
										Player p_ = Bukkit.getPlayerExact(pl);
										if(p_.isOnline()){
											if(!current.lost.contains(p_)){
												count++;
											}
										}
									}
									main.sendPlace(count, p);
									current.spectate(p);
									// there's only one man standing
									if(count < 2){
										main.c_ += main.seconds-main.c;
										main.c = main.seconds; // just skips all the remaining seconds and sets to 60, current timer will do the rest
									}
									
									damager.sendMessage(ChatColor.GOLD + "You shot " + ChatColor.DARK_PURPLE + p.getName() + ChatColor.GOLD + "!");
								}
							}
						}
					}
				}
			}
		}
	}

}
