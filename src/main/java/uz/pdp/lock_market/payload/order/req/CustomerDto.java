package uz.pdp.lock_market.payload.order.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.lock_market.entity.OrderDetail;

@Data
@NoArgsConstructor
public class CustomerDto {
    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String phone;

    @NotBlank
    private String email;

    public CustomerDto(OrderDetail orderDetail) {
        this.name = orderDetail.getName();
        this.surname = orderDetail.getSurname();
        this.phone = orderDetail.getPhone();
        this.email = orderDetail.getEmail();
    }
}
