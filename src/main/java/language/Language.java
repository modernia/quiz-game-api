package language;

import io.modernia.question.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class Language {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String logo;

    @ManyToOne
    private Question[] question;

}
