package com.example.btcscammerswallets.business.data.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BTCAccountHistory {
    private int id;
    private String address;
    private String balance;
    private String scamScore;
}
