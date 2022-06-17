package com.tutorials.mybatis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor // 자동으로 모든 배개변수를 받는 생성자를 생성
@Data // Getter Setter 생성
@Getter
@Setter
public class UserDTO {

    private int seq;
    private String name;
    private String country;

    public UserDTO(int i, Object object, String country2) {
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
