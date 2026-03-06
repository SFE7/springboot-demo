package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Person;
import com.example.demo.Forms.PersonForm;

@Controller
@RequestMapping("/person")
public class PersonController {

    // Liste statique de personnes (en mémoire, pour ce TP)
    private static List<Person> persons = new ArrayList<>();

    // Initialiser avec quelques données
    static {
        persons.add(new Person("Albert", "Einstein"));
        persons.add(new Person("Marie", "Curie"));
        persons.add(new Person("Stephen", "Hawking"));
    }

    // Injecter les messages depuis application.properties
    @Value("${welcome.message}")
    private String welcomeMessage;

    @Value("${error.message}")
    private String errorMessage;

    // Action 1 : Afficher la page d'accueil
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("message", welcomeMessage);
        return "pages/person/index";
    }

    // Action 2 : Afficher la liste des personnes
    @GetMapping("/list")
    public String personList(Model model) {
        model.addAttribute("persons", persons);
        return "pages/person/personList";
    }





    // Action 3 : Afficher le formulaire d'ajout (GET)
    @GetMapping("/add")
    public String showAddPersonPage(Model model) {
        PersonForm personForm = new PersonForm();
        model.addAttribute("personForm", personForm);
        return "pages/person/addPerson";
    }

    // Action 4 : Traiter l'ajout d'une personne (POST)
    @PostMapping("/add")
    public String savePerson(
            Model model,
            @ModelAttribute("personForm") PersonForm personForm
    ) {
        String firstName = personForm.getFirstName();
        String lastName = personForm.getLastName();

        // Validation simple
        if (firstName != null && firstName.trim().length() > 0 &&
                lastName != null && lastName.trim().length() > 0) {

            // Créer une nouvelle personne et l'ajouter à la liste
            Person newPerson = new Person(firstName, lastName);
            persons.add(newPerson);

            // Rediriger vers la liste
            return "redirect:/person/list";
        }

        // En cas d'erreur, afficher le message d'erreur
        model.addAttribute("errorMessage", errorMessage);
        return "pages/person/addPerson";
    }

    // Action 5 : Supprimer une personne
    @GetMapping("/delete/{index}")
    public String deletePerson(@PathVariable int index) {
        if (index >= 0 && index < persons.size()) {
            persons.remove(index);
        }
        return "redirect:/person/list";
    }

    // Action 6 : Afficher le formulaire d'édition (GET)
    @GetMapping("/edit/{index}")
    public String editPersonForm(@PathVariable int index, Model model) {
        if (index >= 0 && index < persons.size()) {
            Person person = persons.get(index);
            person.setIndex(index);
            model.addAttribute("person", person);
            return "pages/person/editPerson";
        }
        return "redirect:/person/list";
    }

    // Action 7 : Traiter la modification d'une personne (POST)
    @PostMapping("/update")
    public String updatePerson(@ModelAttribute Person person) {
        if (person.getIndex() >= 0 && person.getIndex() < persons.size()) {
            persons.set(person.getIndex(), person);
        }
        return "redirect:/person/list";
    }
}