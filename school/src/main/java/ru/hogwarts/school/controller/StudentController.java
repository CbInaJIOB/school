package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
    }

    @PostMapping()              //POST  создать студента
    public Student createStudents(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")     //GET получить студента по id
    public Student getStudentInfo(@PathVariable Long id) {
        return studentService.findStudent(id);
    }

    @GetMapping                 //GET   получить всех студентов
    public Collection<Student> getAllStudents(@RequestParam(defaultValue = "0") int min,
                                              @RequestParam(defaultValue = "0") int max) {
        if (min != 0 && max != 0) {
            return studentService.findByAgeBetween(min, max);
        }
        return studentService.getAllStudents();
    }

    @PutMapping()               //PUT   редактировать студента
    public Student editStudent(@RequestBody Student student) {
        return studentService.editStudent(student);
    }

    @DeleteMapping("{id}")      //DELETE    удалить студента
    public void deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/filter/{age}")     //GET фильтр студента по возрасту
    public Collection<Student> getStudentOfAge(@PathVariable int age) {
        return studentService.findByAge(age);
    }

    @GetMapping("/studentFaculty/{id}")
    public String findStudentFaculty(Long id) {
        return studentService.facultyStudent(id);
    }
}
