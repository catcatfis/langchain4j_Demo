package com.cupk.consultant.mapper;

import com.cupk.consultant.pojo.Reservation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReservationMapper {
    //添加预约
    @Insert("INSERT INTO reservation (name, gender, phone, communication_time, province, estimated_score) VALUES (#{name}, #{gender}, #{phone}, #{communicationTime}, #{province}, #{estimatedScore})")
    void insert(Reservation reservation);
    //查询预约
    @Select("SELECT * FROM reservation WHERE id = #{id}")
    Reservation findById(Long id);
    //根据手机号查询预约
    @Select("SELECT * FROM reservation WHERE phone = #{phone}")
    Reservation findByPhone(String phone);
}
