package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Profiles extends AbstractEntity{

	
	@Id
	private String profileId;
	
	@Column
	private String profileName;
	
	
}
