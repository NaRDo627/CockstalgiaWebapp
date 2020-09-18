package net.hkpark.cockstalgiacore.chatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class UserRequestDto {
    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("params")
    private Map<String, Object> params;

    @JsonProperty("block")
    private BlockDto block;

    @JsonProperty("utterance")
    private String utterance;

    @JsonProperty("lang")
    private String lang;

    @JsonProperty("user")
    private UserDto user;
}
