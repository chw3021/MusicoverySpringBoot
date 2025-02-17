package com.musicovery.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musicovery.post.entity.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
}