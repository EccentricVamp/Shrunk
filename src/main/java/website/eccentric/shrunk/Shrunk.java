package website.eccentric.shrunk;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.simple.SimpleChannel;
import website.eccentric.shrunk.network.Network;
import website.eccentric.shrunk.network.ShrunkMessage;
import website.eccentric.shrunk.network.UnshrunkMessage;

@Mod(Shrunk.MOD_ID)
public class Shrunk {

	public static final String MOD_ID = "shrunk";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	
    public static SimpleChannel NETWORK;

    private final Map<String, KeyMapping> keyMappings = new HashMap<String, KeyMapping>();

	public Shrunk() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onClientSetup);

        MinecraftForge.EVENT_BUS.addListener(this::onClientTick);
	}

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        NETWORK = Network.register();
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        var shrunk = new KeyMapping("key." + MOD_ID + ".shrunk.desc", -1, "key." + MOD_ID + ".category");
        var unshrunk = new KeyMapping("key." + MOD_ID + ".unshrunk.desc", -1, "key." + MOD_ID + ".category");

        keyMappings.put("shrunk", shrunk);
        keyMappings.put("unshrunk", unshrunk);

        ClientRegistry.registerKeyBinding(shrunk);
        ClientRegistry.registerKeyBinding(unshrunk);
    }

    private void onClientTick(ClientTickEvent event) {
        if (this.keyMappings.get("shrunk").consumeClick()) {
            NETWORK.sendToServer(new ShrunkMessage());
        }

        if (this.keyMappings.get("unshrunk").consumeClick()) {
            NETWORK.sendToServer(new UnshrunkMessage());
        }
    }
}
