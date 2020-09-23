package net.hkpark.cockstalgiacore.chatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Skill Request에서 사용자에 대한 정보를 userRequest.user에 담아서 제공하고 있습니다.
 */
@Getter
@NoArgsConstructor
public class UserDto {
    /**
     * 사용자를 식별할 수 있는 key로 최대 70자의 값을 가지고 있습니다.
     * 이 값은 특정한 bot에서 사용자를 식별할 때 사용할 수 있습니다.
     * 동일한 사용자더라도, 봇이 다르면 다른 id가 발급됩니다.
     */
    @JsonProperty("id")
    private String id;

    /**
     * 현재는 botUserKey만 제공합니다.
     */
    @JsonProperty("type")
    private String type;

    /**
     * 추가적으로 제공하는 사용자의 속성 정보입니다.
     */
    @JsonProperty("properties")
    private UserPropertiesDto properties;
}
