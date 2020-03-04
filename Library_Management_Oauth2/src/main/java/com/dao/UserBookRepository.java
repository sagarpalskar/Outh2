package com.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.model.Book;
@Repository
public interface UserBookRepository extends JpaRepository<Book, Integer>  
{
	List<Book> findByBookName(String bookname);
	
	@Transactional
	@Modifying
	@Query(value="delete from Book where bookname =?1",nativeQuery=true)
	int deleteByBookname(String bookname);
}
