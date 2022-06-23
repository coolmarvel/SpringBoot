package com.example.shoppingmall.dto;

import com.example.shoppingmall.constant.ItemSellStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {

    private String searchDateType;
    private ItemSellStatus searchSellStatus;
    private String searchBy;
    private String searchQuery = "";
    
    public String getSearchDateType() {
        return searchDateType;
    }
    public void setSearchDateType(String searchDateType) {
        this.searchDateType = searchDateType;
    }
    public ItemSellStatus getSearchSellStatus() {
        return searchSellStatus;
    }
    public void setSearchSellStatus(ItemSellStatus searchSellStatus) {
        this.searchSellStatus = searchSellStatus;
    }
    public String getSearchBy() {
        return searchBy;
    }
    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }
    public String getSearchQuery() {
        return searchQuery;
    }
    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    
}
