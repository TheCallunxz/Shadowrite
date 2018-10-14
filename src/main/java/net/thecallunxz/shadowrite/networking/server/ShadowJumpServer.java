package net.thecallunxz.shadowrite.networking.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.thecallunxz.shadowrite.networking.ShadowritePacket;
import net.thecallunxz.shadowrite.networking.client.ShadowJumpClient;

public class ShadowJumpServer implements IMessage {
	
	private int currentSlot;


	public ShadowJumpServer() { }
	
	public ShadowJumpServer(int currentSlot){
    	this.currentSlot = currentSlot;
    }
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.currentSlot = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.currentSlot);
	}


	public static class Handler implements IMessageHandler<ShadowJumpServer, IMessage> {
		@Override
		public IMessage onMessage(final ShadowJumpServer message, final MessageContext ctx) {
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.world;
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					EntityPlayerMP player = ctx.getServerHandler().player;
					ItemStack itemJump = player.inventory.getStackInSlot(message.currentSlot);
					itemJump.setItemDamage(999);
					player.onGround = false;
					player.motionY = 0.6D;
					player.fallDistance = -1;
					
					ShadowritePacket.INSTANCE.sendToAll(new ShadowJumpClient(player.posX - 0.5D, player.posY - 0.5D, player.posZ - 0.5D));
					
				}
			});
			return null;
		}
	}
}
