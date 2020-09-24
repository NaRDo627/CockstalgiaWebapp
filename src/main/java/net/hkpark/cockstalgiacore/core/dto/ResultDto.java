package net.hkpark.cockstalgiacore.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Builder
@Getter
public class ResultDto {
    @JsonProperty("message")
    @Builder.Default
    String message = "OK";

    @JsonProperty("data")
    Object data;
}
