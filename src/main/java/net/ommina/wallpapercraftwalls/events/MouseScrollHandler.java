package net.ommina.wallpapercraftwalls.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ommina.wallpapercraft.util.MathUtil;
import net.ommina.wallpapercraftwalls.WallpapercraftWalls;
import net.ommina.wallpapercraftwalls.blocks.ModBlocks;
import net.ommina.wallpapercraftwalls.blocks.WallpaperWallBlock;
import net.ommina.wallpapercraftwalls.items.WallItem;
import net.ommina.wallpapercraftwalls.network.Network;
import net.ommina.wallpapercraftwalls.network.VariantScrollRequest;

@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT )
public class MouseScrollHandler {

    @SubscribeEvent
    public static void onScroll( InputEvent.MouseScrollEvent event ) {

        final LocalPlayer player = Minecraft.getInstance().player;
        final ItemStack held = player.getMainHandItem();

        if ( !held.isEmpty() && held.getItem() instanceof WallItem && player.isCrouching() && held.getItem().getRegistryName().getNamespace().equals( WallpapercraftWalls.MODID ) ) {

            final int delta = MathUtil.clamp( (int) Math.round( event.getScrollDelta() ), -1, 1 );

            cycleVariant( held, delta );

            event.setCanceled( true );

        }

    }

    private static void cycleVariant( ItemStack stack, int delta ) {

        final WallpaperWallBlock block = ModBlocks.WALLS_BLOCKS.get( stack.getItem().getRegistryName().getPath() );

        if ( block != null )
            Network.channel.sendToServer( new VariantScrollRequest( delta ) );

    }

}