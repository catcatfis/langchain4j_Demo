package com.cupk.consultant;

import com.cupk.consultant.pojo.Reservation;
import com.cupk.consultant.servie.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;
    //测试预约服务
    @Test
    void testInsert() {
        Reservation reservation =new Reservation(null, "张三", "男", "12345678901", LocalDateTime.now(), "北京", 5);
        reservationService.insert(reservation);
    }
    @Test
    void testFindByPhone() {
        String phone = "12345678901";
        Reservation reservation = reservationService.findByPhone(phone);
        System.out.println(reservation);
    }
}
