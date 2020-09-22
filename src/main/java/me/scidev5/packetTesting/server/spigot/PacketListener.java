package me.scidev5.packetTesting.server.spigot;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import me.scidev5.packetTesting.client.forge.ForgeMod;
import me.scidev5.packetTesting.client.forge.TestPacket;

public class PacketListener implements PluginMessageListener {

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] data) {
		if (channel != ForgeMod.channel.getId()) return; // TODO: CHANNEL NAME
		
		ByteArrayDataInput dataIn = ByteStreams.newDataInput(data);
		
		String idStr = dataIn.readUTF();
		
		switch (idStr) {
		case TestPacket.PACKET_ID:
			String content = dataIn.readUTF();
			String content2 = dataIn.readUTF();
			SpigotPlugin.getInstance().getServer()
				.broadcastMessage(String.format("[SERVER] %s sent %s and %s", player.getName(), content, content2));
			break;
		}
	}

}
