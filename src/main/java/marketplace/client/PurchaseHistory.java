package marketplace.client;

import lombok.Data;

@Data
public class PurchaseHistory {

    private Integer id;
    private Integer clientId;
    private Integer productId;
    private Integer amount;
}
