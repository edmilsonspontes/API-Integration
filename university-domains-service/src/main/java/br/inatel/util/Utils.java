package br.inatel.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class Utils {
	
	/**
	 * Cria objeto para requisição de rest 
	 * @param country
	 * @return
	 */
	public static HttpEntity<String> createHttpRequestFromCountry(String country) {
		StringBuffer json = new StringBuffer();
		json.append("{\n\t");
		json.append("\t\"university\":{");
		json.append("\t\"country\": \"" + country + "\"");
		json.append("\t}\n");
		json.append("}");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<String>(json.toString(), headers);
	}
	
	/**
	 * Converte um map de string em uma string jscon 
	 * @param objMap
	 * @return
	 */
	public static String mapToJsonUniversity(Map objMap) {
		StringBuffer result = new StringBuffer();

		List<String> domainsList = objMap.get("domains") != null ? (List)objMap.get("domains") : new ArrayList<>() ;
		List<String> webpagesList = objMap.get("web_pages") != null ? (List)objMap.get("web_pages") : new ArrayList<>();
		
		result.append("{ \n");
		result.append("\t\"name\": \"" + objMap.get("name") + "\", \n");
		result.append("\t\"country\": \"" + objMap.get("country") + "\", \n");
		result.append("\t\"alpha_two_code\": \"" + objMap.get("alpha_two_code") + "\", \n");
		
		result.append("\t\"domains\":[\n");
		if(domainsList != null && domainsList.size() > 0) {
			domainsList.forEach((String domain) -> {
				createDomainJson(result, domain);
				if(domainsList.size() > 1) {
					result.append(",\n");
				}
			});
		}
		result.append("\t], \n");
		
		result.append("\t\"web_pages\":[\n");
		if(webpagesList != null && webpagesList.size() > 0) {
			webpagesList.forEach((String wp) -> {
				createWebPageJson(result, wp);
				if(webpagesList.size() > 1) {
					result.append(",\n");
				}
			});
		}
		result.append("\t] \n");
		
		result.append("}");
		return result.toString();
	}

	private static void createWebPageJson(StringBuffer result, Object wp) {
		result.append("\t\t{ \n");
		result.append("\t\t\t\"url\": \"" + wp + "\"\n");
		result.append(" \t\t}");
	}

	private static void createDomainJson(StringBuffer result, Object domain) {
		result.append("\t\t{ \n");
		result.append("\t\t\t\"name\": \"" + domain + "\"\n");
		result.append(" \t\t}");
	}
	
}
