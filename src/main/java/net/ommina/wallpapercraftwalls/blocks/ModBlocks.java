package net.ommina.wallpapercraftwalls.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.ommina.wallpapercraft.Wallpapercraft;
import net.ommina.wallpapercraftwalls.WallpapercraftWalls;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.MOD )
public class ModBlocks {

    public static final String[] COLOURS = { "blue", "brown", "cyan", "gray", "green", "purple", "red", "yellow" };
    public static final String[] WALLS = { "solid", "brick", "checkeredwool", "clay", "colouredbrick",
         "damask", "diagonallydotted", "dotted", "fancytiles", "floral", "rippled", "stonebrick", "striped", "woodplank", "wool" };

    public static final Map<String, WallpaperWallBlock> WALLS_BLOCKS = new HashMap<String, WallpaperWallBlock>();

    @SubscribeEvent
    public static void registerWalls( final RegistryEvent.Register<Block> event ) {

        for ( String pattern : WALLS ) {

            for ( String colour : COLOURS ) {

                final int suffixCount = colour.equals( "cyan" ) ? 9 : 14;

                for ( int suffix = 0; suffix <= suffixCount; suffix++ ) {

                    String name = getName( pattern, colour, suffix );

                    Block sourceBlock = ForgeRegistries.BLOCKS.getValue( Wallpapercraft.getId( name ) );

                    if ( sourceBlock == null || sourceBlock == Blocks.AIR )
                        WallpapercraftWalls.LOGGER.warn( "Source block not found: " + name + " -- unable to create walls for it.  Panic!  This shouldn't happen!" );
                    else {

                        WallpaperWallBlock block = new WallpaperWallBlock( pattern, colour, suffix, Block.Properties.copy( sourceBlock ) );
                        block.setRegistryName( WallpapercraftWalls.getId( name ) );

                        event.getRegistry().register( block );
                        WALLS_BLOCKS.put( name, block );

                    }

                }
            }

        }

    }

    private static String getName( String pattern, String colour, int suffix ) {
        return pattern + colour + "-" + suffix;
    }

}
