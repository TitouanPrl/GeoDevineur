package com.example.geodevineur.repos;

import com.example.geodevineur.tables.Prefecture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrefectureRepository extends CrudRepository<Prefecture, Integer> {

}
