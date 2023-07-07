package com.GreatGuardianHider;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class GreatGuardianHiderPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(GreatGuardianHider.class);
		RuneLite.main(args);
	}
}