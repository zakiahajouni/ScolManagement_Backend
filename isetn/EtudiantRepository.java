package org.isetn;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
/*import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;*/
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    List<Etudiant> findByClasse_CodClass(Long classId);
}
