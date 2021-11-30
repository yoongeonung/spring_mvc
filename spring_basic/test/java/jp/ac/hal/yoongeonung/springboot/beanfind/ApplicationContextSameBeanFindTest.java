package jp.ac.hal.yoongeonung.springboot.beanfind;

import jp.ac.hal.yoongeonung.springboot.member.MemberRepository;
import jp.ac.hal.yoongeonung.springboot.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ApplicationContextSameBeanFindTest {

    private final ApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회시 같은 타입의 빈이 둘 이상이면 중복 오류 발생")
    void findDuplicatedBeanByType() {
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("특정 타입의 빈을 모두 조회")
    void findAllBeanBySpecificType() {
        // map으로 나온다.
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        beansOfType.forEach((key, value) -> {
            assertThat(beansOfType).containsKeys(key);
            assertThat(beansOfType).containsValue(value);
        });
        assertThat(beansOfType.size()).isEqualTo(2);
    }

    @Configuration
    static class SameBeanConfig {
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
}