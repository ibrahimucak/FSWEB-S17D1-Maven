package com.workintech.fswebs17d1.controller;

import com.workintech.fswebs17d1.entity.Animal;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/workintech/animal") // Tüm endpointlerin ortak yolu
public class AnimalController {
    private Map<Integer, Animal> animals;

    @Value("${project.developer.full-name}")
    private String developerName;

    @Value("${course.name}")
    private String courseName;

    @PostConstruct
    public void loadAll() {
        System.out.println("PostConstruct çalıştı!");
        this.animals = new HashMap<>();
        this.animals.put(1, new Animal(1, "monomer")); // Örnek bir hayvan verisi
    }

    @GetMapping("/config")
    public String getCustomConfigValues() {
        return developerName + " ------ " + courseName;
    }

    @GetMapping
    public List<Animal> getAnimals() {
        System.out.println("Tüm hayvanlar listelendi.");
        return new ArrayList<>(animals.values());
    }

    @GetMapping("/{id}")
    public Animal getAnimal(@PathVariable("id") int id) {
        if (id < 0) {
            System.out.println("Geçersiz ID: " + id);
            return null; // Geçersiz durumda bir hata yanıtı döndürmek daha iyi olur
        }
        return this.animals.get(id);
    }

    @PostMapping
    public String addAnimal(@RequestBody Animal animal) {
        if (animals.containsKey(animal.getId())) {
            return "Bu ID'ye sahip hayvan zaten mevcut!";
        }
        System.out.println("Hayvan eklendi: " + animal);
        this.animals.put(animal.getId(), animal);
        return "Hayvan başarıyla eklendi!";
    }

    @PutMapping("/{id}")
    public String updateAnimal(@PathVariable("id") int id, @RequestBody Animal newAnimal) {
        if (!animals.containsKey(id)) {
            return "Güncellenecek ID bulunamadı!";
        }
        this.animals.replace(id, newAnimal);
        return "Hayvan güncellendi: " + newAnimal;
    }

    @DeleteMapping("/{id}")
    public String deleteAnimal(@PathVariable("id") int id) {
        if (!animals.containsKey(id)) {
            return "Silinecek hayvan bulunamadı!";
        }
        this.animals.remove(id);
        return "Hayvan silindi. ID: " + id;
    }
}
