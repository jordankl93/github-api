package com.trustly.githubapi.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "file")
public class File implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy="increment")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "extension")
	private String extension;
	
	@Column(name = "number_lines")
	private Long numberLines;
	
	@Column(name = "size")
	private BigDecimal size;
	
	@Column(name = "url_base")
	private String urlBase;
	

}
