package com.hackathon.woofy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hackathon.woofy.entity.Parent;
import com.hackathon.woofy.entity.User;

public interface ParentRepo extends JpaRepository<Parent, Long>{
	Parent findByUser(User user);
	
	@Query("select p from Parent p where p.user.username = :username")
	Parent findByUsername(@Param("username") String username);
}
