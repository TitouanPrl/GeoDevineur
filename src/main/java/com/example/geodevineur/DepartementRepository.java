package com.example.geodevineur;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartementRepository extends CrudRepository<Departement, String> {

    void saveAll(List<com.example.geodevineur.dep_reg.Departement> departementList);
    
}
