package org.isetn;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/*import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;*/

@RepositoryRestResource
public interface ClasseRepository extends JpaRepository<Classe, Long> {
    List<Classe> findByDepart(String depart);
}
