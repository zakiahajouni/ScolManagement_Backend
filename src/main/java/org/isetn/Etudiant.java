package org.isetn;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Etudiant {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private String prenom;
	private LocalDateTime dateNais;
	@ManyToOne
	@JsonIgnore
	private Formation formation;
	@ManyToOne
	@JsonIgnore
	private Classe classe;

	@OneToMany(mappedBy = "etudiant", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Absence> absences;





}
