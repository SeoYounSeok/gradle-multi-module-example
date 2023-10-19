package com.example.utils.generator;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UniqueIdGeneratorTest {
    
    @Test
    @DisplayName("UUID 유효성 테스트")
    void test_generator_uuid() {
        String uuid = UniqueIdGenerator.uuid();

        assertThat(uuid).isInstanceOf(String.class);
        assertThat(uuid).hasSize(36);
    }

    @Test
    @DisplayName("UUID 중복 생성 테스트")
    void test_uuid_duplicate() {
        String uuid1 = UniqueIdGenerator.uuid();
        String uuid2 = UniqueIdGenerator.uuid();

        assertThat(uuid1).isNotEqualTo(uuid2);
    }

}
