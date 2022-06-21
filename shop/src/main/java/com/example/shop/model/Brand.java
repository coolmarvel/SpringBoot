package com.example.shop.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Column(name = "image")
    @Lob
    private String imageBase64;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;

    @ManyToMany
    private List<Category> categories;

    public Brand() {
    }

    public Brand(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Brand(Long id, String name, List<Product> products, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.products = products;
        this.categories = categories;
    }

    public Brand(Long id, String name, String imageBase64, List<Product> products, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.imageBase64 = imageBase64;
        this.products = products;
        this.categories = categories;
    }

    public Brand(Long id, String name, String imageBase64) {
        this.id = id;
        this.name = name;
        this.imageBase64 = imageBase64;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
