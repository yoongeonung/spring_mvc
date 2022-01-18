package yoongeonung.springbasic.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yoongeonung.springbasic.AppConfig;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    // DIP위반, 구체와 추상 둘다에 의존하고 있다.
//    private final MemberService memberService = new MemberServiceImpl();

    private MemberService memberService;

    @BeforeEach
    void beforeEach() {
        // 제3자 이용 (AppConfig)
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void save() {
        //given
        Member member = new Member(1L, "Spring", Grade.BASIC);
        //when
        memberService.join(member);
        //then
        Member findMember = memberService.findMember(1L);
        Assertions.assertThat(findMember).isSameAs(member);
        Assertions.assertThat(findMember).isEqualTo(member);
    }

}