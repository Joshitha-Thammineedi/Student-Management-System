package com.example.studentmanagementsystem.controller; 
import com.example.studentmanagementsystem.model.Student; 
import com.example.studentmanagementsystem.service.StudentService; 
import jakarta.validation.Valid; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.domain.Page; 
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model; 
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.*; 
import org.springframework.web.servlet.mvc.support.RedirectAttributes; 
import java.util.List; 
@Controller 
public class StudentController { 
    private final StudentService studentService; 
    @Autowired 
    public StudentController(StudentService studentService) { 
        this.studentService = studentService; 
    } 
    @GetMapping("/") 
    public String viewHomePage(Model model,                         @RequestParam(defaultValue 
= "0") int pageNo,                        @RequestParam(defaultValue = "10") int pageSize,                         
@RequestParam(defaultValue = "name") String sortBy,                          
@RequestParam(defaultValue = "asc") String sortDir,                           
@RequestParam(required = false) String keyword) { 
        Page<Student> page; 
        if (keyword != null && !keyword.trim().isEmpty()) { 
            page = studentService.searchStudentsPaginated(keyword, pageNo, pageSize, sortBy, 
sortDir); 
            model.addAttribute("keyword", keyword); 
        } else { 
            page = studentService.getAllStudentsPaginated(pageNo, pageSize, sortBy, sortDir); 
        } 
        List<Student> listStudents = page.getContent(); 
        model.addAttribute("currentPage", pageNo); 
        model.addAttribute("totalPages", page.getTotalPages()); 
        model.addAttribute("totalItems", page.getTotalElements()); 
        model.addAttribute("sortField", sortBy); 
        model.addAttribute("sortDir", sortDir); 
      model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc"); 
        model.addAttribute("listStudents", listStudents); 
        return "index"; 
    @GetMapping("/showNewStudentForm") 
    public String showNewStudentForm(Model model) { 
        Student student = new Student(); 
        model.addAttribute("student", student); 
        return "new_student"; 
    } 
    @PostMapping("/saveStudent") 
    public String saveStudent(@Valid @ModelAttribute("student") Student student 
BindingResult bindingResult, 
         RedirectAttributes redirectAttributes) { 
        if (bindingResult.hasErrors()) { 
            return "new_student"; 
        } 
      studentService.saveStudent(student); 
redirectAttributes.addFlashAttribute("message", "Student added successfully!"); 
        return "redirect:/"; 
    } 
@GetMapping("/showFormForUpdate/{id}") 
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model, 
RedirectAttributes redirectAttributes) { 
        Student student = studentService.getStudentById(id) 
                .orElse(null); 
        if (student == null) { 
redirectAttributes.addFlashAttribute("errorMessage", "Student with ID " + id + " not 
found!"); 
            return "redirect:/"; 
        } 
        model.addAttribute("student", student); 
        return "update_student"; 
    } 
    @PostMapping("/updateStudent") 
    public String updateStudent(@Valid @ModelAttribute("student") Student student,                        
BindingResult bindingResult,                        RedirectAttributes redirectAttributes) { 
        if (bindingResult.hasErrors()) { 
            if (student.getStudentId() == null) {          
redirectAttributes.addFlashAttribute("errorMessage", "Invalid student update request: ID is 
missing."); 
                return "redirect:/"; 
            } 
            return "update_student"; 
        } 
      studentService.saveStudent(student); 
redirectAttributes.addFlashAttribute("message", "Student updated successfully!"); 
        return "redirect:/"; 
    } 
    @GetMapping("/deleteStudent/{id}") 
    public String deleteStudent(@PathVariable(value = "id") long id, RedirectAttributes 
redirectAttributes) { 
        if (studentService.getStudentById(id).isPresent()) { 
    studentService.deleteStudent(id);  redirectAttributes.addFlashAttribute("message", 
"Student with ID " + id + " deleted successfully!"); 
        } else {        redirectAttributes.addFlashAttribute("errorMessage", "Student with ID " + 
id + " not found for deletion."); 
        } 
        return "redirect:/"; 
    } 
} 
