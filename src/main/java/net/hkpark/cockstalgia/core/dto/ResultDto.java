package net.hkpark.cockstalgia.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResultDto {
    @JsonProperty("message")
    @Builder.Default
    String message = "OK";

    @JsonProperty("data")
    Object data;
}
