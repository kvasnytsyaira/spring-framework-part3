package com.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Number {

    @Id
    @GeneratedValue
    int id;

    private int secretNumber;

    public Number(int secretNumber) {
        this.secretNumber = secretNumber;
    }
}
