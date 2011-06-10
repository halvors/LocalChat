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
    
    private List<User> users = new ArrayList<User>();
    
    public UserManager(final LocalChat plugin) {
//        this.plugin = plugin;
    }
    
    public List<User> getUsers() {
        return users;
    }
    
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    public User getUser(String name) {
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            
            if (u.getName().equalsIgnoreCase(name)) {
                return u;
            }
        }
        
        return null;
    }
    
    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }
}
