package me.scidev5.forge.packetTesting.client;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

public class TestPacket {
	
	private final String data;
	
	private TestPacket(String data) {
		this.data = data;
	}

	public static void encode(TestPacket packet, PacketBuffer packetBuffer) {
		packetBuffer.writeString(packet.data);
	}
	public static TestPacket decode(PacketBuffer packetBuffer) {
		String str = packetBuffer.readString();
		return new TestPacket(str);
	}
	public static void handle(TestPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		PacketTesting.LOGGER.info("RECEIVED PACKET: \""+packet.data+"\"");
		if (ctx.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
			PacketTesting.INSTANCE.sendToServer(new TestPacket(packet.data + " xlol"));
			Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(ITextComponent.Serializer.func_240644_b_("YEET")); // func_240644_b_ is print plain text
			ctx.setPacketHandled(true);
		} else {
			
		}
	}
}
