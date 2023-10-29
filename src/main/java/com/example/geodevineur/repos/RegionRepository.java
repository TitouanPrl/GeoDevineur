package com.example.geodevineur.repos;

import com.example.geodevineur.tables.Region;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends CrudRepository<Region, Integer> {

}
