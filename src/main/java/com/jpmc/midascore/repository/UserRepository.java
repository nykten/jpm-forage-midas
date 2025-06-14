package com.jpmc.midascore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpmc.midascore.entity.UserRecord;

@Repository
public interface UserRepository extends JpaRepository<UserRecord, Long> {
//    UserRecord findById(long id);
    UserRecord findByName(String name);
}


//import org.springframework.data.repository.CrudRepository;
//
//public interface UserRepository extends CrudRepository<UserRecord, Long> {
//    UserRecord findById(long id);
//}

