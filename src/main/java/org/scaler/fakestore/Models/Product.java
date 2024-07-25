package org.scaler.fakestore.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class Product extends BaseModel  {


    private  String title;
    private double price;
    private String description;
    private String image;
    @ManyToOne
    private Category category;

}
