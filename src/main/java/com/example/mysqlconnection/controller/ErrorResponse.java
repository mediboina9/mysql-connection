package com.example.mysqlconnection.controller;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class ErrorResponse {
    private String name;
    private int id;

}
