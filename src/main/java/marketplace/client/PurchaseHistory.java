package marketplace.client;

import lombok.Data;

@Data
public class PurchaseHistory {

    private String id;
    private String clientId;
    private String storeId;
    private String productId;

}
