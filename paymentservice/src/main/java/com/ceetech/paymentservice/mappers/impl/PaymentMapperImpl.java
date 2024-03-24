package com.ceetech.paymentservice.mappers.impl;

import org.modelmapper.ModelMapper;

import com.ceetech.paymentservice.dto.PaymentDto;
import com.ceetech.paymentservice.entity.PaymentEntity;
import com.ceetech.paymentservice.mappers.Mapper;

public class PaymentMapperImpl implements Mapper<PaymentEntity, PaymentDto> {

    private ModelMapper modelMapper;

    public PaymentMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentDto mapToDto(PaymentEntity paymentEntity) {
        return modelMapper.map(paymentEntity, PaymentDto.class);
    }

    @Override
    public PaymentEntity mapToEntity(PaymentDto paymentDto) {
        return modelMapper.map(paymentDto, PaymentEntity.class);
    }

}
