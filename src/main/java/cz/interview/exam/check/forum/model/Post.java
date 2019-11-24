package cz.interview.exam.check.forum.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {

    private int id;

    private String title;
}
