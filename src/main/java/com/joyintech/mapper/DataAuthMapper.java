package com.joyintech.mapper;


import java.util.List;
import java.util.Map;


public interface DataAuthMapper {
    List<Object> selectByPage(Map<?, ?> map);
    
    int selectCount(Map<?, ?> map);

}