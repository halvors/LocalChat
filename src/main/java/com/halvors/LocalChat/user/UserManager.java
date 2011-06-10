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

import java.util.ArrayList;
import java.util.List;

import com.halvors.LocalChat.LocalChat;

public class UserManager {
//    private final LocalChat plugin;
    
    private final List<User> users;
    
    public UserManager(final LocalChat plugin) {
//        this.plugin = plugin;
    	this.users = new ArrayList<User>();
    }
    
    public void addUser(User user) {
    	if (users.contains(user)) {
    		users.remove(user);
    	}
    	
        users.add(user);
    }

    public void removeUser(User user) {
    	if (users.contains(user)) {
    		users.remove(user);
    	}
    }
    
    public boolean hasUser(String name) {
    	if (getUser(name) != null) {
    		return true;
    	}
    	
    	return false;
    }
    
    public User getUser(String name) {
        for (User u : users) {
            if (u.getName().equalsIgnoreCase(name)) {
                return u;
            }
        }
        
        return null;
    }
}
