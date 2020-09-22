package me.scidev5.forge.packetTesting.client;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod(PacketTesting.MODID)
public class PacketTesting {
	public static final String MODID = "packettesting";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
		new ResourceLocation(MODID, "main"),
		() -> PROTOCOL_VERSION,
		PROTOCOL_VERSION::equals,
		PROTOCOL_VERSION::equals
	);
	
	public PacketTesting() {
		INSTANCE.registerMessage(0, TestPacket.class, (BiConsumer<TestPacket, PacketBuffer>) TestPacket::encode, (Function<PacketBuffer, TestPacket>) TestPacket::decode, (BiConsumer<TestPacket, Supplier<NetworkEvent.Context>>) TestPacket::handle);
	}
}
