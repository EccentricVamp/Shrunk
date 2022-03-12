package website.eccentric.shrunk.data;

import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class Generator {
    
    public static void onGatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
    }
}
