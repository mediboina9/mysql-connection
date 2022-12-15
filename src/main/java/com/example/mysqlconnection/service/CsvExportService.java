package com.example.mysqlconnection.service;

import com.example.mysqlconnection.model.EMP;
import com.example.mysqlconnection.repo.EmpCrudRepo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
@Service
public class CsvExportService {

    private final EmpCrudRepo employeeRepository;

    public CsvExportService(EmpCrudRepo employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void writeEmployeesToCsv(Writer writer) {
        Iterable<EMP> employees =  employeeRepository.findAll();
        System.out.println("........................" +
                "........................" +
                "=============================" +
                "==================================" +
                "====================================" +
                "=====================================");
        System.out.println(employees);
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (EMP employee : employees) {
                csvPrinter.printRecord(employee.getId(), employee.getName());
            }
        } catch (IOException e) {
            System.out.println("Error While writing CSV "+ e);
        }
    }
}
