package com.api.tec.service;

import java.util.List;

 
public interface ICRUD<T, ID> {

	T registrar(T c);
	T modificar(T c);
	List<T> listar();
	T listarPorId(ID id);
	void eliminar(ID id);
}
