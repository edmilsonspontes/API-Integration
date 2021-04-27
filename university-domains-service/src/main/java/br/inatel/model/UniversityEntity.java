package br.inatel.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_UNIVERSITY")
public class UniversityEntity implements Serializable {

	private static final long serialVersionUID = 6753654060322970545L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "NAME_UNIVERSITY")
	private String name;

	@Column(name = "COUNTRY_UNIVERSITY")
	private String country;

	@Column(name = "ALPHA_TWO_CODE_UNIVERSITY")
	private String alphaTwoCode;

	@Transient
	@JsonManagedReference
	private Set<DomainEntity> domains = new HashSet<DomainEntity>();

	@Transient
	@JsonManagedReference
	private Set<WebPageEntity> web_pages = new HashSet<WebPageEntity>();

}
