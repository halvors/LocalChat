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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents a UserTable.
 * 
 * @author halvors
 */
@Entity()
@Table(name = "lc_user")
public class UserTable {
    @Id
    private int id;
    
    private String name;
    private String group;
    private boolean isMuted;
    private boolean isSpying;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getGroup() {
    	return group;
    }
    
    public void setGroup(String group) {
    	this.group = group;
    }
    
    public boolean isMuted() {
    	return isMuted;
    }
    
    public void setSpying(boolean isMuted) {
    	this.isMuted = isMuted;
    }
    
    public boolean isSpying() {
    	return isSpying;
    }
    
    public void isSpying(boolean isSpying) {
    	this.isSpying = isSpying;
    }
}
