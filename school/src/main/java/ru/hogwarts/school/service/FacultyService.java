package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {

    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty findFaculty(Long id) {
        return facultyRepository.findById(id).get();
    }

    public Collection<Faculty> findAllFacultyInfo() {
        return facultyRepository.findAll();
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getFacultyOfColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public Collection<Faculty> getFacultyOfName(String name) {
        return facultyRepository.findByNameIgnoreCase(name);
    }

//    public List<Student> getStudentByFaculty(Long id) {
//        return findFaculty(id).getStudents();
//    }

    public List<Student> getStudentByFaculty(Long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow();
        return faculty.getStudents();
    }
}
