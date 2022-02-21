package com.assignment.slcsp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;


@Data
@Entity
public class Plan implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4134321647105276182L;

	@Id
	@Column(name = "plan_id")
	private String planId;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "metal_level")
	private String metalLevel;
	
	@Column(name = "rate")
	private Double rate;
	
	@Column(name = "rate_area")
	private long rateArea;

}
