package me.scidev5.packetTesting.client.forge;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketChannel {

	public final String CHANNEL_NAME;
	
	private final String PROTOCOL_VERSION = "1";
	public final SimpleChannel INSTANCE;
	
	private int currentMessageTypeId = 0;
	
	public PacketChannel(String channelName) {
		this.CHANNEL_NAME = channelName;
		this.INSTANCE = NetworkRegistry.newSimpleChannel(
				new ResourceLocation(ForgeMod.MOD_ID, CHANNEL_NAME),
				() -> PROTOCOL_VERSION,
				PROTOCOL_VERSION::equals,
				PROTOCOL_VERSION::equals
			);
	}
	
	public <T> void registerPacket(Class<T> clazz, BiConsumer<T, PacketBuffer> encoder, Function<PacketBuffer, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> handler) {
		this.INSTANCE.registerMessage(this.currentMessageTypeId ++, clazz, encoder, decoder, handler);
	}
	
	public String getId() {
		return ForgeMod.MOD_ID + ":" + this.CHANNEL_NAME;
	}
}
