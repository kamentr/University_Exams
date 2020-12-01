package com.example.btcscammerswallets.business.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import lombok.Data;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BTCTransactionResponse {

    String address;
    Long total_received;
    Long total_sent;
    Long final_balance;
    List<Tx> txs;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Tx {
        List<Input> inputs;
        List<Out> out;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Input {
        Prev_out prev_out;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Prev_out {
        boolean spent;
        int type;
        String addr;
        Long value;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Out {
        boolean spent;
        int type;
        String addr;
        Long value;
    }
}