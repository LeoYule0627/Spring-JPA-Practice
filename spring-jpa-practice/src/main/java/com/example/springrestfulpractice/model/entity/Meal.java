package com.example.springrestfulpractice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "meal")
public class Meal {
    @Id
    @Column
    private int id;
    @Column(name="name", table = "meal")
    private String name;
    @Column(name="price", table = "meal")
    private int price;
    @Column(name="description", table = "meal")
    private String description;
}
