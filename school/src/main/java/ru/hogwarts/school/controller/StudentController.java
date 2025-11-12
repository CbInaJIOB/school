package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping()              //POST  создать студента
    public Student createStudents(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")     //GET получить студента по id
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping                 //GET   получить всех студентов
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PutMapping()               //PUT   редактировать студента
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")      //DELETE    удалить студента
    public Student deleteStudent(@PathVariable long id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("/filter/{age}")     //GET фильтр студента по возрасту

    public Collection<Student> getStudentOfAge(@PathVariable int age) {
        return studentService.filterStudentOfAge(age);
    }
}
