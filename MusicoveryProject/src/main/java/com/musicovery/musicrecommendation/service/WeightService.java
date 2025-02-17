package com.musicovery.musicrecommendation.service;

import org.springframework.stereotype.Service;

import com.musicovery.musicrecommendation.entity.Weight;
import com.musicovery.musicrecommendation.repository.WeightRepository;

import jakarta.transaction.Transactional;

@Service
public class WeightService {

    private final WeightRepository weightRepository;

    public WeightService(WeightRepository weightRepository) {
        this.weightRepository = weightRepository;
    }

    // 플레이리스트 좋아요 시 가중치 증가
    @Transactional
    public void increaseWeightForLikedPlaylist(String userId, Integer musicId) {
        Weight weight = weightRepository.findByUserIdAndMusicId(userId, musicId)
                .orElse(new Weight(null, userId, musicId, 0));
        weight.setWeightAmount(weight.getWeightAmount() + 1);
        weightRepository.save(weight);
    }

    // 플레이리스트 댓글 시 가중치 증가
    @Transactional
    public void increaseWeightForCommentedPlaylist(String userId, Integer musicId) {
        Weight weight = weightRepository.findByUserIdAndMusicId(userId, musicId)
                .orElse(new Weight(null, userId, musicId, 0));
        weight.setWeightAmount(weight.getWeightAmount() + 2);
        weightRepository.save(weight);
    }

    // 음악 재생 시 가중치 증가
    @Transactional
    public void increaseWeightForPlayedSong(String userId, Integer musicId) {
        Weight weight = weightRepository.findByUserIdAndMusicId(userId, musicId)
                .orElse(new Weight(null, userId, musicId, 0));
        weight.setWeightAmount(weight.getWeightAmount() + 1);
        weightRepository.save(weight);
    }
}
