package com.api.tec.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.api.tec.model.User;
import com.api.tec.repo.IGenericRepo;
import com.api.tec.repo.IUserRepo;
import com.api.tec.service.IUserService;
@Service
public class UserServiceImpl extends CRUDImpl<User, UUID> implements IUserService {
 
		//instancia
				@Autowired
				IUserRepo repo;

				@Override
				protected IGenericRepo<User, UUID> getRepo() {
					// TODO Auto-generated method stub
					return repo;
				}

				@Override
				public Optional<User> findByUsername(String email) {
					// TODO Auto-generated method stub
					return repo.findByUsername(email);
				}
			
			
	}
