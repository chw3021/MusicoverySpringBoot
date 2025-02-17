package com.musicovery.music.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "music")
public class Music {

    @Id
    private String musicId;  // 음악 ID (Spotify에서 가져온 값)

    private String title;  // 음악 제목
}
