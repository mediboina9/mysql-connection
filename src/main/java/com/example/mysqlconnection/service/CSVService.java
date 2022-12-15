package com.example.mysqlconnection.service;

import com.example.mysqlconnection.config.CSVHelper;
import com.example.mysqlconnection.model.EMP;
import com.example.mysqlconnection.repo.EmpCrudRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
@Service
public class CSVService {
    @Autowired
    EmpCrudRepo repository;

    public ByteArrayInputStream load() {
        Iterable<EMP> tutorials = repository.findAll();

        ByteArrayInputStream in = CSVHelper.tutorialsToCSV(tutorials);
        return in;
    }
}
