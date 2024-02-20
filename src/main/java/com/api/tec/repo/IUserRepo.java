package com.api.tec.repo;

import java.util.Optional;
import java.util.UUID;

import com.api.tec.model.User; 

public interface IUserRepo extends IGenericRepo<User, UUID>{
   
	 Optional<User>  findByUsername(String email);

}
