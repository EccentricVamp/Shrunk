package website.eccentric.shrunk.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import website.eccentric.shrunk.Shrunk;

public class Network {
    
    public static final ResourceLocation channelName = new ResourceLocation(Shrunk.MOD_ID, "network");
    public static final String networkVersion = new ResourceLocation(Shrunk.MOD_ID, "1").toString();

    public static SimpleChannel register() {
        final var network = NetworkRegistry.ChannelBuilder.named(channelName)
            .clientAcceptedVersions(version -> true)
            .serverAcceptedVersions(version -> true)
            .networkProtocolVersion(() -> networkVersion)
            .simpleChannel();

        return network;
    }
}
