package com.trustly.githubapi.resource;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trustly.githubapi.exception.BusinessException;
import com.trustly.githubapi.model.response.FileResponse;
import com.trustly.githubapi.model.response.GenericError;
import com.trustly.githubapi.service.RepositoryGitHubService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/files")
public class RepositoryGitHubController {
	
	private RepositoryGitHubService repositoryGitHubService;
	
	RepositoryGitHubController(RepositoryGitHubService repositoryGitHubService){
		this.repositoryGitHubService = repositoryGitHubService;
	}
	
	@ApiOperation(value = "Returns files from a github repository")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Returns files from a github repository"),
	    @ApiResponse(code = 400, message = "Not Found", response=GenericError.class),
	    @ApiResponse(code = 500, message = "Internal Error", response=GenericError.class),
	})
	@GetMapping(produces="application/json")	
	public Map<String, List<FileResponse>> findAllFiles(@ApiParam(example="https://github.com/jordankl93/ProxyScala", required = true)@RequestParam(value = "url", required = true) String url) throws BusinessException {
		return repositoryGitHubService.findAllFiles(url);
	} 

}
