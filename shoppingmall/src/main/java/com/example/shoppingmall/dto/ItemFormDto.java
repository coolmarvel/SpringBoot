package com.example.shoppingmall.dto;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import com.example.shoppingmall.constant.ItemSellStatus;
import com.example.shoppingmall.entity.Item;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemName;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stock;

    private ItemSellStatus itemSellStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(String itemDetail) {
        this.itemDetail = itemDetail;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public ItemSellStatus getItemSellStatus() {
        return itemSellStatus;
    }

    public void setItemSellStatus(ItemSellStatus itemSellStatus) {
        this.itemSellStatus = itemSellStatus;
    }

    public List<ItemImgDto> getItemImgDtoList() {
        return itemImgDtoList;
    }

    public void setItemImgDtoList(List<ItemImgDto> itemImgDtoList) {
        this.itemImgDtoList = itemImgDtoList;
    }

    public List<Long> getItemImgIds() {
        return itemImgIds;
    }

    public void setItemImgIds(List<Long> itemImgIds) {
        this.itemImgIds = itemImgIds;
    }

    public static ModelMapper getModelMapper() {
        return modelMapper;
    }

    public static void setModelMapper(ModelMapper modelMapper) {
        ItemFormDto.modelMapper = modelMapper;
    }

    // 상품 수정 시 사용되는 멤버변수들
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();
    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    // DTO -> Entity
    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

    // Entity -> DTO
    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }
}
