package com.gamerking195.dev.thirst.listener;

import com.gamerking195.dev.thirst.Thirst;
import com.gamerking195.dev.thirst.ThirstManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import com.gamerking195.dev.thirst.config.YAMLConfig.ThirstItem;

public class PlayerItemConsumeListener 
implements Listener
{
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent event)
	{		
		for (String itemString : Thirst.getInstance().getYAMLConfig().thirstQuenchingItems)
		{
			ThirstItem item = Thirst.getInstance().getYAMLConfig().new ThirstItem(itemString);
			
			ItemStack is = new ItemStack(Material.valueOf(item.getItem()), 1);
			
			//casting due to spigot api subject to change.
			is.setData(new MaterialData(is.getType(), (byte) item.getMetaData()));

			int quenchPercent = item.getQuenchPercent();
			
			if (is.getType() == event.getItem().getType() && event.getItem().getData().toString().equals(is.getData().toString())) {
                if (Thirst.getInstance().getYAMLConfig().itemConsumption || ThirstManager.getThirst().getPlayerThirst(event.getPlayer()) != 100)
                    ThirstManager.getThirst().setThirst(event.getPlayer(), ThirstManager.getThirst().getPlayerThirst(event.getPlayer()) + quenchPercent);
                else {
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Thirst.getInstance().getYAMLConfig().itemConsumeMessage.replace("%player%", event.getPlayer().getName())));
                    event.setCancelled(true);
                }
            }
		}
	}
}
