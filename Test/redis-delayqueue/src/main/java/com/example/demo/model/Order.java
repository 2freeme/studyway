package com.example.demo.model;

import lombok.Data;

import java.io.Serializable;


@Data
public class Order implements Serializable {

    private Long id;

    private String namme;
}
