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

package com.halvors.LocalChat.user;

import com.halvors.LocalChat.LocalChat;
import com.halvors.LocalChat.group.Group;

public class User {
	private final LocalChat plugin;
	
	private String name;
	private Group group;
	private String format;
	private boolean isMuted;
	private boolean isSpying;
	
	public User(LocalChat instance) {
		plugin = instance;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	public String getFormat() {
		return format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	public boolean isMuted() {
		return isMuted;
	}
	
	public void setMuted(boolean isMuted) {
		this.isMuted = isMuted;
	}
	
	public boolean isSpying() {
		return isSpying;
	}
	
	public void setSpying(boolean isSpying) {
		this.isSpying = isSpying;
	}
	
	public boolean inGroup() {
		if (plugin.getGroupManager().hasGroup(group.getName())) {
			return true;
		}
		
		return false;
	}
}