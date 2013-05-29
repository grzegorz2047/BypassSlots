package pl.grzegorz2047;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;
public class Main extends JavaPlugin implements Listener  {
	//Packet103SetSlot packet = new Packet103SetSlot();
	int slots = 20;
	@Override
	public void onEnable() {
		try {
		    MetricsLite metrics = new MetricsLite(this);
		    metrics.start();
		} catch (IOException e) {
		    // Failed to submit the stats :-(
		}

		
		System.out.println(this.getName() + " Works/dziala");
		getServer().getPluginManager().registerEvents(this, this);
		saveDefaultConfig();
		int temp1 = (Integer) this.getConfig().getInt("slots",slots);
		slots = temp1;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerLogin(PlayerLoginEvent evt) {
		Player player = evt.getPlayer();
		//Location location = evt.getPlayer().getLocation();
		//evt.getPlayer().getWorld().playSound(location,Sound.ENDERDRAGON_GROWL,1, 0);
		Player[] players = this.getServer().getOnlinePlayers();
		if ((evt.getResult() == PlayerLoginEvent.Result.KICK_FULL) && player.hasPermission("bypass.slot"))
	    {
			if(players.length <= slots)
			{
			evt.allow();
			}
			if(players.length > slots)
			{
				String ServerFullMsg ="This server is now FULL";
				 ServerFullMsg = this.getConfig().getString(" ServerFullMsg", ServerFullMsg);
				evt.setKickMessage(ServerFullMsg );
				evt.disallow(evt.getResult(),ServerFullMsg);
				return;
			}
	    }
	  }
	
	
	@EventHandler
	public void pinguPingu(ServerListPingEvent e){
		boolean ShowMaxPluginSlots=false;
		ShowMaxPluginSlots = this.getConfig().getBoolean("ShowMaxPluginSlots");
		if(ShowMaxPluginSlots){
			int FakeMaxPlayersSlots = 20;
			FakeMaxPlayersSlots = this.getConfig().getInt("FakeMaxPlayersSlots");
			e.setMaxPlayers(FakeMaxPlayersSlots);
			int numPlayers=10;
			e.setMotd(e.getMotd() + ChatColor.RED + numPlayers + ChatColor.COLOR_CHAR + FakeMaxPlayersSlots);
		}
	}
	
	
	
	
}
	

