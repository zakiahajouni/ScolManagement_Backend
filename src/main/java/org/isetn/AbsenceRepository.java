package org.isetn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AbsenceRepository extends JpaRepository<Absence,Long> {

    List<Absence> findByEtudiant(Etudiant etudiant);
    @Query("SELECT SUM(a.nha) FROM Absence a WHERE a.etudiant.id = :studentId AND a.matiere.codMatiere = :subjectId")
    Integer sumAbsenceHoursByStudentAndSubject(@Param("studentId") Long studentId, @Param("subjectId") Long subjectId);

    @Query("SELECT SUM(a.nha) FROM Absence a WHERE a.etudiant.id = :studentId")
    Integer sumAbsenceHoursByStudent(@Param("studentId") Long studentId);

}
