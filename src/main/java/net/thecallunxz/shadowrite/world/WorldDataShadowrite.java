package net.thecallunxz.shadowrite.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

public class WorldDataShadowrite extends WorldSavedData {
	private static final String IDENTIFIER = "thecallunxzshadowrite";

	private boolean daylightReset = true;
	private boolean shadowsReleased = false;
	
	public WorldDataShadowrite() {
		super(IDENTIFIER);
	}

	public WorldDataShadowrite(String identifier) {
		super(identifier);
	}

	public static WorldDataShadowrite get(World world) {
		WorldDataShadowrite data = (WorldDataShadowrite) world.loadData(WorldDataShadowrite.class, IDENTIFIER);
		if (data == null) {
			data = new WorldDataShadowrite();
			world.setData(IDENTIFIER, data);
		}
		return data;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		daylightReset = nbt.getBoolean("daylightReset");
		shadowsReleased = nbt.getBoolean("shadowsReleased");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("daylightReset", daylightReset);
		nbt.setBoolean("shadowsReleased", shadowsReleased);
		return nbt;
	}
	
	public boolean isShadowsReleased() {
		return shadowsReleased;
	}
	
	public void setShadowsReleased(boolean shadowsReleased) {
		this.shadowsReleased = shadowsReleased;
		markDirty();
	}

	public boolean hasDaylightReset() {
		return daylightReset;
	}

	public void setDaylightReset(boolean inEvent) {
		this.daylightReset = inEvent;
		markDirty();
	}
}
