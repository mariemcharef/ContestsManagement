package com.cp.Contests_management.Submission;

import lombok.Data;

@Data
//to send to judge0
public class SubmissionRequest {
    private String source_code;
    private int language_id;
    private String stdin;
    private String expected_output;

}
