package net.ommina.wallpapercraftwalls.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ommina.wallpapercraft.util.MathUtil;
import net.ommina.wallpapercraftwalls.blocks.ModBlocks;
import net.ommina.wallpapercraftwalls.blocks.WallpaperWallBlock;
import net.ommina.wallpapercraftwalls.items.WallItem;
import net.ommina.wallpapercraftwalls.network.Network;
import net.ommina.wallpapercraftwalls.network.VariantScrollRequest;

@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT )
public class MouseScrollHandler {

    @SubscribeEvent
    public static void onScroll( InputEvent.MouseScrollEvent event ) {

        final ClientPlayerEntity player = Minecraft.getInstance().player;
        final ItemStack held = player.getHeldItem( Hand.MAIN_HAND );

        if ( !held.isEmpty() && held.getItem() instanceof WallItem && player.isSneaking() ) {

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