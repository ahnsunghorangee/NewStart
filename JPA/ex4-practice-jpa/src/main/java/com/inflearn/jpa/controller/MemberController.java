package com.inflearn.jpa.controller;

import javax.annotation.PostConstruct;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.inflearn.jpa.dto.MemberDto;
import com.inflearn.jpa.entity.Member;
import com.inflearn.jpa.repo.MemberRepo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberRepo memberRepo;

	// [JPA 실전] 12. Web 확장 - 순수 JPA
	@GetMapping("/members/{id}")
	public String findMember(@PathVariable("id") Long id) {
		Member member = memberRepo.findById(id).get();
		return member.getUsername();
	}

	// [JPA 실전] 12. Web 확장 - 도메인 클래스 컨버터
	@GetMapping("/members2/{id}")
	public String findMember2(@PathVariable("id") Member member) {
		return member.getUsername();
	}

	// [JPA 실전] 13. 페이징과 정렬 + MemberDto로 변환
	@GetMapping("/members")
	public Page<MemberDto> list(@PageableDefault(size = 3, sort = "username") Pageable pageable) {
		Page<Member> page = memberRepo.findAll(pageable);
		
		// map을 하면 Member를 바꿀 수 있다.
		// 반환타입이 Member에서 MemberDto로 바뀐다.
		Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
		
		return map;
	}
	
	// [JPA 실전] 13. DTO에서 Entity 참조하기
	@GetMapping("/members2")
	public Page<MemberDto> list2(@PageableDefault(size = 3, sort = "username") Pageable pageable) {
		return memberRepo.findAll(pageable)
				.map(MemberDto::new);
	}

	@PostConstruct
	public void init() {
//		memberRepo.save(new Member("userA"));

		for (int i = 0; i < 100; i++) {
			memberRepo.save(new Member("user" + i, i));
		}
	}
}
