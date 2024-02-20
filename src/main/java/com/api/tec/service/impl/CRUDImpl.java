package com.api.tec.service.impl;

import java.util.List;

import com.api.tec.repo.IGenericRepo;
import com.api.tec.service.ICRUD; 
 
 
 
public  abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

	protected abstract IGenericRepo<T, ID> getRepo();
	
	@Override
	public T registrar(T c) {
		// TODO Auto-generated method stub
		return getRepo().save(c);
	}

	@Override
	public T modificar(T c) {
		// TODO Auto-generated method stub
		return getRepo().save(c);
	}

	@Override
	public List<T> listar() {
		// TODO Auto-generated method stub
		return getRepo().findAll(); 
	}

	@Override
	public T listarPorId(ID id) {
		// TODO Auto-generated method stub
		return getRepo().findById(id).get();
	}

	@Override
	public void eliminar(ID id) {
		// TODO Auto-generated method stub
		getRepo().deleteById(id);
	}

	
}
