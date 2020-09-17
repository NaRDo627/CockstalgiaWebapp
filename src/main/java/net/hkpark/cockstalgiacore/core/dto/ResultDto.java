package net.hkpark.cockstalgiacore.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class ResultDto {
    String message;
    String json;
}
