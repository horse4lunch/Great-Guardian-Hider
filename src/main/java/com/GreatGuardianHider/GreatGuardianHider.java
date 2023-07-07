package com.GreatGuardianHider;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.callback.Hooks;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
		name = "Great Guardian Hider"
)
public class GreatGuardianHider extends Plugin
{
	private final Hooks.RenderableDrawListener drawListener = this::shouldDraw;
	private boolean hideNPCs;

	@Inject
	private Client client;

	@Inject
	private Hooks hooks;

	@Inject
	private GreatGuardianHiderConfig config;

	@Override
	protected void startUp()
	{
		hooks.registerRenderableDrawListener(drawListener);
		hideNPCs = false;
	}

	@Override
	protected void shutDown()
	{
		hooks.unregisterRenderableDrawListener(drawListener);
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{
		final ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);

		if (inventory != null && (inventory.contains(ItemID.ROCK_HAMMER) || inventory.contains(ItemID.ELEMENTAL_GUARDIAN_STONE)) && config.hideNPCs())
		{
			hideNPCs = true;
		}
		else
		{
			hideNPCs = false;
		}
	}

	@VisibleForTesting
	boolean shouldDraw(Renderable renderable, boolean drawingUI)
	{
		if (renderable instanceof NPC)
		{
			NPC npc = (NPC) renderable;
			int npcIdToHide = 11403;
			if (!hideNPCs && npc.getId() == npcIdToHide)
			{
				return false;
			}
		}

		return true;
	}

	@Provides
	GreatGuardianHiderConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(GreatGuardianHiderConfig.class);
	}
}
