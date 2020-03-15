package com.trustly.githubapi.model.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GenericError implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	private String message;
	
	public GenericError() {
		this.code = "999";
		this.message = "Internal error";
	}

}
