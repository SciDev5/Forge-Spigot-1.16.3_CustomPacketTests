package me.scidev5.packetTesting.client.forge;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

public class TestPacket {

	public static final String PACKET_NAME = "testPacket";
	public static final String PACKET_ID = ForgeMod.MOD_ID + ":" + PACKET_NAME;
	
	private final String data;
	
	/*
	 * Create the packet; maybe use a builder.
	 */
	private TestPacket(String data) {
		this.data = data;
	}

	/*
	 * A method used to write to a PacketBuffer with the contents of this packet.
	 */
	public static void encode(TestPacket packet, PacketBuffer packetBuffer) {
		packetBuffer.writeString(PACKET_ID);
		packetBuffer.writeString(packet.data);
	}
	
	/*
	 * A method used to decode the content of a PacketBuffer into a packet of this type.
	 */
	public static TestPacket decode(PacketBuffer packetBuffer) {
		String id = packetBuffer.readString();
		if (!id.equals(PACKET_ID)) return null;
		String str = packetBuffer.readString();
		return new TestPacket(str);
	}
	
	/*
	 * A method used to handle when the packet is received.
	 */
	public static void handle(TestPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		
		switch (ctx.getDirection()) {
		case PLAY_TO_CLIENT:
			
			// Show a message in the local player's chat and the console.
			String notifyText = "RECEIVED PACKET ["+PACKET_NAME+"]: \""+packet.data+"\"";
			ForgeMod.LOGGER.info(notifyText);
			Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(ITextComponent.Serializer.func_240644_b_(notifyText)); 
			// NOTE: func_240644_b_ is a method that creates an ITextComponent with plain text.
			
			// Send a packet back to the server (as an example).
			ForgeMod.channel.INSTANCE.sendToServer(new TestPacket("responding to \"" + packet.data + "\""));
			
			// Tell FML we handled this packet.
			ctx.setPacketHandled(true);
			
			break;
		default:
			// This example does not handle these messages.
		}
	}
}
