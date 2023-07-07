/*
 * Copyright (c) 2018, Lotto <https://github.com/devLotto>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
		name = "Great Guardian Hider"
)
public class GreatGuardianHiderPlugin extends Plugin
{

	private final Hooks.RenderableDrawListener drawListener = this::shouldDraw;


	private boolean hideNPCs;

	@Inject
	private Client client;

	@Inject
	private Hooks hooks;



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
		if (inventory != null && (inventory.contains(ItemID.CATALYTIC_GUARDIAN_STONE) || inventory.contains(ItemID.ELEMENTAL_GUARDIAN_STONE)))
		{
			hideNPCs = true;
		}
		else
		{
			hideNPCs = false;
		}
	}

	@VisibleForTesting
	boolean shouldDraw(Renderable renderable, boolean Draw)
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

}
