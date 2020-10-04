package net.hkpark.cockstalgia.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.hkpark.cockstalgia.core.annotation.MappingName;
import net.hkpark.cockstalgia.core.entity.Member;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberDto {
    public static MemberDto fromEntity(Member member) {
        return MemberDto.builder()
                .userNo(member.getUserNo())
                .name(member.getName())
                .build();
    }

    @JsonProperty("userNo")
    private Integer userNo;

    @JsonProperty("name")
    private String name;

    public Member toEntity() {
        return Member.builder()
                .name(this.name)

                .build();
    }
}
