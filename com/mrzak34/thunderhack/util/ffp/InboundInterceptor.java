//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.ffp;

import net.minecraftforge.fml.relauncher.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import net.minecraft.network.*;

@SideOnly(Side.CLIENT)
public class InboundInterceptor extends NettyPacketDecoder
{
    private final EnumPacketDirection direction;
    private final NetworkHandler handler;
    private boolean isPlay;
    
    public InboundInterceptor(final NetworkHandler handler, final EnumPacketDirection direction) {
        super(direction);
        this.handler = handler;
        this.direction = direction;
        this.isPlay = false;
    }
    
    protected void decode(final ChannelHandlerContext context, final ByteBuf in, final List<Object> out) throws Exception {
        if (in.readableBytes() != 0) {
            final int start_index = in.readerIndex();
            super.decode(context, in, (List)out);
            if (!this.isPlay) {
                final EnumConnectionState state = (EnumConnectionState)context.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get();
                this.isPlay = (state == EnumConnectionState.PLAY);
            }
            if (this.isPlay && out.size() > 0) {
                Packet packet = out.get(0);
                final int id = ((EnumConnectionState)context.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get()).getPacketId(this.direction, packet);
                final int end_index = in.readerIndex();
                in.readerIndex(start_index);
                packet = this.handler.packetReceived(this.direction, id, (Packet<?>)packet, in);
                in.readerIndex(end_index);
                if (packet == null) {
                    out.clear();
                }
                else {
                    out.set(0, packet);
                }
            }
        }
    }
}
