package com.example.mysqlconnection.service;

import com.example.mysqlconnection.model.EMP;
import com.example.mysqlconnection.repo.RepoJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpService {
    @Autowired
    RepoJpa repoJpa;

    public List<EMP> getAllEmp(){
        return repoJpa.findAll();
    }
}
