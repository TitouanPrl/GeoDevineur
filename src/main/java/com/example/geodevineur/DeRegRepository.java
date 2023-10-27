package com.example.geodevineur;

import com.example.geodevineur.dep_reg.Departement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.geodevineur.dep_reg.DepReg;

import java.util.List;

@Repository
public interface DeRegRepository extends CrudRepository<DepReg, Integer> {

}
