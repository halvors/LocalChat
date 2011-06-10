package com.halvors.LocalChat.user;

import java.util.ArrayList;
import java.util.List;

import com.halvors.LocalChat.LocalChat;

public class UserManager {
	private final LocalChat plugin;
	
	private List<User> users = new ArrayList<User>();
	
	public UserManager(LocalChat instance) {
		plugin = instance;
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
