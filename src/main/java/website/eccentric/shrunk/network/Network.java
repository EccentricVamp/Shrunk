package website.eccentric.shrunk.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import website.eccentric.shrunk.Shrunk;

public class Network {
    
    public static final ResourceLocation channelName = new ResourceLocation(Shrunk.ID, "network");
    public static final String networkVersion = new ResourceLocation(Shrunk.ID, "1").toString();

    public static SimpleChannel register() {
        final var network = NetworkRegistry.ChannelBuilder.named(channelName)
            .clientAcceptedVersions(version -> true)
            .serverAcceptedVersions(version -> true)
            .networkProtocolVersion(() -> networkVersion)
            .simpleChannel();

        network.registerMessage(0, ShrunkMessage.class, ShrunkMessage::encode, ShrunkMessage::decode, ShrunkMessage::handle);
        network.registerMessage(1, UnshrunkMessage.class, UnshrunkMessage::encode, UnshrunkMessage::decode, UnshrunkMessage::handle);

        return network;
    }
}
