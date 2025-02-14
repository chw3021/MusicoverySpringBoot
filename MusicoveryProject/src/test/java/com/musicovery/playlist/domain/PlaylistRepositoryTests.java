package com.musicovery.playlist.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.musicovery.playlist.repository.PlaylistRepository;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class PlaylistRepositoryTests {
	
	@Setter(onMethod_= @Autowired)
	private PlaylistRepository playlistRepository;
	
	
	//테이블에 값 추가되는지 test
	//@Test
	public void playlistInsertTest() {
		Playlist playlist = new Playlist();
		playlist.setPlaylistTitle("예시로 만든 첫 플레이리스트입니다.");
		playlist.setPlaylistComment("장르는 발라드로, 노래방 가기 전 들었으면 하는 플리를 만들어 보았습니다.");
		playlist.setPlaylistPhoto("");
		playlist.setUserId("추예진@gmail.com");
		
		log.info("플리 테이블에 첫번째 데이터 입력");
		playlistRepository.save(playlist);
		
		
		
		Playlist playlist2 = new Playlist();
		playlist2.setPlaylistTitle("예시로 만든 두번째 플레이리스트입니다.");
		playlist2.setPlaylistComment("장르는 인디로, 버스에서 들었으면 하는 플리를 만들어 보았습니다.");
		playlist2.setPlaylistPhoto("");
		playlist2.setUserId("구운감자@gmail.com");
		
		log.info("플리 테이블에 두번째 데이터 입력");
		playlistRepository.save(playlist2);
		
		
		
		
		Playlist playlist3 = new Playlist();
		playlist3.setPlaylistTitle("예시로 만든 세번째 플레이리스트입니다.");
		playlist3.setPlaylistComment("삭제용 플리입니다.");
		playlist3.setPlaylistPhoto("");
		playlist3.setUserId("구운고구마@gmail.com");
		
		log.info("플리 테이블에 세번째 데이터 입력");
		playlistRepository.save(playlist3);
	}
	
	
	//플리 전체 레코드 수 구하는 test
	//@Test
	public void playlistCountTest() {
		long playlistCount = playlistRepository.count();
		log.info(String.valueOf(playlistCount));
	}
	
	//플리 리스트(해당 타입 모든 인스턴스 반환)
	//@Test
	public void playlistListTest() {
		List<Playlist> playlistList = (List<Playlist>) playlistRepository.findAll();
		for(Playlist playlist : playlistList) {
			log.info(playlist.toString());
		}
	}

	//플리 상세조회
	//@Test
	public void playlistDetailTest() {
		Optional<Playlist> playlistOptional = playlistRepository.findById(1L);
		if(playlistOptional.isPresent()) {
			Playlist playlist = playlistOptional.get();
			log.info(playlist.toString());
		}
	}

	
	//플리 수정
	//@Test
	public void playlistUpdateTest() {
		Optional<Playlist> playlistOptional = playlistRepository.findById(2L);
		
		if(playlistOptional.isPresent()) {
			Playlist playlist = playlistOptional.get();
			
			playlist.setPlaylistTitle("수정이 된 플레이리스트입니다.");
			playlist.setPlaylistComment("장르는 힙합으로, 버스에서 내렸을 때 들을 플리를 만들어보았습니다.");
			playlist.setUserId("탄감자@gmail.com");
			
			log.info("플리 테이블 데이터 수정 완료.");
			playlistRepository.save(playlist);
		}
	}
	
	//플리 삭제
	//@Test
	public void playlistDeleteTest() {
		playlistRepository.deleteById(3L);
		
	}
}
