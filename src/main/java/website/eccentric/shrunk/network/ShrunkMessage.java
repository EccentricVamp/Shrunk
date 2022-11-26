package website.eccentric.shrunk.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ShrunkMessage {
    public static ShrunkMessage decode(final FriendlyByteBuf buffer) {
        buffer.readByte();
        return new ShrunkMessage();
    }

    public static void encode(final ShrunkMessage message, final FriendlyByteBuf buffer) {
        buffer.writeByte(0);
    }

    public static void handle(final ShrunkMessage message, final Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            var player = context.get().getSender();
            if (player == null)
                return;

            var server = player.getServer();
            if (server == null)
                return;

            var source = server.createCommandSourceStack();

            server.getCommands().performPrefixedCommand(source, "scale set 0.2 " + player.getGameProfile().getName());
        });

        context.get().setPacketHandled(true);
    }
}