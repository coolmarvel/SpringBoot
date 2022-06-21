package com.example.shop.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "This field is required!")
    private String name;

    @NotNull(message = "This field is required!")
    @PositiveOrZero(message = "Quantity can't be negative")
    private Integer quantity;

    @PositiveOrZero(message = "Price can't be negative")
    private Float price;

    @Column(name = "image")
    @Lob
    private String imageBase64;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;

    @NotNull
    @ManyToOne
    private Brand brand;

    @NotNull
    @ManyToOne
    private Category category;

    @ManyToOne
    private Type type;

    @ManyToMany
    private List<Ingredient> ingredients;

    public Product(Long id,
            String name,
            Float price,
            Integer quantity,
            Brand brand,
            Category category,
            Type type,
            List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
        this.category = category;
        this.type = type;
        this.ingredients = ingredients;
    }

    public Product(Long id,
            String name,
            Float price,
            Integer quantity,
            Brand brand,
            Category category,
            Type type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
        this.category = category;
        this.type = type;
    }

    public Product(Long id,
            String name,
            Integer quantity,
            Float price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Product() {
    }

    public Product(Long id, @NotNull(message = "This field is required!") String name,
            @PositiveOrZero(message = "Price can't be negative") Float price,
            @NotNull(message = "This field is required!") @PositiveOrZero(message = "Quantity can't be negative") Integer quantity,
            @NotNull Brand brand,
            @NotNull Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
        this.category = category;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
