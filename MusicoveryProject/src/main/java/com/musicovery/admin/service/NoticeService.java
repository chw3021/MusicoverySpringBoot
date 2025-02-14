package com.musicovery.admin.service;

import java.util.List;

import com.musicovery.admin.entity.Notice;

public interface NoticeService {
	List<Notice> getAllNotices();

	Notice createNotice(Notice notice);

	boolean deleteNotice(Long noticeId);
}
