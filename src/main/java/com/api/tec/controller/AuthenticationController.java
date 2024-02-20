package com.api.tec.controller; 
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
 
import com.api.tec.config.JwtService; 
import com.api.tec.model.User;
import com.api.tec.service.IUserService;

import jakarta.servlet.http.HttpServletResponse; 
 

@RestController
@RequestMapping( "/auth" )   
public class AuthenticationController {
	@Autowired
	   IUserService service;
	@Autowired
	   JwtService jwtService  ;
	@Autowired
	    PasswordEncoder passwordEncoder  ;
	@Autowired
	    AuthenticationManager authenticationManager;

	 @GetMapping("/listar")
	    public ResponseEntity<List<User>> listar(HttpServletResponse response) {
	        
	        List<User> lista = service.listar();
	        return new ResponseEntity<>(lista, HttpStatus.OK);
	    }
	 
	 private boolean isValidEmail(String email) {
		    if (email == null) {
		        return false; 
		    }
		    String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		    return email.matches(regex);
		}

		 private boolean isValidPassword(String password) {
			 if (password == null) {
			        return false; 
			    }
			    String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
			    return password.matches(regex);  
			}
	 
	 
	 
	 @PutMapping("/modificar/{username}")
	 public ResponseEntity<Object> modificar(@PathVariable("username") String username, @RequestBody User usuario) {
	     // Verificar si el usuario existe
	     Optional<User> existingUser = service.findByUsername(username);
	     Optional<User> correo = service.findByUsername(usuario.getUsername());
	     if (!existingUser.isPresent()) {
	    	 //caso contrario
	         String mensaje = "El usuario no existe";
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", mensaje));

 	     }
	      
         if (correo.isPresent()) {
             String mensaje = "El correo " + usuario.getUsername() + " ya ha sido registrado para otro usuario";
             return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("mensaje", mensaje));
         }
	     
	     if (!isValidEmail(usuario.getUsername())) {
             String mensaje = "El formato del correo electrónico no es válido";
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", mensaje));
         }
         if (!isValidPassword(usuario.getPassword())) {
             String mensaje = "El formato de la contraseña no es válido";
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", mensaje));
         }

 
	     usuario.setIdUsuario(existingUser.get().getIdUsuario());
	     
	     if (usuario.getName().isEmpty()) {
	    	 usuario.setName(existingUser.get().getName());
	     }

	     if (usuario.getUsername().isEmpty()) {
	    	 usuario.setUsername(existingUser.get().getUsername());
	     }

	     if (usuario.getPassword().isEmpty()) {
	    	 usuario.setPassword(existingUser.get().getPassword());
	     }

	     if (usuario.getPhones() == null || usuario.getPhones().isEmpty()) {
	    	 usuario.setPhones(existingUser.get().getPhones());
	     }
	     if (usuario.getCreated() == null) {
	    	 usuario.setCreated(existingUser.get().getCreated());
	     }

	     if (usuario.getModified() == null) {
	    	 usuario.setModified(new Date()) ;
	    	 }

	     if (usuario.getLast_login() == null) {
	    	 usuario.setLast_login(existingUser.get().getLast_login());
	     }

	     if (usuario.getIsactive() == null) {
	    	 usuario.setIsactive(existingUser.get().getIsactive());
	     }

	     usuario.setModified(new Date()) ; 
	     User modifiedUser = service.modificar(usuario);
	     return ResponseEntity.ok(modifiedUser);
	 }
	    
	}