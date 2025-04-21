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
        student.setName("Alice");
        student.setEmail("alice@example.com");

        mockMvc.perform(post("/students/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    @DisplayName("Should retrieve all students")
    public void testGetAllStudents() throws Exception {
        studentRepository.save(new Student(null, "Bob", "bob@example.com"));
        studentRepository.save(new Student(null, "Carol", "carol@example.com"));

        mockMvc.perform(get("/students/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", anyOf(is("Bob"), is("Carol"))))
                .andExpect(jsonPath("$[1].email", anyOf(is("bob@example.com"), is("carol@example.com"))));
    }

    @Test
    @DisplayName("Should return student by ID")
    public void testGetStudentById() throws Exception {
        Student student = studentRepository.save(new Student(null, "Dave", "dave@example.com"));

        mockMvc.perform(get("/students/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dave"))
                .andExpect(jsonPath("$.email").value("dave@example.com"));
    }

    @Test
    @DisplayName("Should return 404 for non-existing student")
    public void testGetStudentById_NotFound() throws Exception {
        mockMvc.perform(get("/students/9999"))
                .andExpect(status().isNotFound());
    }
}
