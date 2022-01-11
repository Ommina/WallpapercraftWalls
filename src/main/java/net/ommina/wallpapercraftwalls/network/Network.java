package net.ommina.wallpapercraftwalls.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.ommina.wallpapercraftwalls.WallpapercraftWalls;

public class Network {

    private static final ResourceLocation NAME = WallpapercraftWalls.getId( "network" );
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel channel;
    private static int channelId = 0;

    static {

        channel = NetworkRegistry.ChannelBuilder.named( NAME )
             .clientAcceptedVersions( PROTOCOL_VERSION::equals )
             .serverAcceptedVersions( PROTOCOL_VERSION::equals )
             .networkProtocolVersion( () -> PROTOCOL_VERSION )
             .simpleChannel();

        channel.messageBuilder( VariantScrollRequest.class, channelId++ )
             .decoder( VariantScrollRequest::fromBytes )
             .encoder( VariantScrollRequest::toBytes )
             .consumer( VariantScrollRequest::handle )
             .add();

    }

    private Network() {
    }

    public static void init() {
        // Hi!
    }

}
