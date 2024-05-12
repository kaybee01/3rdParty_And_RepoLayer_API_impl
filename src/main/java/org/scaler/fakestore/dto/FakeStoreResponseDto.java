package org.scaler.fakestore.dto;

import lombok.Data;
import org.scaler.fakestore.Models.Category;

@Data
public class FakeStoreResponseDto {

    public Long id;
    private  String title;
    private double price;
    private String desc;
    private String image;
    private String category;
}
