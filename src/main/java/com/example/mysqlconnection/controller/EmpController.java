package com.example.mysqlconnection.controller;

import com.example.mysqlconnection.repo.EmpCrudRepo;
import com.example.mysqlconnection.service.EmpService;
import com.example.mysqlconnection.repo.RepoJpa;
import com.example.mysqlconnection.model.EMP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

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
        logger.debug(" this is debug log ...for getAllEmp()");
        logger.error(" This is error log ...for getAllEmp()");
        logger.trace("This is trace log ...for getAllEmp()....");
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
        logger.info("getAll method info log ...for getAllEmpFindByName().......");
        logger.debug(" this is debug log ...for getAllEmpFindByName()...");
        logger.error(" This is error log ...for getAllEmpFindByName() ...");
        logger.trace("This is trace log ...for getAllEmpFindByName()....");
        Iterable<EMP> iterable= empCrudRepo.findByName(name);
        logger.info("findAll()...",iterable);
        ArrayList<EMP> bookingDetailsArrayList=new ArrayList<>();
        for(EMP i:iterable){
            bookingDetailsArrayList.add(i);
        }
        return bookingDetailsArrayList;
    }
    @GetMapping("/{name}/{id}")
    public List<EMP> getAllEmpFindByNameOrId(@PathVariable String name,@PathVariable int id){
        logger.info("getAll method info..log ...for getAllEmpFindByNameOrId()........");
        logger.debug(" this is debug log ...for getAllEmpFindByNameOrId()...");
        logger.error(" This is error log ...for getAllEmpFindByNameOrId()......");
        logger.trace("This is trace log ...for getAllEmpFindByNameOrId().......");
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
    public void createEmp(@RequestBody EMP emp){
        logger.info("RequestBody.....",emp.getName());
        System.out.println(emp.getName());
        if(emp.getName().equals(null)){
            emp.setName("DefaultName");
            logger.info("DefaultName setted for this emp..log ...for create new Emp()...");
        }
        else {
            logger.info("name not null", emp.getName());
        }

        empCrudRepo.save(emp);
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
}
