package com.GreatGuardianHider;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface GreatGuardianHiderConfig extends Config
{
	@ConfigItem(
			position = 9,
			keyName = "hideNPCs",
			name = "Hide NPCs",
			description = "Configures whether or not NPCs are hidden"
	)
	default boolean hideNPCs()
	{
		return false;
	}
}
