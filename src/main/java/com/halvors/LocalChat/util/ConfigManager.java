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

package com.halvors.LocalChat.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.util.config.Configuration;

import com.halvors.LocalChat.LocalChat;
import com.halvors.LocalChat.group.Group;
import com.halvors.LocalChat.group.GroupManager;
import com.halvors.LocalChat.user.User;
import com.halvors.LocalChat.user.UserManager;

public class ConfigManager {
	private final LocalChat plugin;
	
	private UserManager userManager;
	private GroupManager groupManager;
	
	private File configFile;
//	private File groupsConfigFile;
	private File usersConfigFolder;
    
 	// Messages
	
	// Join
    public String You_are_now_in_group;
    public String You_are_no_longer_in_a_group;
    
    // Status
    public String Your_current_group_is;
    public String You_are_not_in_a_group;
    
    // Player status
    public String Player_current_group_is;
    public String Player_is_not_in_a_group;
    
    // Spying
    public String You_are_now_spying;
    public String You_are_no_longer_spying;
    
    // Set
    public String Player_group_set_to;
    
    // Does not exist messages
    public String Group_does_not_exist;
    public String Player_does_not_exist;
    public String World_does_not_exist;
    
    // Create
    public String Group_succesfully_created;
    public String Group_already_exists;
    
    // Muted
    public String You_are_muted;
    
    // Reload
    public String Reload_complete;
	
	public ConfigManager(final LocalChat plugin) {
		this.plugin = plugin;
		
		groupManager = plugin.getGroupManager();
		
	    configFile = new File(plugin.getDataFolder(), "config.yml");
//	    groupsConfigFile = new File(plugin.getDataFolder(), "groups.yml");
	    
	    usersConfigFolder = new File(plugin.getDataFolder(), "users/");
	}
	
	// Load configuration
	public void load() {
		checkConfig(configFile, "config.yml");
		
        Configuration config = new Configuration(configFile);
        config.load();
        
        loadGlobals(config);
        loadGroups(config);
    }

	// Save configuration
	public void save() {
		Configuration config = new Configuration(configFile);
		
		saveGlobals(config);
		saveGroups(config);
		
		config.save();
	}
	
	// Reload configuration
	public void reload() {
        load();
    }
	
	private void checkConfig(File config, String defaultName) {
        if (!config.exists()) {
            try {
                config.getParentFile().mkdir();
                config.createNewFile();
                OutputStream output = new FileOutputStream(config, false);
                InputStream input = ConfigManager.class.getResourceAsStream(defaultName);
                byte[] buf = new byte[8192];
                while (true) {
                    int length = input.read(buf);
                    if (length < 0) {
                        break;
                    }
                    output.write(buf, 0, length);
                }
                input.close();
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
	private void loadGlobals(Configuration config) {
        // Messages
		
        // Join
		You_are_now_in_group = config.getString("Messages.You_are_now_in_group", You_are_now_in_group);
		You_are_no_longer_in_a_group = config.getString("You_are_no_longer_in_a_group", You_are_no_longer_in_a_group);
		
		// Status
		Your_current_group_is = config.getString("Messages.Your_current_group_is", Your_current_group_is);
		You_are_not_in_a_group = config.getString("Messages.You_are_not_in_a_group", You_are_not_in_a_group);
		
		// Player status
		Player_current_group_is = config.getString("Messages.Player_current_group_is", Player_current_group_is);
		Player_is_not_in_a_group = config.getString("Messages.Player_is_not_in_a_group", Player_is_not_in_a_group);
		
		// Spying
		You_are_now_spying = config.getString("Messages.You_are_now_spying", You_are_now_spying);
		You_are_no_longer_spying = config.getString("Messages.You_are_no_longer_spying", You_are_no_longer_spying);
		
		// Set
		Player_group_set_to = config.getString("Messages.Player_group_set_to", Player_group_set_to);
		
		// Does not exist messages
		Player_does_not_exist = config.getString("Messages.Player_does_not_exist", Player_does_not_exist);
		Group_does_not_exist = config.getString("Messages.Group_does_not_exist", Group_does_not_exist);
		World_does_not_exist = config.getString("Messages.World_does_not_exist", World_does_not_exist);
		
		// Create
		Group_succesfully_created = config.getString("Messages.Group_succesfully_created", Group_succesfully_created);
		Group_already_exists = config.getString("Messages.Group_already_exists", Group_already_exists);
		
		// Muted
		You_are_muted = config.getString("Messages.You_are_muted", You_are_muted);
		
		// Reload
		Reload_complete = config.getString("Messages.Reload_complete", Reload_complete);
	}
	
	private void loadGroups(Configuration config) {
		List<String> keyList = config.getKeys("Groups");
		List<Group> groups = new ArrayList<Group>();
		
		try {
			for (int i = 0; i < keyList.size(); i++) {
				String key = keyList.get(i);
				String root = "Groups." + key + ".";
			
				Group g = new Group(plugin);
				g.setName(keyList.get(i));
				g.setDefault(config.getBoolean(root + "Default", false));
				g.setDistance(config.getInt(root + "Distance", 0));
//				g.setPlayers(config.getStringList(root + "Players", null));
//				g.setWorlds(config.getStringList(root + "Worlds", null));

				groups.add(g);
			}
			
			groupManager.setGroups(groups);
		} catch (Exception e) {
		}
	}
	
	public void loadPlayer(String name) {
        File userConfigFile = new File(usersConfigFolder, name + ".yml");
        
        try {
        	Configuration config = new Configuration(userConfigFile);
        	config.load();
        	
        	User u = new User(plugin);
        	u.setName(name);
        	u.setGroup(groupManager.getGroup(config.getString("Group", groupManager.getDefaultGroup().getName())));
        	u.setMuted(config.getBoolean("Muted", false));
        	u.setSpying(config.getBoolean("Spying", false));
            
        	userManager.addUser(u);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveGlobals(Configuration config) {
        // Messages
    	
    	// Join
        config.setProperty("Messages.You_are_now_in_group", You_are_now_in_group);
        config.setProperty("Messages.You_are_no_longer_in_a_group", You_are_no_longer_in_a_group);
        
        // Status
        config.setProperty("Messages.Your_current_group_is", Your_current_group_is);
        config.setProperty("Messages.You_are_not_in_a_group", You_are_not_in_a_group);
        
        // Player status
        config.setProperty("Messages.Player_current_group_is", Player_current_group_is);
        config.setProperty("Messages.Player_is_not_in_a_group", Player_is_not_in_a_group);
        
        // Spying
        config.setProperty("Messages.You_are_now_spying", You_are_now_spying);
        config.setProperty("Messages.You_are_no_longer_spying", You_are_no_longer_spying);
        
        // Set
        config.setProperty("Messages.Player_group_set_to", Player_group_set_to);
        
        // Does not exist messages
        config.setProperty("Messages.Player_does_not_exist", Player_does_not_exist);
        config.setProperty("Messages.Group_does_not_exist", Group_does_not_exist);
        config.setProperty("Messages.World_does_not_exist", World_does_not_exist);
        
        // Create
        config.setProperty("Messages.Group_succesfully_created", Group_succesfully_created);
        config.setProperty("Messages.Group_already_exists", Group_already_exists);
        
        // Muted
        config.setProperty("Messages.You_are_muted", You_are_muted);
        
        // Reload
        config.setProperty("Messages.Reload_complete", Reload_complete);
        
        config.save();
    }
    
    private void saveGroups(Configuration config) {
    	List<Group> groups = plugin.getGroupManager().getGroups();
    	
    	for (int i = 0; i < groups.size(); i++) {
    		Group g = groups.get(i);
    		String root = "Groups." + g.getName() + ".";
    		
    		config.setProperty(root + "Default", g.getDefault());
    		config.setProperty(root + "Distance", g.getDistance());
//    		config.setProperty(root + "Players", g.getPlayers());
//    		config.setProperty(root + "Worlds", g.getWorlds());
    	}
    	
    	config.save();
    }
    
    public void savePlayer(String name) {
    	File userConfigFile = new File(usersConfigFolder, name + ".yml");
    	
    	try {
            Configuration config = new Configuration(userConfigFile);
            
            User u = userManager.getUser(name);
            
            if (u != null) {
            	config.setProperty("Group", userManager.getUser(name).getGroup().getName());
            	config.setProperty("Muted", u.isMuted());
            	config.setProperty("Spying", u.isSpying());
            }
            
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}