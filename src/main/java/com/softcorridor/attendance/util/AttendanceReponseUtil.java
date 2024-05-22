package com.softcorridor.attendance.util;

import com.softcorridor.attendance.dto.AttendanceResponse;

public class AttendanceReponseUtil {
    public static AttendanceResponse getResponse(int code) {
        switch (code) {
            case 200:
                return new AttendanceResponse(code, "OK", "Successful", "{}");
            case 400:
                return new AttendanceResponse(code, "BAD REQUEST", "Bad Request", "{}");
            case 403:
                return new AttendanceResponse(code, "UNAUTHORIZED", "Unauthorized or Invalid token", "{}");
            case 404:
                return new AttendanceResponse(code, "NOT FOUND", "Entity not found", "{}");
            case 500:
                return new AttendanceResponse(code, "INTERNAL SERVER ERROR", "Error occurred while processing", "{}");
            default:
                return new AttendanceResponse(code, "UNKNOWN CODE", "UNKNOWN", "{}");
        }

    }

}
