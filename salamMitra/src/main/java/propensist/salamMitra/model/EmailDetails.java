package propensist.salamMitra.model;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
 
    private String recipient;
    private String messageBody;
    private String subject;
}