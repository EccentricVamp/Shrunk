package website.eccentric.shrunk.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfiguration {
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    
    public static final ForgeConfigSpec COMMON_CONFIG;

    //public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_BLACKLIST;

    static {
        COMMON_BUILDER.comment("General").push("general");
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
