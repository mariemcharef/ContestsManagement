package com.cp.Contests_management;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ApiResponse {//to return data from frontend
    private String message;
    private Object data;
}
