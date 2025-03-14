package com.musicovery.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musicovery.admin.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
