package com.trustly.githubapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.trustly.githubapi.exception.BusinessException;
import com.trustly.githubapi.model.File;
import com.trustly.githubapi.model.response.FileResponse;
import com.trustly.githubapi.model.response.GenericError;
import com.trustly.githubapi.repository.FileRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RepositoryGitHubServiceImpl implements RepositoryGitHubService {
	
	private final FileRepository fileRepository;
	
    @Autowired
    private Environment env;

	@Override
	public Map<String, List<FileResponse>> findAllFiles(String urlBase) throws BusinessException {
		URL url;
		StringBuilder html;
		StringBuilder line;
		
		List<String> listLink = new ArrayList<>();
		List<File> listFile = null;
		
		listFile = fileRepository.findByUrlBase(urlBase);
		
		if (listFile ==  null || listFile.isEmpty()) {		
			listFile = new ArrayList<>();
			listLink.add(urlBase);
			
			if(!listLink.isEmpty()) {
			
				try {
					
					do {
						url = new URL(listLink.remove(listLink.size() - 1));
						URLConnection conn = url.openConnection();
			
						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						
						html = new StringBuilder();
			
						String inputLine;
						while ((inputLine = br.readLine()) != null) {
							html.append( inputLine );
						}
						
						
						Pattern pattFile = Pattern.compile(env.getProperty("regex.github.repository.file"), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
						Matcher mFile = pattFile.matcher(html);
		
						File file = null;
						while(mFile.find()) {
							line = new StringBuilder();
							
							line.append(mFile.group(1));
							line.append(mFile.group(2));
							line.append(mFile.group(3));
							
							String[] strings = line.toString().split("\\s+");
							
							// [1] - number of lines
							// [5] - file size
							// [6] - Bytes|KB|MB|GB
							String aux = null;
							String name = null;
							Long numberLines = null;
							BigDecimal fileSize = null;
							String extension = null;
							for(int i=0; i < strings.length; i++) {					
								
								String string = strings[i] != null ? strings[i].trim() : null;
								
								if(i==0) {
									name = string;
								} else if (string != null && string.matches("-?\\d+(\\.\\d+)?")) {
									aux = string;
								} else if ("lines".equalsIgnoreCase(string)) {
									try {
										numberLines = Long.parseLong(aux);
									} catch(NumberFormatException  e ) {
										numberLines = null;										
									}
								} else if ("bytes".equalsIgnoreCase(string)) {
									
									fileSize = new BigDecimal(aux);
									
								} else if ("kb".equalsIgnoreCase(string)) {
									
									fileSize = new BigDecimal(aux);
									fileSize = fileSize.multiply(new BigDecimal("1024"));
									
								} else if ("mb".equalsIgnoreCase(string)) {
									
									fileSize = new BigDecimal(aux);
									fileSize = fileSize.multiply(new BigDecimal("1048576"));
									
								} else if ("gb".equalsIgnoreCase(string)) {
									
									fileSize = new BigDecimal(aux);
									fileSize = fileSize.multiply(new BigDecimal("1073741824"));
									
								}
							}
							
							String[] nameSplit = name.split("\\.");						
							extension = nameSplit != null & nameSplit.length != 0 ? nameSplit[nameSplit.length - 1] : null;
							
							file = File.builder()
									.numberLines(numberLines)
									.size(fileSize)
									.name(name)
									.extension(extension)
									.urlBase(urlBase)
									.build();
							
							fileRepository.save(file);
							
							listFile.add(file);
							
						}
					
						
						Pattern pattLink = Pattern.compile(env.getProperty("regex.github.repository.link"), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
						Matcher mLink = pattLink.matcher(html);			
						
						String path = null;
						while(mLink.find()) {
							path = mLink.group(1);
							
							if (path != null)
								listLink.add(env.getProperty("github.page.home") + path);
						}
						
						
			
						br.close();
					} while (!listLink.isEmpty());
		
				} catch (MalformedURLException e) {
					e.printStackTrace();
					throw new BusinessException(new GenericError("Malformed URL", "The URL could not be found: " + urlBase));
				} catch (IOException e) {
					e.printStackTrace();
					throw new BusinessException(new GenericError("Not Found", "The URL could not be found: " + urlBase));
				}
				
			}
		}
		
		List<FileResponse> fileResponseList = listFile.stream().map(q -> new FileResponse(q)).collect(Collectors.toList());
		
		Map<String, List<FileResponse>> fileResponseGroupByExt = fileResponseList.stream().collect(Collectors.groupingBy(FileResponse::getExtension));
		
		return fileResponseGroupByExt;
	}
	
	

}
