package com.cupk.consultant.servie;

import com.cupk.consultant.mapper.ReservationMapper;
import com.cupk.consultant.pojo.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    @Autowired
    private ReservationMapper reservationMapper;
    //添加预约
    public  void insert(Reservation reservation){
        reservationMapper.insert(reservation);
    }
    //查询预约
    public Reservation findByPhone(String phone){
        return reservationMapper.findByPhone(phone);
    }
}
