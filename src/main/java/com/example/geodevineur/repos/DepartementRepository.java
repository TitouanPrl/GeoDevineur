package com.example.geodevineur.repos;

import com.example.geodevineur.tables.Departement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartementRepository extends CrudRepository<Departement, Integer> {

}
