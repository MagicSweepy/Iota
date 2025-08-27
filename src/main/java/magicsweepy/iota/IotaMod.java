package magicsweepy.iota;

import magicsweepy.iota.api.IotaTags;
import magicsweepy.iota.util.SidedLogger;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;
import org.jspecify.annotations.NullMarked;

@Mod(modid = IotaTags.MOD_ID,
     name = IotaTags.MOD_NAME,
     version = IotaTags.MOD_VERSION)
@NullMarked
public final class IotaMod
{

    public static final Logger logger = new SidedLogger(IotaTags.MOD_ID);

    public static ResourceLocation id(String path)
    {
        return new ResourceLocation(IotaTags.MOD_ID, path);
    }

}
