package com.example.mysqlconnection.repo;

import com.example.mysqlconnection.model.EMP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpCrudRepo extends CrudRepository<EMP,Integer> {

    public List<EMP> findByName(String name);

    @Query("select e from EmpSql as e where e.name=:name or e.id=:id")
    public List <EMP> findByNameOrId(@Param("name") String name,@Param("id") int id);

   /* @Modifying
    @Query(value = "update EmpSql as e set e.name=? where e.id=?", nativeQuery=true)
    public List <EMP> updateNameById( String name, Integer id);*/

    @Query("select e from EmpSql as e ")
    public List <EMP> findAllEmps(Sort sort);

    @Query("select e from EmpSql as e Order by name")
    public Page<EMP> findAllEmpsPage(Pageable pageable);


}
