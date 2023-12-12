package org.isetn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classe {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codClass;
	private String nomClass;
	private int nbreEtud;
	private String depart;

	@ManyToMany(mappedBy = "classes", fetch = FetchType.EAGER)
	@JsonIgnore
	private Collection<Matiere> matieres;

	@OneToMany(mappedBy="classe", fetch = FetchType.LAZY)
	@JsonIgnore
	private Collection<Etudiant> etudiants;



}
