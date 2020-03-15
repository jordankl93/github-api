package com.trustly.githubapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trustly.githubapi.model.File;

public interface FileRepository extends JpaRepository<File, Long> {
	
	List<File> findByUrlBase(String url);

}
