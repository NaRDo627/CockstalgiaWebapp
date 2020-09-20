package net.hkpark.cockstalgiacore.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.hkpark.cockstalgiacore.core.entity.Member;

import javax.persistence.Column;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberDto {
    public static MemberDto fromEntity(Member member) {
        return MemberDto.builder()
                .userNo(member.getUserNo())
                .id(member.getId())
                .password(member.getPassword())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .build();
    }

    @JsonProperty("userNo")
    private Integer userNo;

    @JsonProperty("id")
    private String id;

    @JsonProperty("password")
    private String password;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    public Member toEntity() {
        return Member.builder()
                .id(this.id)
                .password(this.password)
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .build();
    }
}
