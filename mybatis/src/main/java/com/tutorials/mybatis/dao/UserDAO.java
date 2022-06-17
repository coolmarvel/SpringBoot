package com.tutorials.mybatis.dao;

import java.util.List;

import com.tutorials.mybatis.dto.UserDTO;

public interface UserDAO {
    List<UserDTO> selectUsers(UserDTO param) throws Exception;
}
