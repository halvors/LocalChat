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

import com.halvors.LocalChat.LocalChat;
    
public class GroupManager {
//    private final LocalChat plugin;
    
    private final List<Group> groups;
    
    public GroupManager(final LocalChat plugin) {
//        this.plugin = plugin;
    	this.groups = new ArrayList<Group>();
    }
    
    public void addGroup(Group group) {
    	if (groups.contains(group)) {
    		groups.remove(group);
    	}
    	
        groups.add(group);
    }

    public void removeGroup(Group group) {
    	if (groups.contains(group)) {
    		groups.remove(group);
    	}
    }
    
    public boolean hasGroup(String group) {
    	for (Group g : groups) {
            if (getGroup(group) != null) {
                return true;
            }
        }
        
        return false;
    }
    
    public Group getGroup(String name) {
    	for (Group g : groups) {

            if (g.getName().equalsIgnoreCase(name)) {
                return g;
            }
        }
        
        return null;
    }
    
    public List<Group> getGroups() {
    	return groups;
    }
    
    public Group getDefaultGroup() {
        for (Group g : groups) {
            if (g.getDefault()) {
                return g;
            }
        }
        
        return null;
    }
}
