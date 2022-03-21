package com.example.bsumts.entity.exam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class QuestionEntity {

    @Id
    @Column(length = 16)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "question")
    private String question;

    @Column(name = "used_kb")
    private Boolean usedKB;

    @Column(name = "used_pi")
    private Boolean usedPI;

    @OneToMany(mappedBy = "question", orphanRemoval = true)
    private List<AnswerEntity> answers;

}
