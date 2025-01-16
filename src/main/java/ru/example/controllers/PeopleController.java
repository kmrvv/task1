package ru.example.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.dao.BooksDAO;
import ru.example.dao.PersonDAO;
import ru.example.models.Book;
import ru.example.models.Person;
import ru.example.util.PersonValidator;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final BooksDAO booksDAO;

    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, BooksDAO booksDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.booksDAO = booksDAO;
        this.personValidator = personValidator;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.showUsers());
        return "people/index";
    }

    @GetMapping("/new")
    public String addPersonForm(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String addPerson(@ModelAttribute("person") @Valid Person person,
                            BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new";
        }

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute(personDAO.getUser(id));
        List<Book> bookList = booksDAO.getBooksByPersonId(id);
        if (!bookList.isEmpty()) model.addAttribute(bookList);
        return "people/show";
    }

   @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute(personDAO.getUser(id));
        return "people/edit";
   }

   @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDAO.update(id, person);
        return "redirect:/people";
   }

   @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
   }
}
