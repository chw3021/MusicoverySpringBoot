package com.musicovery.admin.service;

//import com.musicovery.user.entity.User; // 🚨 User 엔티티가 제공될 때 활성화
//import com.musicovery.admin.repository.UserRepository;
import org.springframework.stereotype.Service;
//import java.util.List;

@Service
public class UserManagementServiceImpl implements UserManagementService {
//    private final UserRepository userRepository;

//    public UserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

//    @Override
//    public List<User> getAllUsers() {
//        // 🚨 User 테이블이 제공되면 주석 해제
//        // return userRepository.findAll();
//        return null; // TODO: User 테이블이 추가되면 기능 활성화
//    }
//
//    @Override
//    public User getUserById(String userId) {
//        // 🚨 User 테이블이 제공되면 주석 해제
//        // return userRepository.findById(userId)
//        //         .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userId));
//        return null; // TODO: User 테이블이 추가되면 기능 활성화
//    }

	@Override
	public boolean toggleUserStatus(String userId) {
		// 🚨 User 테이블이 제공되면 주석 해제
		// User user = getUserById(userId);
		// user.setActive(!user.isActive()); // 계정 상태 토글
		// userRepository.save(user);
		// return true;
		return false; // TODO: User 테이블이 추가되면 기능 활성화
	}

	@Override
	public boolean deleteUser(String userId) {
		// 🚨 User 테이블이 제공되면 주석 해제
		// if (!userRepository.existsById(userId)) return false;
		// userRepository.deleteById(userId);
		// return true;
		return false; // TODO: User 테이블이 추가되면 기능 활성화
	}
}
