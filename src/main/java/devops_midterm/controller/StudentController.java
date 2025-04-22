package devops_midterm.controller;

import devops_midterm.model.Student;
import devops_midterm.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return ResponseEntity.ok(service.save(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<Student> getAll() {
        return service.getAll();
    }


    @GetMapping("/status")
    public ResponseEntity<String> getStatus() throws IOException {
        Path path = Paths.get("health.log"); // I intentionally do not write the full path
        if (!Files.exists(path)) {
            return ResponseEntity.status(404).body("No health log found.");
        }
        List<String> lines = Files.readAllLines(path);
        if (lines.isEmpty()) {
            return ResponseEntity.ok("Health log is empty.");
        }
        String lastLine = lines.get(lines.size() - 1);
        return ResponseEntity.ok(lastLine);
    }

}

