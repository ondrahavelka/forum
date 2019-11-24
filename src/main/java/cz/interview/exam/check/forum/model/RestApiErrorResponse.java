package cz.interview.exam.check.forum.model;

import lombok.Data;

@Data
public class RestApiErrorResponse {

    private String signature;

    private String errorText;
}
