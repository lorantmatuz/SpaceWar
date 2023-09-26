package hu.elte.inf.szofttech2023.team3.spacewar;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MonotonicSourceTest {

    @Test
    void test() {
    	String hello = "Hello";
    	assertThat(hello).isNotEqualTo("World");
    }
    
}