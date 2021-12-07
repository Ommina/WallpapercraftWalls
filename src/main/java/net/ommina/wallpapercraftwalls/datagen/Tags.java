package net.ommina.wallpapercraftwalls.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.ommina.wallpapercraft.blocks.IDecorativeBlock;
import net.ommina.wallpapercraftwalls.WallpapercraftWalls;
import net.ommina.wallpapercraftwalls.blocks.ModBlocks;

public class Tags extends BlockTagsProvider {

    public Tags( DataGenerator generator, ExistingFileHelper helper ) {
        super( generator, WallpapercraftWalls.MODID, helper );
    }

    //region Overrides
    @Override
    protected void addTags() {

        for ( final IDecorativeBlock b : ModBlocks.WALLS_BLOCKS.values() ) {

            final Block block = ForgeRegistries.BLOCKS.getValue( WallpapercraftWalls.getId( b.getNameForRegistry() ) );

            assert block != null;
            final Material m = block.defaultBlockState().getMaterial();

            if ( m == Material.STONE || m == Material.GLASS || m == Material.CLAY )
                tag( BlockTags.MINEABLE_WITH_PICKAXE ).add( block );
            else if ( m == Material.WOOD || m == Material.WOOL )
                tag( BlockTags.MINEABLE_WITH_AXE ).add( block );

        }

    }

    @Override
    public String getName() {
        return "Wallpapercraft Tags";
    }

}