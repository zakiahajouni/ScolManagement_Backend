package org.isetn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime; // Import LocalDateTime
import java.util.Collection; // Import Collection
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Matiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codMatiere;
    private String nomMat;
    private String hours ,minutes;



    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "classe_matiere",
            joinColumns = @JoinColumn(name = "codMatiere"),
            inverseJoinColumns = @JoinColumn(name = "codClass"))

    private Collection<Classe> classes;


    @OneToMany(mappedBy = "matiere",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Absence> absences;



}
