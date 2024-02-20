package com.api.tec.controller;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.tec.config.JwtService;
import com.api.tec.model.JwtRequest;
import com.api.tec.model.JwtResponse;
import com.api.tec.model.Phone;
import com.api.tec.model.User;
import com.api.tec.model.UserRequest;
import com.api.tec.service.IUserService;
 

@RestController
@RequestMapping( "/users" )  
public class UserController {

	@Autowired 
	IUserService service; 
	@Autowired
	JwtService jwtService  ; 
	@Autowired
    AuthenticationManager authenticationManager;
	@Autowired
	BCryptPasswordEncoder encriptar;
	
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
	  
	 @PostMapping("/registro")
	 public ResponseEntity<Object> registrarUsuario(@RequestBody User user) { 
	     try {
 	         if (!isValidEmail(user.getUsername())) {
	             String mensaje = "El formato del correo electrónico no es válido";
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", mensaje));
	         }
	         if (!isValidPassword(user.getPassword())) {
	             String mensaje = "El formato de la contraseña no es válido";
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", mensaje));
	         }

	         // Verificar si el usuario ya está registrado
	         Optional<User> existingUser = service.findByUsername(user.getUsername());
	         if (existingUser.isPresent()) {
	             String mensaje = "El correo " + user.getUsername() + " ya ha sido registrado para otro usuario";
	             return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("mensaje", mensaje));
	         }
	   
	         //seteo
	         user.setCreated(new Date());
	         user.setModified(new Date());
	         user.setLast_login(new Date()); 
	         user.setIsactive(true); 
	         user.setPassword(encriptar.encode(user.getPassword()));
	         
	         User savedUser = service.registrar(user);

	         //token JWT 
	         String token=jwtService.getToken(savedUser);

	         // Convertir a UserRequest
	         UserRequest newUser = convertirAUserRequest(savedUser,token);

	         // Devolver la respuesta con los detalles del usuario registrado
	         return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	     } catch (Exception e) {
	         String mensaje = "Error al registrar el usuario: " + e.getMessage();
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("mensaje", mensaje));
	     }
	 }

	private UserRequest convertirAUserRequest(User user,String token) {
	    UserRequest userRequest = new UserRequest();
	    userRequest.setId(user.getIdUsuario());
	    userRequest.setName(user.getName());
	    userRequest.setCreated(user.getCreated());
	    userRequest.setModified(user.getModified());
	    userRequest.setLast_login(user.getLast_login());
	    userRequest.setToken(token);
	    userRequest.setIsactive(user.getIsactive()); 
	    for (Phone phone : user.getPhones()) {
	        phone.setUser(user); 
	    }
	    return userRequest;
	}
	
	
	//login
	  @PostMapping("/login")
	    public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) {
	        try {
	        	 // Obtener el correo de usuario
	            String username = jwtRequest.getUsername();
	            
	            // Obtener  usuario existente
	            Optional<User> existingUser = service.findByUsername(username);
	            if (!existingUser.isPresent()) {
	                String mensaje = "El usuario no existe";
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", mensaje));
	            }
	         // Generar el token  
	            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
	            UserDetails user=service.findByUsername(jwtRequest.getUsername()).orElseThrow();
	            String token=jwtService.getToken(user);

	            //Modificar datos
	            User usuario = existingUser.get();
	            usuario.setLast_login(new Date());
	            service.modificar(usuario);
	            
	            return ResponseEntity.ok(new JwtResponse(token));
	        } catch (Exception e) {
	            String mensaje = "Error al generar el token: " + e.getMessage();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("mensaje", mensaje));
	        }
	    }

	 
	    
	
	
	 
}
