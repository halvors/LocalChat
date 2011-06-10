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

package com.halvors.LocalChat.group;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.halvors.LocalChat.LocalChat;

public class Group {
	private final LocalChat plugin;
	
	private String name;
	private boolean isDefault;
	private String description;
	private int distance;
	
	private List<String> players;
	private List<String> worlds;
	
	public Group(LocalChat instance) {
		plugin = instance;
		
		players = new ArrayList<String>();
		worlds = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean getDefault() {
		return isDefault;
	}
	
	public void setDefault(boolean d) {
		this.isDefault = d;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public List<String> getPlayers() {
		return players;
	}
	
	public void setPlayers(List<String> players) {
		this.players = players;
	}
	
    public void addPlayer(String name) {
        if (!players.contains(name)) {
            players.add(name);
            
            for (int i = 0; i < players.size(); i++) {
            	String player = players.get(i);
            	
            	if (!name.equalsIgnoreCase(player)) {
            		plugin.getServer().broadcastMessage(ChatColor.GREEN + player + " joined the group!");
            	}
            }
        }
    }

    public void removePlayer(String name) {
        if (players.contains(name)) {
            players.remove(name);
            
            for (int i = 0; i < players.size(); i++) {
            	String player = players.get(i);
            	
            	if (!name.equalsIgnoreCase(player)) {
            		plugin.getServer().broadcastMessage(ChatColor.GREEN + player + " has left the group!");
            	}
            }
        }
    }
    
	public List<String> getWorlds() {
		return worlds;
	}
	
	public void setWorlds(List<String> worlds) {
		this.worlds = worlds;
	}
	
    public void addWorld(String name) {
        if (!worlds.contains(name)) {
        	worlds.add(name);
        }
    }

    public void removeWorld(String name) {
        if (players.contains(name)) {
            players.remove(name);
        }
    }
}