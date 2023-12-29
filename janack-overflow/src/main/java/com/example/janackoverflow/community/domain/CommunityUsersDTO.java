package com.example.janackoverflow.community.domain;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityUsersDTO {
    private Long id;
    private String nickname;
    private String profileImage;
}
