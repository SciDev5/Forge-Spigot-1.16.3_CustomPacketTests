package me.scidev5.packetTesting.server.spigot;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.scidev5.packetTesting.client.forge.ForgeMod;
import me.scidev5.packetTesting.client.forge.TestPacket;

public class SpigotPlugin extends JavaPlugin implements Listener {
	
	private static SpigotPlugin instance;
	public static SpigotPlugin getInstance() { return instance; }
	
	public final PacketListener packetListener;
	
	public SpigotPlugin() {
		instance = this;
		this.packetListener = new PacketListener();
	}
	
	@Override
	public void onEnable() {
		this.registerEvents();
		this.registerPluginChannels();
	}
	
	// Register Events
	private void registerEvents() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	// Register plugin channels (for talking to forge).
	private void registerPluginChannels() {
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, ForgeMod.channel.getId());
		this.getServer().getMessenger().registerIncomingPluginChannel(this, ForgeMod.channel.getId(), this.packetListener);
	}

	// EXAMPLE: sending message from bukkit event.
	@EventHandler
	public void on(BlockBreakEvent e) {
		Player p = e.getPlayer();
		ByteArrayDataOutput buff = ByteStreams.newDataOutput();
		
		// Write packet id to buffer.
		buff.writeUTF(TestPacket.PACKET_ID);
		// Write two strings to plugin message data. NOTICE: THESE WILL BE READ SEPERATELY FROM EACH OTHER!
		buff.writeUTF("FirstThing");
		buff.writeUTF("SecondThing");
		
		// Send the message.
		p.sendPluginMessage(this, ForgeMod.channel.getId(), buff.toByteArray());
	}
}
