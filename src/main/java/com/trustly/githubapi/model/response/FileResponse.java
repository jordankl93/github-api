package com.trustly.githubapi.model.response;

import java.math.BigDecimal;

import com.trustly.githubapi.model.File;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {
	
	private String name;
	
	private String extension;
	
	private Long lines;
	
	private BigDecimal size;
	
	public FileResponse(File file) {
		this.name = file.getName();
		this.extension = file.getExtension();
		this.lines = file.getNumberLines();
		this.size = file.getSize();		
	}
	

}
