package com.meli.adn.analizer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MeliAdnAnalyzerApplicationTest {

    @Test
    void main() {
        final MeliAdnAnalyzerApplicationTest mainApplication = new MeliAdnAnalyzerApplicationTest();
        Assertions.assertThat(mainApplication).isNotNull();
    }

}