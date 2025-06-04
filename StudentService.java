package com.example.studentmanagementsystem.service; 
import com.example.studentmanagementsystem.model.Student; 
import com.example.studentmanagementsystem.repository.StudentRepository; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.PageRequest; 
import org.springframework.data.domain.Pageable; 
import org.springframework.data.domain.Sort; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional; 
import java.util.Optional; 
@Service 
@Transactional 
public class StudentService { 
    private final StudentRepository studentRepository; 
    @Autowired 
    public StudentService(StudentRepository studentRepository) { 
        this.studentRepository = studentRepository; 
    } 
    public Page<Student> getAllStudentsPaginated(int pageNo, int pageSize, String sortBy, 
String sortDir) 
{ 
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? 
Sort.by(sortBy).ascending() : 
Sort.by(sortBy).descending(); 
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort); 
        Return 
studentRepository.findAll(pageable); 
    } 
    public Optional<Student> getStudentById(Long id) { 
        return studentRepository.findById(id); 
    } 
    public Student saveStudent(Student student) { 
        return studentRepository.save(student); 
    } 
    public void deleteStudent(Long id) { 
        studentRepository.deleteById(id); 
    } 
    public Page<Student> searchStudentsPaginated(String keyword, int pageNo, int pageSize, 
String sortBy, String sortDir) { 
        Sort sort = 
sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending() : 
Sort.by(sortBy).descending(); 
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort); 
        return 
studentRepository.findByNameContainingIgnoreCaseOrStudentClassContainingIgnoreCase(
 keyword, keyword, pageable); 
    } 
} 
