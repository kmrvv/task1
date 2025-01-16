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
import ru.example.util.BookValidator;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksDAO booksDAO;
    private final PersonDAO personDAO;

    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BooksDAO booksDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.booksDAO = booksDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", booksDAO.showBooks());
        return "books/index";
    }


    @GetMapping("/new")
    public String addBookForm(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        booksDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model) {
        model.addAttribute(booksDAO.getBook(id));
        model.addAttribute(personDAO.showUsers());
//        model.addAttribute(personDAO.getUserByBookId(id));
        Person person = personDAO.getUserByBookId(id);
        if (person != null) model.addAttribute(person);
        return "books/show";
    }

    @PatchMapping("/{id}/addUserId")
    public String addUserId(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        booksDAO.updateUserId(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/deleteUserId")
    public String deleteUserId(@PathVariable("id") int id) {
        booksDAO.deleteUserId(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute(booksDAO.getBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        booksDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        booksDAO.delete(id);
        return "redirect:/books";
    }
}
