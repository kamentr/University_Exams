package com.example.btcscammerswallets.business.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;


import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class BTCAccountResponse {
    public Long final_balance;
    public Long n_tx;
    public Long total_received;
}
