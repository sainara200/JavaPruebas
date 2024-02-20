package com.api.tec.model;
 
import java.util.UUID;
 

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name="phone")
public class Phone { 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	@JsonIgnore
	private UUID idPhone; 
	private Integer number; 
	private String citycode; 
	private String countrycode;   
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  
    	
    }
