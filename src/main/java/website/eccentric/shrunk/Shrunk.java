package website.eccentric.shrunk;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.simple.SimpleChannel;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import website.eccentric.shrunk.configuration.CommonConfiguration;
import website.eccentric.shrunk.data.Generator;
import website.eccentric.shrunk.network.Network;

@Mod(Shrunk.MOD_ID)
public class Shrunk {

	public static final String MOD_ID = "shrunk";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	
    public static final Tag.Named<Item> CURIOS_CURIO = ItemTags.bind(new ResourceLocation("curios", "curio").toString());
	
    public static SimpleChannel NETWORK;

    private final Map<String, KeyMapping> keyMappings = new HashMap<String, KeyMapping>();

	public Shrunk() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        //ITEMS.register(bus);
        //CONTAINERS.register(bus);
        //RECIPES.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfiguration.COMMON_CONFIG);

        bus.addListener(this::onInterModEnqueue);
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onClientSetup);
        bus.addListener(Generator::onGatherData);

        //RecipeUnlocker.register(MODID, MinecraftForge.EVENT_BUS, 2);
	}

    private void onInterModEnqueue(InterModEnqueueEvent event) {
        if (ModList.get().isLoaded("curios")) {
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CURIO.getMessageBuilder().build());
        }
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        NETWORK = Network.register();
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        //MenuScreens.register(SBCONTAINER.get(), SBGui::new);

        var toggle = new KeyMapping("key." + MOD_ID + ".toggle.desc", -1, "key." + MOD_ID + ".category");
        keyMappings.put("toggle", toggle);

        ClientRegistry.registerKeyBinding(toggle);
    }
}
