package com.example.mysqlconnection.model;

import javax.persistence.*;

@Entity(name = "EmpSql")

public class EMP {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
   // @Column(insertable = false)
    private String name;

    public EMP(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public EMP() {
    }

    @Override
    public String toString() {
        return "EMP{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
