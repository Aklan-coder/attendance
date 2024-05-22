package com.softcorridor.attendance.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**********************************************************
 2023 Copyright (C),  Soft Corridor Limited                                         
 https://www.softcorridor.com                                        
 **********************************************************
 * Author    : Soft Corridor
 * Date      : 27/09/2023
 * Time      : 23:17
 * Project   : lims
 * Package   : com.softcorridor.lims.utility
 **********************************************************/
public class ValueMapper {


    public static String jsonAsString(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
