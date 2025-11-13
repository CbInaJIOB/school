package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl {
    private final HashMap<Long, Faculty> facultys = new HashMap<>();
    private static long lastId = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++lastId);
        facultys.put(lastId, faculty);
        return faculty;
    }

    public Faculty findFaculty(long id) {
        return facultys.get(id);
    }

    public Collection<Faculty> getAllFaculty() {
        return facultys.values();
    }

    public Faculty editFaculty(Faculty faculty) {
        if (facultys.containsKey(faculty.getId())) {
            facultys.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty deleteFaculty(long id) {
        return facultys.remove(id);
    }

    public Collection<Faculty> filterFacultyOfColor(String color) {
//        return facultys.values().stream().filter(faculty -> faculty.getColor() == color).collect(Collectors.toList());
        return getAllFaculty().stream().filter(e -> e.getColor().equals(color)).collect(Collectors.toList());

    }
}
