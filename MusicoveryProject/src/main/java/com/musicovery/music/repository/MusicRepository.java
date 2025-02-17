package com.musicovery.music.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.music.entity.Music;

public interface MusicRepository extends JpaRepository<Music, String> {

    // musicId로 음악을 찾는 메서드
    Optional<Music> findByMusicId(String musicId);
}