package com.softcorridor.attendance.service;
import com.softcorridor.attendance.dto.AttendanceResponse;
import com.softcorridor.attendance.dto.WebAddClientDTO;
import com.softcorridor.attendance.dto.WebUpdateClientDTO;
import com.softcorridor.attendance.exception.AccessDeniedException;
import com.softcorridor.attendance.model.Client;
import com.softcorridor.attendance.model.User;
import com.softcorridor.attendance.repository.ClientRepository;
import com.softcorridor.attendance.util.AttendanceReponseUtil;
import com.softcorridor.attendance.util.AttendanceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClientService {
private final ClientRepository clientRepository;

    public AttendanceResponse addClient(WebAddClientDTO webAddClientDTO) throws AccessDeniedException {
        AttendanceResponse attendanceResponse = null;
        User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userPrincipal.getId();
            try {
                Client client = new Client();
                client.setId(UUID.randomUUID().toString());
                client.setName(webAddClientDTO.getName());
                client.setAddress(webAddClientDTO.getAddress());
                client.setCreatedBy(userId);
                client.setCreatedAt(AttendanceUtil.now());
                client.setModifiedAt(AttendanceUtil.now());
                client.setModifiedBy(userId);
                boolean added = clientRepository.add(client);

                if (added) {
                    attendanceResponse = AttendanceReponseUtil.getResponse(200);
                    attendanceResponse.setData(client);
                } else {
                    attendanceResponse = AttendanceReponseUtil.getResponse(400);
                    attendanceResponse.setMessage("Failed to save to DB");
                }

            } catch (Exception e) {
                e.printStackTrace();
                attendanceResponse = AttendanceReponseUtil.getResponse(500);
            }
            return attendanceResponse;

    }


    public AttendanceResponse updateClient(WebUpdateClientDTO webUpdateClientDTO) throws AccessDeniedException {
        AttendanceResponse attendanceResponse = null;
        User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userPrincipal.getId();
            try {
                Client client = new Client();
                client.setId(UUID.randomUUID().toString());
                client.setName(webUpdateClientDTO.getName());
                client.setAddress(webUpdateClientDTO.getAddress());
                client.setModifiedAt(AttendanceUtil.now());
                client.setModifiedBy(userId);
                boolean updated = clientRepository.update(client);
                if (updated) {
                    attendanceResponse = AttendanceReponseUtil.getResponse(200);
                    attendanceResponse.setData(client);
                } else {
                    attendanceResponse = AttendanceReponseUtil.getResponse(400);
                    attendanceResponse.setMessage("Failed to save to DB");
                }
            } catch (Exception e) {
                e.printStackTrace();
                attendanceResponse = AttendanceReponseUtil.getResponse(500);
            }
            return attendanceResponse;

    }

    public AttendanceResponse getAllClient() {
        AttendanceResponse attendanceResponse = null;
        try {
            List<Client> clientList = clientRepository.getAll();
            attendanceResponse = AttendanceReponseUtil.getResponse(200);
            attendanceResponse.setData(clientList);
        } catch (Exception e) {
            e.printStackTrace();
            attendanceResponse = AttendanceReponseUtil.getResponse(500);
        }
        return attendanceResponse;

    }

    public AttendanceResponse deleteClient(String deleteId) throws AccessDeniedException {

        AttendanceResponse attendanceResponse = null;

        User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = userPrincipal.getId();

        try {

            Client client = new Client();
            client.setId(deleteId);
            boolean deleted = clientRepository.delete(client);

            if (deleted) {
                attendanceResponse = AttendanceReponseUtil.getResponse(200);
            } else {
                attendanceResponse = AttendanceReponseUtil.getResponse(400);
            }
        } catch (Exception e) {
            e.printStackTrace();
            attendanceResponse = AttendanceReponseUtil.getResponse(500);
        }


        return attendanceResponse;
    }


}
