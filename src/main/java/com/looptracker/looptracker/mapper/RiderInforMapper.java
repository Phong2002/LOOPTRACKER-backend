package com.looptracker.looptracker.mapper;
import com.looptracker.looptracker.dto.RiderInforDto;
import com.looptracker.looptracker.entity.RiderInfor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RiderInforMapper  extends BaseMapper<RiderInfor, RiderInforDto>{
}
