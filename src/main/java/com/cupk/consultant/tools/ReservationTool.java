package com.cupk.consultant.tools;

import com.cupk.consultant.pojo.Reservation;
import com.cupk.consultant.servie.ReservationService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ReservationTool {
    @Autowired
    private ReservationService reservationService;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Tool("添加考生预约信息")
    public void addReservation(
            @P("考生姓名") String name,
            @P("考生性别") String gender,
            @P("考生电话") String phone,
            @P("考生时间(格式:yyyy-MM-dd HH:mm:ss)") String time,
            @P("考生省份") String province,
            @P("考生估分") String estimatedScore
    ) {
        Reservation reservation = new Reservation(
                null, name, gender, phone, LocalDateTime.parse(time, TIME_FORMATTER), province, Integer.parseInt(estimatedScore)
        );
        reservationService.insert(reservation);
    }
    @Tool("根据考生电话查询考生预约信息")
    public Reservation findReservationByPhone(@P("考生电话") String phone) {
        return reservationService.findByPhone(phone);
    }
}
