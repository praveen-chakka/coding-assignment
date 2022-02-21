package com.assignment.slcsp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ZipRate implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1357563307955014659L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	
	@Column(name = "zip_code")
	private String zipCode;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "county_code")
	private String countyCode;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "rate_area")
	private long rateArea;

}
