package ru.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.example.models.Book;
import ru.example.models.Person;

import java.util.List;

@Component
public class BooksDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BooksDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> showBooks() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBook(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(user_id, name, author, year) VALUES (?, ?, ?, ?)",
                null, book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book bookUpdate) {
        jdbcTemplate.update("UPDATE book set name=?, author=?, year=? WHERE book_id=?",
                bookUpdate.getName(), bookUpdate.getAuthor(), bookUpdate.getYear(), id);
    }

    // change UPDATE book set user_id=2 WHERE book_id=2
    public void updateUserId(int id, Book bookUpdate) {
        jdbcTemplate.update("UPDATE book set user_id=? WHERE book_id=?",
                bookUpdate.getUserId(), id);
    }

    public void deleteUserId(int id) {
        jdbcTemplate.update("UPDATE book set user_id=? WHERE book_id=?",
                null, id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book where book_id=?", id);
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT book_id, book.user_id, book.name, author, year FROM person JOIN book" +
                " on person.user_id = book.user_id where person.user_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }

}
