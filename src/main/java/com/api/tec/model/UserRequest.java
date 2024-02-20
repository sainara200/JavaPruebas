package com.api.tec.model;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor 
@AllArgsConstructor
@Getter
@Setter
public class UserRequest { 
	    private UUID id;
	    private String name;
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm")  
	    private Date created; 
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm")  
		private Date modified; 
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm")  
		private Date last_login;
		private String token;
		private Boolean isactive;
}
