package net.ommina.wallpapercraftwalls.items;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.ommina.wallpapercraftwalls.WallpapercraftWalls;
import net.ommina.wallpapercraftwalls.blocks.ModBlocks;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.MOD )
@ObjectHolder( WallpapercraftWalls.MODID )
public class ModItems {

    @SubscribeEvent
    public static void registerItems( final RegistryEvent.Register<Item> event ) {

        ModBlocks.WALLS_BLOCKS.keySet().stream().sorted().forEachOrdered( s ->
             event.getRegistry().register( new WallItem( ModBlocks.WALLS_BLOCKS.get( s ), new Item.Properties().group( WallpapercraftWalls.WALLS_TAB ) ).setRegistryName( s ) )
        );

    }

    @Nullable
    public static WallItem get( final String pattern, final String colour, final int suffix ) {
        return (WallItem) ForgeRegistries.ITEMS.getValue( WallpapercraftWalls.getId( pattern + colour + "-" + suffix ) );
    }

}
