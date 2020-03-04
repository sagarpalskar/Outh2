package com.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> 
{

	User findByuname(String uname);
}
