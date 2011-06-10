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

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.halvors.LocalChat.group.Group;
import com.halvors.LocalChat.group.GroupManager;
import com.halvors.LocalChat.user.UserManager;
import com.halvors.LocalChat.util.ConfigManager;

public class LocalChatCommandExecutor implements CommandExecutor {
	private LocalChat plugin;

	private ConfigManager configManager;
	private GroupManager groupManager;
	private UserManager userManager;
	
	public LocalChatCommandExecutor(LocalChat plugin) {
		this.plugin = plugin;
		
		configManager = plugin.getConfigManager();
		groupManager = plugin.getGroupManager();
		userManager = plugin.getUserManager();
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			
			if (args.length == 0) {
				if (plugin.hasPermissions(player, "LocalChat.status")) {
					if (args.length >= 2) {
						Player player1 = plugin.getServer().getPlayer(args[1]);
						
						if (player1 != null) {
							if (player == player1) {
								showStatus(player);
							} else {
								showStatus(player, player1);
							}
						} else {
							player.sendMessage(ChatColor.RED + configManager.Player_does_not_exist);
						}
					} else {
						showStatus(player);
					}
					
					return true;
				}
			} else {
				String subCommand = args[0];
				
				if (subCommand.equalsIgnoreCase("help")) {
					if (plugin.hasPermissions(player, "LocalChat.help")) {
						showHelp(player, label);
					
						return true;
					}
				} else if (subCommand.equalsIgnoreCase("status")) {
					if (plugin.hasPermissions(player, "LocalChat.status")) {
						if (args.length >= 2) {
							Player player1 = player.getServer().getPlayer(args[1]);
						
							if (player1 != null) {
								if (player == player1) {
									showStatus(player);
								} else {
									showStatus(player, player1);
								}
							} else {
								player.sendMessage(ChatColor.RED + configManager.Player_does_not_exist);
							}
						} else {
							showStatus(player);
						}

						return true;
					}
				} else if (subCommand.equalsIgnoreCase("list")) {
					if (plugin.hasPermissions(player, "LocalChat.list")) {
						List<Group> groups = plugin.getGroupManager().getGroups();
					
						for (Group g : groups) {
							String name = g.getName();
//							int distance = g.getDistance();
						
							player.sendMessage(ChatColor.YELLOW + "Group: " + ChatColor.GREEN + name);
						}

						return true;
					}
				} else if (subCommand.equalsIgnoreCase("join")) {
					if (plugin.hasPermissions(player, "LocalChat.join")) {
						if (args.length >= 2) {
							String name = player.getName();
							String group = args[1];
						
							if (groupManager.hasGroup(group)) {
								Group g = groupManager.getGroup(group);
								g.addPlayer(name);
							
								player.sendMessage(ChatColor.GREEN + configManager.You_are_now_in_group.replace("<group>", g.getName()));
							}
						} else {
							player.sendMessage("No group specified."); // TODO: Add to config file
						}
					
						return true;
					}
				} else if (subCommand.equalsIgnoreCase("leave")) {
					if (plugin.hasPermissions(player, "LocalChat.leave")) {
						String name = player.getName();
					
						if (userManager.getUser(name).inGroup()) {
							Group g = userManager.getUser(name).getGroup();
							g.removePlayer(name);
					
							player.sendMessage(ChatColor.GREEN + configManager.You_are_no_longer_in_a_group);
						} else {
							player.sendMessage(ChatColor.RED + configManager.You_are_not_in_a_group);
						}
					
						return true;
					}
				} else if (subCommand.equalsIgnoreCase("create")) {
					if (plugin.hasPermissions(player, "LocalChat.admin.create")) {
						if (args.length >= 2) {
							String group = args[1];						
							
							if (groupManager.getGroup(args[1]) != null) {
								player.sendMessage(ChatColor.RED + configManager.Group_already_exists.replace("<group>", group));
							} else {
								Group g = new Group(plugin);
								g.setName(group);
								
								groupManager.addGroup(g);
								configManager.save();
					
								player.sendMessage(ChatColor.GREEN + configManager.Group_succesfully_created.replace("<group>", group));
							}
						} else {
							player.sendMessage("No group specified."); // TODO: Add to config file
						}
					
						return true;
					}
				} else if (subCommand.equalsIgnoreCase("delete")) {
					if (plugin.hasPermissions(player, "LocalChat.admin.delete")) {
						if (args.length >= 2) {
							String group = args[1];
						
							if (groupManager.hasGroup(group)) {
								Group g = groupManager.getGroup(group);
							
								groupManager.removeGroup(g);
								configManager.save();

								player.sendMessage(ChatColor.GREEN + configManager.Group_succesfully_created.replace("<group>", g.getName()));
							} else {
								player.sendMessage(ChatColor.RED + configManager.Group_does_not_exist);
							}
						} else {
							player.sendMessage("No group specified."); // TODO: Add to config file
						}
					
						return true;
					}
				} else if (subCommand.equalsIgnoreCase("spy")) {
					if (plugin.hasPermissions(player, "LocalChat.admin.spy")) {	
						String name = player.getName();
					
						if (userManager.getUser(name).isSpying()) {
							userManager.getUser(player.getName()).setSpying(false);
						
							player.sendMessage(ChatColor.RED + configManager.You_are_no_longer_spying);
						} else {
							userManager.getUser(player.getName()).setSpying(true);
							
							player.sendMessage(ChatColor.GREEN + configManager.You_are_now_spying);
						}
					
						return true;
					}	
				} else if (subCommand.equalsIgnoreCase("set")) {
					if (plugin.hasPermissions(player, "LocalChat.admin.set")) {
						if (args.length >= 3) {
							Player player1 = player.getServer().getPlayer(args[1]);
						
							if (player1 != null) {
								String group = args[2];
							
								if (groupManager.hasGroup(group)) {
									Group g = groupManager.getGroup(group);
								
									userManager.getUser(player1.getName()).setGroup(g);
								
									player.sendMessage(ChatColor.GREEN + configManager.Player_group_set_to.replace("<player>", player1.getName()).replace("<group>", group));
								} else {
									player.sendMessage(ChatColor.GREEN + configManager.Group_does_not_exist);
								}
							} else {
								player.sendMessage(ChatColor.RED + configManager.Player_does_not_exist);
							}
						} else {
							// Maybe add something here 
						}
					
						return true;
					}
				} else {
					if (plugin.hasPermissions(player, "LocalChat.help")) {
						showHelp(player, label);
						
						return true;
					}
				}
			}
		}
		
		return false;
	}

	private void showHelp(Player player, String label) {
		player.sendMessage(ChatColor.YELLOW + plugin.getName() + " (" + ChatColor.WHITE + plugin.getVersion() + ")");
		player.sendMessage(ChatColor.RED + "[]" + ChatColor.WHITE + " Required, " + ChatColor.GREEN + "<>" + ChatColor.WHITE + " Optional");

		String command = label + " ";
		
		if (plugin.hasPermissions(player, "LocalChat.help")) {
			player.sendMessage(command + "help" + ChatColor.YELLOW + " - Show help");
		}

		if (plugin.hasPermissions(player, "LocalChat.status")) {
			player.sendMessage(command + "status " + ChatColor.GREEN + "(" + ChatColor.WHITE + "player" + ChatColor.GREEN + ")" + ChatColor.YELLOW + " - Show your/others status");
		}
		
		if (plugin.hasPermissions(player, "LocalChat.list")) {
			player.sendMessage(command + "list" + ChatColor.YELLOW + " - List all groups");
		}

		if (plugin.hasPermissions(player, "LocalChat.join")) {
			player.sendMessage(command + "join " + ChatColor.RED + "[" + ChatColor.WHITE + "group" +  ChatColor.RED + "]" + ChatColor.YELLOW + " - Join group");
		}

		if (plugin.hasPermissions(player, "LocalChat.leave")) {
			player.sendMessage(command + "leave" + ChatColor.YELLOW + " - Leave group");
		}

		if (plugin.hasPermissions(player, "LocalChat.create")) {
			player.sendMessage(command + "create " + ChatColor.RED + "[" + ChatColor.WHITE + "group" + ChatColor.RED + "]" + ChatColor.YELLOW + " - Create a group");
		}

		if (plugin.hasPermissions(player, "LocalChat.delete")) {
			player.sendMessage(command + "delete " + ChatColor.RED + "[" + ChatColor.WHITE + "group" + ChatColor.RED + "]" + ChatColor.YELLOW + " - Delete a group");
		}

		if (plugin.hasPermissions(player, "LocalChat.admin.set")) {
			player.sendMessage(command + "set " + ChatColor.RED + "[" + ChatColor.WHITE + "player" + ChatColor.RED + "] [" + ChatColor.WHITE + "group" + ChatColor.RED + "]" + ChatColor.YELLOW + " - Set player to group");
		}

		if (plugin.hasPermissions(player, "LocalChat.admin.spy")) {
			player.sendMessage(command + "spy" + ChatColor.YELLOW + " - See all chats in all groups");
		}
	}
	
	private void showStatus(Player player) {
		if (userManager.getUser(player.getName()).inGroup()) {
			player.sendMessage(ChatColor.GREEN + configManager.Your_current_group_is.replace("<group>", plugin.getUserManager().getUser(player.getName()).getGroup().getName()));
		} else {
			player.sendMessage(ChatColor.RED + configManager.You_are_not_in_a_group);
		}
	}
	
	private void showStatus(Player player1, Player player2) {
		if (userManager.getUser(player2.getName()).inGroup()) {
			player1.sendMessage(ChatColor.GREEN + configManager.Player_current_group_is.replace("<player>", player2.getName().replace("<group>", plugin.getUserManager().getUser(player2.getName()).getGroup().getName())));
		} else {
			player1.sendMessage(ChatColor.RED + configManager.Player_is_not_in_a_group.replace("<player>", player2.getName()));
		}
	}
}

/*	
private void sendChatGroupMessage(Player player, String chatGroup, String[] args) {
	if (!plugin.cmdMap.containsKey(player)) {
		LocalChat.cmdMap.put(player, chatGroup);
		String msg = "";
			
		for (int i = 1; i < args.length; i++) {
			msg = msg += args[i] + " ";
		}
		
		player.chat(msg);
	}
}
*/