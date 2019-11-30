package com.reservo.reservo.DAL;

import java.util.List;

import com.reservo.reservo.Models.User;

public interface User_DAL {

	List<User> getAllUsers();

	User getUserById(String userId);

	User addNewUser(User user);

	Object getAllUserSettings(String userId);

	String getUserSetting(String userId, String key);

	String addUserSetting(String userId, String key, String value);
}