package devops_midterm.service;

import devops_midterm.model.Student;
import devops_midterm.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public Student save(Student student) {
        return repo.save(student);
    }

    public Optional<Student> getById(Long id) {
        return repo.findById(id);
    }

    public List<Student> getAll() {
        return repo.findAll();
    }
}

