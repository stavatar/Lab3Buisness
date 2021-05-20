package com.repository;


import com.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long>
{
    Users findByLogin(String login);

}