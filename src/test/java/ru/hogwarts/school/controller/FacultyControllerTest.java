package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controllers.FacultyController;
import ru.hogwarts.school.models.Faculty;

import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.services.AvatarService;
import ru.hogwarts.school.services.FacultyService;
import ru.hogwarts.school.services.StudentService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private AvatarRepository avatarRepository;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private AvatarService avatarService;

    @SpyBean
    private FacultyService facultyService;

    @SpyBean
    private StudentService studentService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void getFaculty() throws Exception {
        final Long id = 1L;
        final String name = "test";
        final String color = "testColor";

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void addAndDeleteTest() throws Exception {
        final Long id = 1L;
        final String name = "test";
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
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty/")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));


        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/1"))
                .andExpect(status().isOk());
        verify(facultyRepository, atLeastOnce()).deleteById(1L);


    }

    @Test
    public void getAll() throws Exception {
        final Long id = 1L;
        final String name = "test";
        final String color = "testColor";

        final Long id2 = 2L;
        final String name2 = "test2";
        final String color2 = "testColor2";


        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        Faculty faculty2 = new Faculty();
        faculty2.setId(id2);
        faculty2.setName(name2);
        faculty2.setColor(color2);


        when(facultyRepository.findAll()).thenReturn(List.of(faculty, faculty2));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(faculty, faculty2))));
    }


    @Test
    public void findByColorOrName() throws Exception {

        final Long id = 1L;
        final String name = "test";
        final String color = "testColor";

        final Long id2 = 2L;
        final String name2 = "test2";
        final String color2 = "testColor2";


        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        Faculty faculty2 = new Faculty();
        faculty2.setId(id2);
        faculty2.setName(name2);
        faculty2.setColor(color2);



        when(facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(anyString(), anyString())).thenReturn(List.of(faculty, faculty2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .queryParam("colorOrName", color)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(faculty, faculty2))));
    }
}
