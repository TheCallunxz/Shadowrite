package net.thecallunxz.shadowrite.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerSpiderEyes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.thecallunxz.shadowrite.entities.EntityShadowWarrior;
import net.thecallunxz.shadowrite.models.ModelShadowEntity;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

public class RenderShadowWarrior extends RenderLiving<EntityShadowWarrior> {
	public static final Factory FACTORY = new Factory();
	
	private static final ResourceLocation mobTexture = new ResourceLocation("shadowrite:textures/entity/mobs/shadowwarrior.png");

	public RenderShadowWarrior(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelShadowEntity(), 0F);
		this.addLayer(new LayerCustomHeldItem(this));
		LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelShadowEntity(0.5F, true);
                this.modelArmor = new ModelShadowEntity(1.0F, true);
            }
        };
        this.addLayer(layerbipedarmor);
	}
	
	public ModelShadowEntity getMainModel() {
		return (ModelShadowEntity) super.getMainModel();
	}

	@Override
	@Nonnull
	protected ResourceLocation getEntityTexture(@Nonnull EntityShadowWarrior entity) {
		return mobTexture;
	}
	
	public void doRender(EntityShadowWarrior entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.setModelVisibilities(entity);
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
	}
	
	private void setModelVisibilities(EntityShadowWarrior entity) {
		ModelShadowEntity modelplayer = this.getMainModel();
		if(entity.isShieldOut()) {
			modelplayer.leftArmPose = ModelBiped.ArmPose.BLOCK;
		}else{
			modelplayer.leftArmPose = ModelBiped.ArmPose.EMPTY;
		}
	}
	
	protected float getDeathMaxRotation(EntityShadowWarrior entityLivingBaseIn)
    {
        return 0.0F;
    }

	public static class Factory implements IRenderFactory<EntityShadowWarrior> {
		@Override
		public Render<? super EntityShadowWarrior> createRenderFor(RenderManager manager) {
			return new RenderShadowWarrior(manager);
		}
	}
}