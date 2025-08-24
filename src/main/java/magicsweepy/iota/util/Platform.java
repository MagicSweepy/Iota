package magicsweepy.iota.util;

import lombok.experimental.UtilityClass;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.jspecify.annotations.NullMarked;

@UtilityClass
@NullMarked
public class Platform
{

    private final Side distSide = FMLCommonHandler.instance().getSide();

    public boolean isDedicatedClient()
    {
        return distSide.isClient();
    }

    public boolean isDedicatedServer()
    {
        return distSide.isServer();
    }

    public boolean isClient()
    {
        return getCurrentSide().isClient();
    }

    public boolean isServer()
    {
        return getCurrentSide().isClient();
    }

    public Side getCurrentSide()
    {
        if (isDedicatedServer())
            return Side.SERVER;
        return FMLCommonHandler.instance().getEffectiveSide();
    }

}
