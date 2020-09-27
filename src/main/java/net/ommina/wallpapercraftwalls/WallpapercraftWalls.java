package net.ommina.wallpapercraftwalls;

import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.ommina.wallpapercraft.CreativeTab;
import net.ommina.wallpapercraftwalls.network.Network;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.ommina.wallpapercraftwalls.WallpapercraftWalls.MODID;


@Mod( MODID )
public class WallpapercraftWalls {

    public static final String MODID = "wallpapercraftwalls";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final ItemGroup WALLS_TAB = new CreativeTab();

    public WallpapercraftWalls() {

        MinecraftForge.EVENT_BUS.register( this );

        FMLJavaModLoadingContext.get().getModEventBus().addListener( this::setup );

    }

    public static ResourceLocation getId( String path ) {
        return new ResourceLocation( MODID, path );
    }

    private void setup( final FMLCommonSetupEvent event ) {
        Network.init();
    }

}
