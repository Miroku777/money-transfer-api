package com.example.moneytransferapi.mappers;

import com.example.moneytransferapi.dto.UserResponse;
import com.example.moneytransferapi.entityes.EmailData;
import com.example.moneytransferapi.entityes.PhoneData;
import com.example.moneytransferapi.entityes.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {EmailData.class, PhoneData.class, Collectors.class})
public interface UserMapper {

    @Mapping(target = "emails", expression = "java(user.getEmails().stream().map(EmailData::getEmail).collect(Collectors.toList()))")
    @Mapping(target = "phones", expression = "java(user.getPhones().stream().map(PhoneData::getPhone).collect(Collectors.toList()))")
    UserResponse toResponse(User user);
}