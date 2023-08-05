package practice.aws_practice;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter @Getter
public class Test {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
