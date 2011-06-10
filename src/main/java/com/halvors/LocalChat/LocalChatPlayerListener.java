/*
 * Copyright (C) 2011 halvors <halvors@skymiastudios.com>
 *
 * This file is part of LocalChat.
 *
 * LocalChat is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LocalChat is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LocalChat.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.halvors.LocalChat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.halvors.LocalChat.group.GroupManager;
import com.halvors.LocalChat.user.UserManager;
import com.halvors.LocalChat.util.ConfigManager;

public class LocalChatPlayerListener extends PlayerListener {
    private final LocalChat plugin;

    private final ConfigManager configManager;
    private final GroupManager groupManager;
    private final UserManager userManager;
    
    public LocalChatPlayerListener(LocalChat plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.groupManager = plugin.getGroupManager();
        this.userManager = plugin.getUserManager();
    }
    
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        try {
            configManager.loadPlayer(player.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        
        try {
            configManager.savePlayer(player.getName());
            userManager.removeUser(userManager.getUser(player.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onPlayerChat(PlayerChatEvent event) {
        if (!event.isCancelled()) {
            Player player = event.getPlayer();
            
            if (groupManager.hasGroup(userManager.getUser(player.getName()).getGroup().getName())) {
                double distance = userManager.getUser(player.getName()).getGroup().getDistance();
                
                if (distance > 0) {
                    Player[] players = player.getServer().getOnlinePlayers();
                    
                    for (int i = 0; i < players.length; i++) {
                        Player player1 = players[i];
                        double playerDistance = player.getLocation().toVector().distance(player1.getLocation().toVector());
                        
                        if ((playerDistance <= distance) || (plugin.getUserManager().getUser(player1.getName()).isSpying())) {
                            player1.sendMessage(String.format(event.getFormat(), player1.getDisplayName(), event.getMessage() + " - Test"));
                        }
                    }
                    
                    event.setCancelled(true);
                } else if (distance < 0) {
                    player.sendMessage(ChatColor.RED + configManager.You_are_muted);
                    event.setCancelled(true);
                }
            }
        }
    }
}