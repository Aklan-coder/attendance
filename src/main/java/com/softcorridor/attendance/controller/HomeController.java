package com.softcorridor.attendance.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**********************************************************
 2024 Copyright (C),  Soft Corridor Limited                                         
 https://www.softcorridor.com | info@softcorridor.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Date      : 10/05/2024
 * Time      : 16:53
 * Project   : attendance
 * Package   : com.softcorridor.attendance.controller
 **********************************************************/
@RestController
public class HomeController {
    @RequestMapping("/")
    public void redirectHome(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
}
