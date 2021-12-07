package net.ommina.wallpapercraftwalls.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.ommina.wallpapercraft.blocks.IDecorativeBlock;
import net.ommina.wallpapercraft.blocks.InWorldHelper;
import net.ommina.wallpapercraft.items.ModItems;
import net.ommina.wallpapercraft.items.PressColour;
import net.ommina.wallpapercraft.items.PressVariant;
import net.ommina.wallpapercraft.sounds.ModSoundType;

import javax.annotation.Nullable;

public class WallpaperWallBlock extends WallBlock implements IDecorativeBlock {

    private static final String POSTFIX = "";

    private final String pattern;
    private final String colour;
    private final String suffix;

    public WallpaperWallBlock( final String pattern, final String colour, final int suffix, Properties properties ) {
        super( properties );

        this.pattern = pattern;
        this.colour = colour;
        this.suffix = "-" + suffix;

    }

    //region Overrides
    @Override
    public void attack( final BlockState state, final Level world, final BlockPos pos, final Player player ) {
        onBlockClicked( state, world, pos, player );
    }

    @Override
    public String getPostfix() {
        return POSTFIX;
    }

    @Override
    public String getNameForRegistry() {
        return this.pattern + this.colour + this.suffix;
    }

    @Override
    public String getPattern() {
        return this.pattern;
    }

    @Override
    public String getColour() {
        return this.colour;
    }

    @Override
    public String getSuffix() {
        return this.suffix;
    }

    @Override
    public SoundType getSoundType( final BlockState state, final LevelReader world, final BlockPos pos, @Nullable final Entity entity ) {

        if ( !(entity instanceof Player) )
            return SoundType.STONE;

        final Player player = (Player) entity;

        if ( player.getMainHandItem().isEmpty() )
            return SoundType.STONE;

        if ( player.getMainHandItem().getItem() == ModItems.PAINTBRUSH || player.getMainHandItem().getItem() instanceof PressColour || player.getMainHandItem().getItem() instanceof PressVariant )
            return ModSoundType.BLOCK_CHANGE;

        return SoundType.STONE;

    }
//endregion Overrides

    public void onBlockClicked( final BlockState state, final Level level, final BlockPos pos, final Player player ) {

        Block block = Blocks.AIR;

        if ( player.getMainHandItem().getItem() == ModItems.PAINTBRUSH )
            block = InWorldHelper.getIncrementedBlockColour( this );
        else if ( player.getMainHandItem().getItem() instanceof PressColour )
            block = InWorldHelper.getBlockFromColourPress( this, (PressColour) player.getMainHandItem().getItem() );
        else if ( player.getMainHandItem().getItem() instanceof PressVariant )
            block = InWorldHelper.getBlockFromVariantPress( this, (PressVariant) player.getMainHandItem().getItem() );

        if ( block == Blocks.AIR )
            return;

        BlockState newState = block.defaultBlockState()
             .setValue( WallBlock.UP, state.getValue( WallBlock.UP ) )
             .setValue( WallBlock.NORTH_WALL, state.getValue( WallBlock.NORTH_WALL ) )
             .setValue( WallBlock.EAST_WALL, state.getValue( WallBlock.EAST_WALL ) )
             .setValue( WallBlock.SOUTH_WALL, state.getValue( WallBlock.SOUTH_WALL ) )
             .setValue( WallBlock.WEST_WALL, state.getValue( WallBlock.WEST_WALL ) )
             .setValue( WallBlock.WATERLOGGED, state.getValue( WallBlock.WATERLOGGED ) );

        if ( !level.isClientSide )
            level.setBlock( pos, newState, 3 );

    }

}
