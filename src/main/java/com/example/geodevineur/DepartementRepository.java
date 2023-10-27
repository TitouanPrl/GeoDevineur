package com.example.geodevineur;

import com.example.geodevineur.tables.Departement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.geodevineur.tables.Departement;

@Repository
public interface DepartementRepository extends CrudRepository<Departement, Integer> {

}
