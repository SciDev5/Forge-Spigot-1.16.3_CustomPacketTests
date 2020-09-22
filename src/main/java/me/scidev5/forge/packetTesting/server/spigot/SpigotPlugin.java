package me.scidev5.forge.packetTesting.server.spigot;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class SpigotPlugin extends JavaPlugin implements Listener, PluginMessageListener {
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "packettesting:main");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "packettesting:main", this);
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] data) {
		this.getServer().broadcastMessage(String.format("[SERVER] %s sent %s", player.getName(), ByteStreams.newDataInput(data).readUTF()));
	}
	
	@EventHandler
	public void on(BlockBreakEvent e) {
		Player p = e.getPlayer();
		ByteArrayDataOutput buff = ByteStreams.newDataOutput();
		
		//buff.writeByte(0);
		buff.writeUTF("Brokebloc");
		buff.writeUTF("SecondMSG");
		
		p.sendPluginMessage(this, "packettesting:main", buff.toByteArray());
	}
}
