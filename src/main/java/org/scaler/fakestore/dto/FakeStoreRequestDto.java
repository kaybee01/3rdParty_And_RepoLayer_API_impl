package org.scaler.fakestore.dto;

import lombok.Data;
import org.scaler.fakestore.Models.Category;

@Data
public class FakeStoreRequestDto {
    private String title;
    private double price;
    private  String desc;
    private String category;
    private String image;
}
