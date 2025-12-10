package ru.hogwarts.school.Controller;

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
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

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
    private FacultyController facultyController;

    final long facultyId = 999L;
    final String facultyName = "testName";
    final String facultyColor = "testColor";


    @Test
    public void getFacultyInfoTest() throws Exception {

        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);

        when(facultyRepository.findById(Mockito.any())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + facultyId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor));
    }

    @Test
    public void getFacultyOfColorTest() throws Exception {

        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);

        when(facultyRepository.findByColorIgnoreCase(Mockito.anyString())).thenReturn(Collections.singleton(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?color=" + facultyColor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(facultyId))
                .andExpect(jsonPath("$[0].name").value(facultyName))
                .andExpect(jsonPath("$[0].color").value(facultyColor));
    }

    @Test
    public void getFacultyOfNameTest() throws Exception {

        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);

        when(facultyRepository.findByNameIgnoreCase(Mockito.anyString())).thenReturn(Collections.singleton(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?name=" + facultyName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(facultyId))
                .andExpect(jsonPath("$[0].name").value(facultyName))
                .andExpect(jsonPath("$[0].color").value(facultyColor));
    }

    @Test
    public void findAllFacultyInfoTest() throws Exception {

        Faculty f1 = new Faculty();
        f1.setId(facultyId);
        f1.setName(facultyName);
        f1.setColor(facultyColor);

        Faculty f2 = new Faculty();
        f2.setId(123L);
        f2.setName("testFaculty2");
        f2.setColor("testColor2");

        when(facultyRepository.findAll()).thenReturn(List.of(f1, f2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(facultyId))
                .andExpect(jsonPath("$[0].name").value(facultyName))
                .andExpect(jsonPath("$[0].color").value(facultyColor))
                .andExpect(jsonPath("$[1].id").value(123L))
                .andExpect(jsonPath("$[1].name").value("testFaculty2"))
                .andExpect(jsonPath("$[1].color").value("testColor2"));
    }

    @Test
    public void createFacultyTest() throws Exception {

        final long id = 999L;
        final String name = "testName";
        final String color = "testColor";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void editFacultyTest() throws Exception {
        final long id = 999L;
        final String name = "testName";
        final String color = "testColor";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void deleteFacultyTest() throws Exception {

        final long id = 999L;
        final String name = "testName";
        final String color = "testColor";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findById(Mockito.any())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentByFacultyTest() throws Exception {

        Faculty faculty = new Faculty();
        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);

        Student s1 = new Student();
        s1.setId(123L);
        s1.setName("Student_1");
        s1.setAge(11);
        s1.setFaculty(faculty);

        Student s2 = new Student();
        s2.setId(456L);
        s2.setName("Student_2");
        s2.setAge(12);
        s2.setFaculty(faculty);

        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);

        faculty.setStudents(students);
        doReturn(students).when(facultyService).getStudentByFaculty(facultyId);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/getStudentByFaculty/" + facultyId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(s1.getId()))
                .andExpect(jsonPath("$[0].name").value(s1.getName()))
                .andExpect(jsonPath("$[0].age").value(s1.getAge()))
                .andExpect(jsonPath("$[1].id").value(s2.getId()))
                .andExpect(jsonPath("$[1].name").value(s2.getName()))
                .andExpect(jsonPath("$[1].age").value(s2.getAge()));

    }
}
