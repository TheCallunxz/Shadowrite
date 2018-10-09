package net.thecallunxz.shadowrite.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelShadowEntity extends ModelBiped
{
    public ModelShadowEntity()
    {
        this(0.0F, false);
    }

    public ModelShadowEntity(float modelSize, boolean child)
    {
        super(modelSize, 0.0F, 64, child ? 32 : 64);
    }

}