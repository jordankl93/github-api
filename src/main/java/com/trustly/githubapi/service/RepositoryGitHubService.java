package com.trustly.githubapi.service;

import java.util.List;
import java.util.Map;

import com.trustly.githubapi.exception.BusinessException;
import com.trustly.githubapi.model.response.FileResponse;

public interface RepositoryGitHubService {
	
	public Map<String, List<FileResponse>> findAllFiles(String url)  throws BusinessException;

}
