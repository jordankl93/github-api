package com.trustly.githubapi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.trustly.githubapi.model.response.FileResponse;
import com.trustly.githubapi.service.RepositoryGitHubService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GithubapiApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private RepositoryGitHubService service;

	@Test
	public void validRequestReturns200OK() throws Exception {
		FileResponse fileResponse = FileResponse.builder()
								.name("teste.txt")
								.extension("txt")
								.lines(100l)
								.size(new BigDecimal(1000))
								.build();
		
		List<FileResponse> fileResponseList = new ArrayList<>();
		fileResponseList.add(fileResponse);
		 
		Map<String, List<FileResponse>> allFiles = fileResponseList.stream().collect(Collectors.groupingBy(FileResponse::getExtension));
		
	 
	    given(service.findAllFiles(any(String.class))).willReturn(allFiles);
	 
	    mockMvc.perform(get("/files")
	      .contentType(MediaType.APPLICATION_JSON)
	      .param("url", "https://github.com/jordankl93/ProxyScala"))      
	      .andExpect(status().isOk())
	      .andExpect(content().contentType("application/json"));
	}

}