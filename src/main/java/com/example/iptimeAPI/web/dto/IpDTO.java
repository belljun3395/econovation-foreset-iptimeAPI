package com.example.iptimeAPI.web.dto;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import javax.validation.constraints.Pattern;

@Data
public class IpDTO {

    @ApiParam(example = "168.131.35.101")
    @Pattern(regexp = "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])", message = "IP 주소 형식이 올바르지 않습니다")
    private String ip;

}