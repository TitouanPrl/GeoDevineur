package com.example.geodevineur;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.geodevineur.dep_reg.DepReg;

@Repository
public interface DeRegRepository extends CrudRepository<DepReg, Integer> {
}
