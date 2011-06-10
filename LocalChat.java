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

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.halvors.LocalChat.group.GroupManager;
import com.halvors.LocalChat.user.UserManager;
import com.halvors.LocalChat.util.ConfigManager;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class LocalChat extends JavaPlugin {
	public static String name;
	public static String version;
	
	private final Logger log = Logger.getLogger("Minecraft");
	
	private PluginManager pm;
	private PluginDescriptionFile pdfFile;
    
    private final ConfigManager configManager = new ConfigManager(this);
    private final GroupManager groupManager = new GroupManager(this);
    private final UserManager userManager = new UserManager(this);
    
    private final LocalChatPlayerListener playerListener = new LocalChatPlayerListener(this);

    public static PermissionHandler Permissions;
    
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    
    public LocalChat() {
    	
    }
    
    public void onEnable() {
    	pm = getServer().getPluginManager();
    	pdfFile = this.getDescription();
        
        // Load name and version from pdfFile
        name = pdfFile.getName();
        version = pdfFile.getVersion();

        // Load configuration
        try {
            configManager.load();
        } catch (Exception e) {
            e.printStackTrace();
            log(Level.WARNING, "Error encountered while loading data. Disabling " + name + ".");
            this.getServer().getPluginManager().disablePlugin(this);
            
            return;
        }

        for (Player player : this.getServer().getOnlinePlayers()) {
        	configManager.loadPlayer(player.getName());
        }
        
        try {
            configManager.save();
        } catch (Exception e) {
            e.printStackTrace();
            log(Level.WARNING, "Error encountered while saving data. " + name + ".");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Register our events
        pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Normal, this);
        
        // Register our commands
        this.getCommand("lc").setExecutor(new LocalChatCommandExecutor(this));
        
        log(Level.INFO, "version " + version + " is enabled!");
        
        setupPermissions();
    }
    
    public void onDisable() {
    	try {
            for (Player player : this.getServer().getOnlinePlayers()) {
            	configManager.savePlayer(player.getName());
            }
            
            configManager.save();
        } catch (Exception e) {
            e.printStackTrace();
            log(Level.WARNING, "Error encountered while saving data. " + name + ".");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
    	
        log(Level.INFO, "Plugin disabled!");
    }
    
    private void setupPermissions() {
    	Plugin permissions = this.getServer().getPluginManager().getPlugin("Permissions");

        if (Permissions == null) {
        	if (permissions != null) {
            	Permissions = ((Permissions)permissions).getHandler();
            } else {
            	log(Level.INFO, "Permission system not detected, defaulting to OP");
            }
        }
    }

    public static boolean hasPermissions(Player p, String s) {
        if (Permissions != null) {
            return Permissions.has(p, s);
        } else {
            return p.isOp();
        }
    }
    
    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
    
    public void log(Level level, String msg) {
        this.log.log(level, "[" + name + "] " + msg);
    }
    
    public ConfigManager getConfigManager() {
    	return configManager;
    }
    
    public GroupManager getGroupManager() {
    	return groupManager;
    }
    
    public UserManager getUserManager() {
    	return userManager;
    }
}