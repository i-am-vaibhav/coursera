package com.coursera.vo;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ChangePassword(BigDecimal id,String oldPassword,String newPassword) {
}
