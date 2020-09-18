package net.hkpark.cockstalgiacore.chatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class ActionDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("clientExtra")
    private Map<String, Object> clientExtra;

    @JsonProperty("params")
    private Map<String, Object> params;

    @JsonProperty("id")
    private String id;

    @JsonProperty("detailParams")
    private Map<String, Object> detailParams;
}
