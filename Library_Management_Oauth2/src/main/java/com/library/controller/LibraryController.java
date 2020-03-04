package com.library.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.UserBookRepository;
import com.model.Book;

@RestController
public class LibraryController 
{
	static Logger log = LogManager.getLogger(LibraryController.class.getName());


	@Autowired
	UserBookRepository bookrepo;

	@GetMapping("/welcome")
	public String welcome() {
		log.info("calling /hello api");
		return "Welcome To Library";
	}
	
	@GetMapping("/availableBook")
	@PreAuthorize("hasAuthority('user')")
	public List<Book> availableBook() 
	{
		log.info("user asking for /availableBook api");
		List<Book> book = bookrepo.findAll();
		log.info("responce = " + book.toString());
		return book;
	}

	@PostMapping("/saveBook")
	@PreAuthorize("hasAuthority('admin')")
	public String saveBook(@RequestBody Book book) 
	{
		log.info("admin asking for /saveBook api");
		Book sbook = bookrepo.save(book);
		return "book is save in library "+sbook;
	}

	@GetMapping(path = "/findByBookName/{bookName}")
	//@PreAuthorize("hasAuthority('user')")
	public List<Book> findByBookName(@PathVariable("bookName") String bookName)
	{
		log.info("user asking for /getBookInfo api");
		List<Book> book = bookrepo.findByBookName(bookName);
		log.info("Book" + book.toString());
		return book;
	}

	@DeleteMapping("/deleteByName/{bookName}")
	@PreAuthorize("hasAuthority('admin')")
	public String deleteByName(@PathVariable("bookName") String bookName)
	{
		log.info("admin asking for /deleteBook api");
		log.info("" + bookName);
		if (bookrepo.deleteByBookname(bookName) == 1) 
		{
			return "Succ";
		} 
		else 
		{
			return "no record found";
		}
	}

	@DeleteMapping("/deleteBook")
	@PreAuthorize("hasAuthority('admin')")
	public String deleteBook(@RequestBody Book book) 
	{
		log.info("admin asking for /deleteBook api");
		bookrepo.delete(book);
		log.info("deleted book =" + book.toString());
		return " Successfully";
	}
}
