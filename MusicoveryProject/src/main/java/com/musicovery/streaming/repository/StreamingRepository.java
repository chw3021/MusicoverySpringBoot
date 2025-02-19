package com.musicovery.streaming.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicovery.streaming.entity.Streaming;

public interface StreamingRepository extends JpaRepository<Streaming, Long> {
    List<Streaming> findByIsLiveTrue(); // 현재 라이브 중인 스트리밍 목록 가져오기
}