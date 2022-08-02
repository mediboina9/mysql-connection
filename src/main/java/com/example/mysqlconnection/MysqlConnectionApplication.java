package com.example.mysqlconnection;

import com.example.mysqlconnection.model.EMP;
import com.example.mysqlconnection.repo.EmpCrudRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MysqlConnectionApplication implements CommandLineRunner {
@Autowired
EmpCrudRepo empCrudRepo;
	public static void main(String[] args) {
		SpringApplication.run(MysqlConnectionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		EMP obj=new EMP(1,"siva");
		EMP obj1=new EMP(2,"satya");

		empCrudRepo.save(obj);
		empCrudRepo.save(obj1);
	}
}
