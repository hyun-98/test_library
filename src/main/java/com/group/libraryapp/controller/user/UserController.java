package com.group.libraryapp.controller.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@RestController
public class UserController {


    private final JdbcTemplate jdbcTemplate;

    public UserController(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/user")   //user를 저장하기
    public void saveUser(@RequestBody UserCreateRequest request){
        String sql = "INSERT INTO user (name, age) VALUES(?, ?)";
        jdbcTemplate.update(sql, request.getName(), request.getAge());
    }

    @GetMapping("/user")    //user를 받아오기
    public List<UserResponse> getUsers(){
            String sql = "SELECT * FROM user";
            return jdbcTemplate.query(sql, new RowMapper<UserResponse>() {
                @Override
                public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                    long id = rs.getLong("id");
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    return new UserResponse(id, name, age);
                }
            });
    }

    @PutMapping("/user")        //user update
    public void updateUser(@RequestBody UserUpdateRequest request){

        String sql = "UPDATE user SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, request.getName(), request.getId());
    }
    /*
        아직 현재 존재하지 않는 유저를 수정하거나 삭제하더라도 오류 발생이 아닌 정상 작동으로 인지됨
     */

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name){
        String sql = "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(sql, name);
    }

}

/*
    코드 리팩토링에서
    Create
    Read
    Update
    Delete 순으로 정리한다
 */