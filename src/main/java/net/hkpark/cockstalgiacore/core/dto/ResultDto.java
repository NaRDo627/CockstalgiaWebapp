package net.hkpark.cockstalgiacore.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ResultDto {
    @JsonProperty("message")
    String message;

    @JsonProperty("data")
    Object data;
}
