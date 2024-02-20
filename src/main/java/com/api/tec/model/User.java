package com.api.tec.model;

 import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Data
@Entity
@Table(name="usuario")
public class User implements UserDetails  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private UUID idUsuario; 
	private String name; 
	//@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Correo inválido")
	private String username; 
	//@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un dígito, un carácter especial y tener al menos 8 caracteres")
	private String password;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Phone> phones ;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")   
    @Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")  
	private Date modified;
	@Temporal(TemporalType.TIMESTAMP)	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")   
	private Date last_login; 
	private Boolean isactive;
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	 
	
}
