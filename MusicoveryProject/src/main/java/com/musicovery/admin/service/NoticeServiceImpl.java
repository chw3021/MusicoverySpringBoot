package com.musicovery.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musicovery.admin.entity.Notice;
import com.musicovery.admin.repository.NoticeRepository;

@Service
public class NoticeServiceImpl implements NoticeService {
	private final NoticeRepository noticeRepository;

	public NoticeServiceImpl(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}

	@Override
	public List<Notice> getAllNotices() {
		return noticeRepository.findAll();
	}

	@Override
	public Notice createNotice(Notice notice) {
		return noticeRepository.save(notice);
	}

	@Override
	public boolean deleteNotice(Long noticeId) {
		if (!noticeRepository.existsById(noticeId))
			return false;
		noticeRepository.deleteById(noticeId);
		return true;
	}
}
