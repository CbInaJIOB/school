package ru.hogwarts.school.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentService studentService;
    @SpyBean
    private AvatarService avatarService;
    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private StudentController studentController;

    final long idStudent = 999L;
    final int ageStudent = 999;
    final String nameStudent = "Дункан Маклауд";

    @Test
    public void editStudentTest() throws Exception {    // тест на редактирование студента

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", idStudent);
        studentObject.put("age", ageStudent);
        studentObject.put("name", nameStudent);

        Student student = new Student();
        student.setId(idStudent);
        student.setAge(ageStudent);
        student.setName(nameStudent);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idStudent))
                .andExpect(jsonPath("$.age").value(ageStudent))
                .andExpect(jsonPath("$.name").value(nameStudent));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + idStudent)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idStudent))
                .andExpect(jsonPath("$.age").value(ageStudent))
                .andExpect(jsonPath("$.name").value(nameStudent));
    }

    @Test
    public void createStudentTest() throws Exception {  // проверка на создание студента

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", idStudent);
        studentObject.put("age", ageStudent);
        studentObject.put("name", nameStudent);

        Student student = new Student();
        student.setId(idStudent);
        student.setAge(ageStudent);
        student.setName(nameStudent);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idStudent))
                .andExpect(jsonPath("$.age").value(ageStudent))
                .andExpect(jsonPath("$.name").value(nameStudent));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + idStudent)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idStudent))
                .andExpect(jsonPath("$.age").value(ageStudent))
                .andExpect(jsonPath("$.name").value(nameStudent));
    }

    @Test
    public void findStudentTest() throws Exception {    // проверка на получение студента по идентификатору
        JSONObject studentObject = new JSONObject();
        studentObject.put("id", idStudent);
        studentObject.put("age", ageStudent);
        studentObject.put("name", nameStudent);

        Student student = new Student();
        student.setId(idStudent);
        student.setAge(ageStudent);
        student.setName(nameStudent);

        when(studentRepository.findById(Mockito.any())).thenReturn(of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + idStudent)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idStudent))
                .andExpect(jsonPath("$.name").value(nameStudent))
                .andExpect(jsonPath("$.age").value(ageStudent));
    }

    @Test
    public void getAllStudentsTest() throws Exception { // проверка на получение всех студентов
        Student s1 = new Student();
        s1.setId(idStudent);
        s1.setName(nameStudent);
        s1.setAge(ageStudent);

        Student s2 = new Student();
        s2.setId(999);
        s2.setName("testName2");
        s2.setAge(999);

        when(studentRepository.findAll()).thenReturn(List.of(s1, s2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(idStudent))
                .andExpect(jsonPath("$[0].name").value(nameStudent))
                .andExpect(jsonPath("$[0].age").value(ageStudent))
                .andExpect(jsonPath("$[1].id").value(999))
                .andExpect(jsonPath("$[1].name").value("testName2"))
                .andExpect(jsonPath("$[1].age").value(999));
    }

    @Test
    public void deleteStudentTest() throws Exception {  // проверка на удаление студента
        JSONObject studentObject = new JSONObject();
        studentObject.put("id", idStudent);
        studentObject.put("age", ageStudent);
        studentObject.put("name", nameStudent);

        Student student = new Student();
        student.setId(idStudent);
        student.setAge(ageStudent);
        student.setName(nameStudent);

        when(studentRepository.findById(Mockito.any())).thenReturn(of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + idStudent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void facultyStudentTest() throws Exception { //проверка на нахождение факультета студента

        Faculty faculty = new Faculty();
        faculty.setId(999L);
        faculty.setName("Hogwarts");
        faculty.setColor("red");

        Student student = new Student();
        student.setId(idStudent);
        student.setAge(ageStudent);
        student.setName(nameStudent);
        student.setFaculty(faculty);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/studentFaculty/" + idStudent)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    public void findByAgeTest() throws Exception {
        Student s1 = new Student();
        s1.setId(idStudent);
        s1.setName(nameStudent);
        s1.setAge(ageStudent);

        Student s2 = new Student();
        s2.setId(999);
        s2.setName("testName2");
        s2.setAge(999);

        Student s3 = new Student();
        s3.setId(123);
        s3.setName("testName3");
        s3.setAge(123);

        when(studentRepository.findByAge(anyInt())).thenReturn(List.of(s1, s2, s3));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/filter/" + idStudent)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(s1.getId()))
                .andExpect(jsonPath("$[0].name").value(s1.getName()))
                .andExpect(jsonPath("$[0].age").value(s1.getAge()))
                .andExpect(jsonPath("$[1].id").value(s2.getId()))
                .andExpect(jsonPath("$[1].name").value(s2.getName()))
                .andExpect(jsonPath("$[1].age").value(s2.getAge()));
    }

    @Test
    public void findByAgeBetweenTest() throws Exception {
        Student s1 = new Student();
        s1.setId(idStudent);
        s1.setName(nameStudent);
        s1.setAge(ageStudent);

        Student s2 = new Student();
        s2.setId(888);
        s2.setName("testName2");
        s2.setAge(888);

        Student s3 = new Student();
        s3.setId(123);
        s3.setName("testName3");
        s3.setAge(123);

        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(List.of(s2, s3));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student?min=100&max=900")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].id").value(s2.getId()))
                .andExpect(jsonPath("$[0].name").value(s2.getName()))
                .andExpect(jsonPath("$[0].age").value(s2.getAge()))

                .andExpect(jsonPath("$[1].id").value(s3.getId()))
                .andExpect(jsonPath("$[1].name").value(s3.getName()))
                .andExpect(jsonPath("$[1].age").value(s3.getAge()));
    }
}
