package net.thecallunxz.shadowrite.util;

import net.minecraft.client.Minecraft;

public class ShadowClientUtil {

	public static boolean checkJumpWithoutSneak() {
		return  Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed();
	}
	
}
