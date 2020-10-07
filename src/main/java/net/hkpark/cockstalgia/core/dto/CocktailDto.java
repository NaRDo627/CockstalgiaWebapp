package net.hkpark.cockstalgia.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.hkpark.cockstalgia.core.annotation.ExcludeMapping;
import net.hkpark.cockstalgia.core.annotation.MappingDateFormat;
import net.hkpark.cockstalgia.core.constant.LiquorType;
import net.hkpark.cockstalgia.core.entity.Member;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CocktailDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cocktailNo")
    private Integer cocktailNo;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("simpleRecipe")
    private String simpleRecipe;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @MappingDateFormat
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("regDate")
    private String regDate;

    @MappingDateFormat
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("modDate")
    private String modDate;

    @JsonProperty("base")
    private LiquorType base;
}
