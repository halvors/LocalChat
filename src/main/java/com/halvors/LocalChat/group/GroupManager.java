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
//	private final LocalChat plugin;
	
	private List<Group> groups = new ArrayList<Group>();
	
	public GroupManager(final LocalChat plugin) {
//		this.plugin = plugin;
	}
	
	public List<Group> getGroups() {
		return groups;
	}
	
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	public Group getGroup(String name) {
		for (int i = 0; i < groups.size(); i++) {
			Group g = groups.get(i);
			
			if (g.getName().equalsIgnoreCase(name)) {
				return g;
			}
		}
		
		return null;
	}
	
	public void addGroup(Group group) {
        groups.add(group);
    }

	public void removeGroup(Group group) {
		groups.remove(group);
	}
	
	public boolean hasGroup(String group) {
		for (int i = 0; i < groups.size(); i++) {
			String name = groups.get(i).getName();
			
			if (name.equalsIgnoreCase(group)) {
				return true;
			}
		}
		
		return false;
	}
	
	public Group getDefaultGroup() {
		for (int i = 0; i < groups.size(); i++) {
			Group g = groups.get(i);
			
			if (g.getDefault()) {
				return g;
			}
		}
		
		return null;
	}
}