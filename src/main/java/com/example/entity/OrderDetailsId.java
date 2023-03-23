package com.example.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderDetailsId implements Serializable {
    private Integer orderNumber;
    private Integer productCode;
}