package com.github.users.controllers;

import com.github.users.dto.response.UserInfoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/users")
public interface IUserController {

    @GetMapping("/info/all")
    List<UserInfoResponse> findUserInfoAll();

    @GetMapping("/info/{id}")
    UserInfoResponse findUserInfoById(@PathVariable String id);

}
