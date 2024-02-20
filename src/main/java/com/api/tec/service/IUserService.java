package com.api.tec.service;

import java.util.Optional;
import java.util.UUID;
 
import com.api.tec.model.User;

public interface IUserService extends ICRUD<User, UUID>{
	 public Optional<User> findByUsername(String email); 
}
