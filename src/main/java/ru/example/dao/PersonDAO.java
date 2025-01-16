package ru.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.example.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> showUsers() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getUser(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM person where user_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public Optional<Person> getUser(String name) {
         return jdbcTemplate.query("SELECT * FROM person where name=?", new Object[]{name},
                 new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(name, year_of_birth) VALUES(?, ?)",
                person.getName(), person.getYearOfBirth());
    }

    public void update(int id, Person personUpdate) {
        jdbcTemplate.update("UPDATE person SET name=?, year_of_birth=? WHERE user_id=?",
                personUpdate.getName(), personUpdate.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE user_id=?", id);
    }

    public Person getUserByBookId(int id) {
        return jdbcTemplate.query("SELECT person.user_id, person.name, person.year_of_birth FROM person JOIN book " +
                        "on person.user_id = book.user_id WHERE book.book_id=?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }
}
