package com.example.mysqlconnection.controller;

import com.example.mysqlconnection.repo.EmpCrudRepo;
import com.example.mysqlconnection.service.CSVService;
import com.example.mysqlconnection.service.CsvExportService;
import com.example.mysqlconnection.service.EmpService;
import com.example.mysqlconnection.repo.RepoJpa;
import com.example.mysqlconnection.model.EMP;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class EmpController {
    private static final Logger logger= LoggerFactory.getLogger(EmpController.class);
    @Autowired
    private EmpCrudRepo empCrudRepo;
    @Autowired
    private RepoJpa repoJpa;
    @Autowired
    private EmpService empService;



    @GetMapping("/")
    public List<EMP> getAllEmp(){
        logger.info("getAll method info.......");
        logger.debug(" this is debug...");
        logger.error(" This is error ...");
        logger.trace("This is trace ....");
        Iterable<EMP> iterable= empCrudRepo.findAllEmps(Sort.by("name"));
        logger.info("findAll()...",iterable);
        ArrayList<EMP> bookingDetailsArrayList=new ArrayList<>();
        for(EMP i:iterable){
            bookingDetailsArrayList.add(i);
        }
        return bookingDetailsArrayList;
    }
    @GetMapping("/{name}")
    public List<EMP> getAllEmpFindByName(@PathVariable String name){
        logger.info("getAll method info.......");
        logger.debug(" this is debug...");
        logger.error(" This is error ...");
        logger.trace("This is trace ....");
        Iterable<EMP> iterable= empCrudRepo.findByName(name);
        logger.info("findAll()...",iterable);
        ArrayList<EMP> bookingDetailsArrayList=new ArrayList<>();
        for(EMP i:iterable){
            bookingDetailsArrayList.add(i);
        }
        return bookingDetailsArrayList;
    }
    @GetMapping("/{name}/{id}")
    public List<EMP> getAllEmpFindByName(@PathVariable String name,@PathVariable int id){
        logger.info("getAll method info.......");
        logger.debug(" this is debug...");
        logger.error(" This is error ...");
        logger.trace("This is trace ....");
        Iterable<EMP> iterable= empCrudRepo.findByNameOrId(name,id);
        logger.info("findAll()...",iterable);
        ArrayList<EMP> bookingDetailsArrayList=new ArrayList<>();
        for(EMP i:iterable){
            bookingDetailsArrayList.add(i);
        }
        return bookingDetailsArrayList;
    }
    @PutMapping("/")
    public Optional<EMP> updateNameById(@RequestBody EMP emp){
        //logger.error("This is error when performing update on table");

        //empCrudRepo.updateNameById(emp.getName(), emp.getId());
        empCrudRepo.save(emp);
        logger.error("This is error when performing update on table");
        return empCrudRepo.findById(emp.getId());
    }
    @PostMapping("/")
    public ResponseEntity<EMP> createEmp(@RequestBody  @NotNull EMP emp){
        logger.info("RequestBody.....",emp.getName());
        System.out.println(emp.getName());
        if(emp.getName().equals(null)){
            emp.setName("DefaultName");
            logger.info("DefaultName setted for this emp");
        }
        else {
            logger.info("name not null", emp.getName());
        }

        empCrudRepo.save(emp);
        return new ResponseEntity<>(emp,HttpStatus.CREATED);
    }
    @GetMapping("/msg")
    public String getMsg(){
        return "Hello friends.................";
    }
    @GetMapping("/JpaRepo")
    public List<EMP> getJpaRepoEmployees(){
        List<EMP> emps=new ArrayList<>();

                emps =empService.getAllEmp();
                logger.info("JpaRepo......",emps);
        return emps;
    }
  @Autowired
  CsvExportService csvExportService;

    @RequestMapping(path = "/employees")
    public void getAllEmployeesInCsv(HttpServletResponse servletResponse) throws IOException, IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"employees.csv\"");
        csvExportService.writeEmployeesToCsv(servletResponse.getWriter());
    }
    @Autowired
    CSVService fileService;

    @GetMapping("/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "tutorials.csv";
        InputStreamResource file = new InputStreamResource(fileService.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}
