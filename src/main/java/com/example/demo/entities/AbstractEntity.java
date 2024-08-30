package com.example.demo.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity  {
	 
	@CreatedBy
//	@JsonIgnore
	@Column(name="createdby",updatable = false,  insertable = true)
	protected String createdBy;

	@CreatedDate
	@CreationTimestamp
	@JsonIgnore
	@Column(name="createdon",updatable = false, insertable = true)
	protected LocalDateTime createdOn;

	@LastModifiedBy
	@JsonIgnore
	@Column(name="modifiedby", insertable = true)
	protected String modifiedBy;

	@LastModifiedDate
	@JsonIgnore
	@UpdateTimestamp
	@Column(name="modifiedon", insertable = true)
	protected LocalDateTime modifiedOn;

}
