package io.modernia.question;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import language.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    private String question;
    @Nullable
    private String code;
    private String answer;
    private List<String> choices;
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Language languageId;
}