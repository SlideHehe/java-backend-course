package edu.hw2.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.*;

public class CallingInfoTest {
    @DisplayName("1) Проверка callingInfo")
    @Test
    void exampleName() {
        CallingInfo callingInfo = Utils.callingInfo();

        assertThat(callingInfo.className()).isEqualTo(this.getClass().getName());
        assertThat(callingInfo.methodName()).isEqualTo("exampleName");
    }

    @DisplayName("2) Проверка callingInfo")
    @Test
    void testMethod() {
        CallingInfo callingInfo = Utils.callingInfo();

        assertThat(callingInfo.className()).isEqualTo(this.getClass().getName());
        assertThat(callingInfo.methodName()).isEqualTo("testMethod");
    }
}
