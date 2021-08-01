package whirss.minecraftparty;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PAPIVariables extends PlaceholderExpansion {
	
	private Main main;
	
	public PAPIVariables(Main main) {
		this.main = main;
	}

    @Override
    public String getAuthor() {
        return "Whirss";
    }
    
    @Override
    public String getIdentifier() {
        return "minecraftparty";
    }

    @Override
    public String getVersion() {
        return main.currentversion;
    }
    
    public String onRequest(String player, String params) {
        if(params.equalsIgnoreCase("players_ingame")) {
            return Integer.toString(main.players.size());
        }
        
        if(params.equalsIgnoreCase("min_players")) {
            return Integer.toString(main.min_players);
        }
        
        if(params.equalsIgnoreCase("current_minigame")) {
            return main.getConfig().getString("ag");
        }
        
        if(params.equalsIgnoreCase("time_remaining")) {
            return Integer.toString(main.seconds - main.c);
        }
        
        if(params.equalsIgnoreCase("current_round")) {
            return Integer.toString(main.currentmg + 1);
        }
        
        if(params.equalsIgnoreCase("max_round")) {
            return Integer.toString(main.minigames.size());
        }
        
        if(params.equalsIgnoreCase("credits")) {
            return Integer.toString(main.getPlayerStats(player, "credits"));
        }
        
        if(params.equalsIgnoreCase("wins")) {
            return Integer.toString(main.getPlayerStats(player, "wins"));
        }
        
        return null; // Placeholder is unknown by the Expansion
    }
}