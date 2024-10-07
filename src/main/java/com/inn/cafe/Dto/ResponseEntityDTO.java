package com.inn.cafe.Dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseEntityDTO {

    private String message;

    private Object status;

    private Object data;
}
