package com.techner.tau.services.mapper;

import com.techner.tau.common.entity.User;

public interface UserMapper {

	/**
	 * Login de usuario de la aplicacion
	 * 
	 * @return true si login correcto, false en otro caso
	 */
	User login(User user);

	/**
	 * Actualiza usuarios
	 * 
	 * @param user
	 *            usarios
	 */
	void updateUser(User user);

	/**
	 * Insertar usuarios
	 * 
	 * @param user
	 *            usarios
	 */
	void insertUser(User user);

	/**
	 * Obtener usuario
	 * 
	 * @param email
	 *            email
	 */
	User getUser(Integer id);
}
