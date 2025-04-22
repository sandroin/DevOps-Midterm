package devops_midterm;

import devops_midterm.model.Student;
import devops_midterm.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = DevopsMidtermApplication.class)
@AutoConfigureMockMvc
public class StudentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDatabase() {
        studentRepository.deleteAll();
    }

    @Test
    @DisplayName("Should add a new student successfully")
    public void testAddStudent() throws Exception {
        Student student = new Student();
        student.setName("Sandro");
        student.setEmail("sandro@gmail.com");

        mockMvc.perform(post("/students/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Sandro"))
                .andExpect(jsonPath("$.email").value("sandro@gmail.com"));
    }

    @Test
    @DisplayName("Should retrieve all students")
    public void testGetAllStudents() throws Exception {
        studentRepository.save(new Student(null, "Saba", "saba@gmail.com"));
        studentRepository.save(new Student(null, "Gio", "gio@gmail.com"));

        mockMvc.perform(get("/students/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", anyOf(is("Saba"), is("Gio"))))
                .andExpect(jsonPath("$[1].email", anyOf(is("saba@gmail.com"), is("gio@gmail.com"))));
    }

    @Test
    @DisplayName("Should return student by ID")
    public void testGetStudentById() throws Exception {
        Student student = studentRepository.save(new Student(null, "Ana", "ana@gmail.com"));

        mockMvc.perform(get("/students/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ana"))
                .andExpect(jsonPath("$.email").value("ana@gmail.com"));
    }

    @Test
    @DisplayName("Should return 404 for non-existing student")
    public void testGetStudentById_NotFound() throws Exception {
        mockMvc.perform(get("/students/9999"))
                .andExpect(status().isNotFound());
    }
}
