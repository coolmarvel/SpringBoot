package com.example.shoppingmall.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.shoppingmall.dto.ItemSearchDto;
import com.example.shoppingmall.dto.MainItemDto;
import com.example.shoppingmall.entity.Item;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    // @QueryProjection 을 이용하여 바로 Dto 객체 반환
    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
