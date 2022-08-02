package com.example.mysqlconnection.repo;

import com.example.mysqlconnection.model.EMP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RepoJpa extends JpaRepository<EMP,Integer> {
    ArrayList<EMP> findBy();
}
