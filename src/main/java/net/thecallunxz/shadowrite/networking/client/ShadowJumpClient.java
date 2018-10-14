package net.thecallunxz.shadowrite.networking.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.thecallunxz.shadowrite.init.InitSounds;

public class ShadowJumpClient implements IMessage {
	
	private double posX;
	private double posY;
	private double posZ;


	public ShadowJumpClient() { }
	
	public ShadowJumpClient(double posX, double posY, double posZ){
    	this.posX = posX;
    	this.posY = posY;
    	this.posZ = posZ;
    }
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.posX = buf.readDouble();
		this.posY = buf.readDouble();
		this.posZ = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(this.posX);
		buf.writeDouble(this.posY);
		buf.writeDouble(this.posZ);
	}


	public static class Handler implements IMessageHandler<ShadowJumpClient, IMessage> {

        @Override
        public IMessage onMessage(final ShadowJumpClient message, MessageContext ctx) {
            IThreadListener mainThread = Minecraft.getMinecraft();
            mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                	EntityPlayer player = Minecraft.getMinecraft().player;
                	
                	for (int i = 0; i < 150; ++i)
				    {
				        double d0 = (double)message.posX + player.getEntityWorld().rand.nextDouble();
				        double d1 = (double)message.posY + player.getEntityWorld().rand.nextDouble();
				        double d2 = (double)message.posZ + player.getEntityWorld().rand.nextDouble();
				        player.getEntityWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, player.getEntityWorld().rand.nextDouble()/2 - 0.25, 0.0D, player.getEntityWorld().rand.nextDouble()/2 - 0.25);
				    }
                	
                	player.getEntityWorld().playSound((double)((float)message.posX + 0.5F), (double)((float)message.posY + 0.5F), (double)((float)message.posZ + 0.5F), InitSounds.shadowjump, SoundCategory.PLAYERS, 1.0F, 1.0F, false);
                	
                }
            });
            return null;
        }
    }
}
