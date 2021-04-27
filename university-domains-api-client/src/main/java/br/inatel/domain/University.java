package br.inatel.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class University implements Serializable{
	private static final long serialVersionUID = -4796671289451682712L;
	
	private String country;
	private String[] domains;
	private String[] web_pages;
	private String alpha_two_code;
	
	
	public University() {
	}

	private String name;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String[] getDomains() {
		return domains;
	}

	public void setDomains(String[] domains) {
		this.domains = domains;
	}

	public String[] getweb_pages() {
		return web_pages;
	}

	public void setweb_pages(String[] web_pages) {
		this.web_pages = web_pages;
	}

	public String getAlpha_two_code() {
		return alpha_two_code;
	}

	public void setAlpha_two_code(String alpha_two_code) {
		this.alpha_two_code = alpha_two_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
