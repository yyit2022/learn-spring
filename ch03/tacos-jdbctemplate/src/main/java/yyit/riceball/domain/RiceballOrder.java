package yyit.riceball.domain;


import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 饭团订单
 * @author yyit
 */
@Data
public class RiceballOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Date placedAt;

    @NotBlank(message="收货人姓名为必填项")
    private String deliveryName;

    @NotBlank(message="街道为必填项")
    private String deliveryStreet;

    @NotBlank(message="城市为必填项")
    private String deliveryCity;

    @NotBlank(message="省份为必填项")
    private String deliveryState;

    @NotBlank(message="邮政编号为必填项")
    private String deliveryZip;

    @CreditCardNumber(message="无效的信用卡号")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message="必须格式化为 MM/YY")
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="无效 CVV")
    private String ccCVV;


    private List<Riceball> riceballs = new ArrayList<>();

    public void addRiceBall(Riceball ball) {
        this.riceballs.add(ball);
    }

}
