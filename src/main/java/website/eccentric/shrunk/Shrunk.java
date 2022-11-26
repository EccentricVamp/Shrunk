package website.eccentric.shrunk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.simple.SimpleChannel;
import website.eccentric.shrunk.network.Channel;
import website.eccentric.shrunk.network.ShrunkMessage;
import website.eccentric.shrunk.network.UnshrunkMessage;

@Mod(Shrunk.ID)
public class Shrunk {
    public static final String ID = "shrunk";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static final KeyMapping KEY_SHRUNK = new KeyMapping(
            "key." + ID + ".shrunk.desc",
            KeyConflictContext.IN_GAME,
            InputConstants.UNKNOWN,
            "key." + ID + ".category");

    public static final KeyMapping KEY_UNSHRUNK = new KeyMapping(
            "key." + ID + ".unshrunk.desc",
            KeyConflictContext.IN_GAME,
            InputConstants.UNKNOWN,
            "key." + ID + ".category");

    public static SimpleChannel CHANNEL;

    public Shrunk() {
        var modEvent = FMLJavaModLoadingContext.get().getModEventBus();
        modEvent.addListener(this::onCommonSetup);
        modEvent.addListener(this::onRegisterKeyMappings);

        MinecraftForge.EVENT_BUS.addListener(this::onClientTick);
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        CHANNEL = Channel.register();
    }

    private void onRegisterKeyMappings(RegisterKeyMappingsEvent keyMapping) {
        keyMapping.register(KEY_SHRUNK);
        keyMapping.register(KEY_UNSHRUNK);
    }

    private void onClientTick(ClientTickEvent event) {
        if (KEY_SHRUNK.isDown()) {
            CHANNEL.sendToServer(new ShrunkMessage());
        }

        if (KEY_UNSHRUNK.isDown()) {
            CHANNEL.sendToServer(new UnshrunkMessage());
        }
    }
}
