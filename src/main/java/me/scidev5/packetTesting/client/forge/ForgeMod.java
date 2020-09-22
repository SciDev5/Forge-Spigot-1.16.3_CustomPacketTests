package me.scidev5.packetTesting.client.forge;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkEvent;

@Mod(ForgeMod.MOD_ID)
public class ForgeMod {
	public static final String MOD_ID = "packettesting";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	
	public static final PacketChannel channel = new PacketChannel("channelmain");
	
	public ForgeMod() {
		channel.registerPacket(TestPacket.class, (BiConsumer<TestPacket, PacketBuffer>) TestPacket::encode, (Function<PacketBuffer, TestPacket>) TestPacket::decode, (BiConsumer<TestPacket, Supplier<NetworkEvent.Context>>) TestPacket::handle);
	}
}
