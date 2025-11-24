package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")     //GET получить факультет по id
    public Faculty getFacultyInfo(@PathVariable Long id) {
        return facultyService.findFaculty(id);
    }

    @GetMapping                 //GET получить всех факультетов
    public Collection<Faculty> getAllFaculty() {
        return facultyService.findAllFacultyInfo();
    }

    @PostMapping()              //POST  создать факультет
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }


    @PutMapping()               //PUT редактировать факультет
    public Faculty editFaculty(@RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }

    @DeleteMapping("{id}")      //DELETE    удалить факультет
    public void deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
    }

    @GetMapping("/filter")     //GET фильтр факультета по цвету
    public Collection<Faculty> getFacultyOfColor(@RequestParam("color") String color) {
        return facultyService.filterFacultyOfColor(color);
    }

}
