package cn.medcn.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by lixuan on 2017/8/23.
 */
@Data
@NoArgsConstructor
public class TestAccountDTO {

    protected Integer id;

    protected String username;

    protected String linkman;

    protected String mobile;

    protected String hospital;

    protected Date registDate;

    protected String province;

    protected String city;


}
