Package com.example.studentmanagementsystem.model; 
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 
import jakarta.validation.constraints.Email; 
import jakarta.validation.constraints.Min; 
import jakarta.validation.constraints.NotBlank; 
import jakarta.validation.constraints.NotNull; 
import jakarta.validation.constraints.Pattern; 
import jakarta.validation.constraints.Size; 
import lombok.AllArgsConstructor; 
import lombok.Data; 
import lombok.NoArgsConstructor; 
@Entity 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Student { 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long studentId; 
    @NotBlank(message = "Name cannot be empty") 
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") 
    private String name; 
    @NotNull(message = "Age cannot be empty") 
    @Min(value = 5, message = "Age must be at least 5") 
    private Integer age; 
    @NotBlank(message = "Class cannot be empty") 
    @Size(min = 1, max = 50, message = "Class must be between 1 and 50 characters") 
    private String studentClass; 
    @NotBlank(message = "Email cannot be empty") 
    @Email(message = "Email should be valid") 
    private String email; 
    @NotBlank(message = "Address cannot be empty") 
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters") 
    private String address; 
    public Student(String name, Integer age, String studentClass, String email, String address) 
{ 
this.name = name; 
this.age = age; 
this.studentClass = studentClass; 
this.email = email; 
this.address = address; 
} 
}
