package az.practice.bookstore.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto  {
    private String country;
    private String streetName;
    private String houseNumber;
    private String postCode;
}
