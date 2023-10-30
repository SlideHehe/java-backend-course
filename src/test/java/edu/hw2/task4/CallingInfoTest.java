package edu.hw2.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.*;

public class CallingInfoTest {
    @DisplayName("1) Проверка callingInfo")
    @Test
    void exampleName() {
        // given
        String expectedClassName = this.getClass().getName();
        String expectedMethodName = "exampleName";

        // when
        CallingInfo callingInfo = Utils.callingInfo();

        // then
        assertThat(callingInfo.className()).isEqualTo(expectedClassName);
        assertThat(callingInfo.methodName()).isEqualTo(expectedMethodName);
    }

    @DisplayName("2) Проверка callingInfo")
    @Test
    void testMethod() {
        // given
        String expectedClassName = this.getClass().getName();
        String expectedMethodName = "testMethod";

        // when
        CallingInfo callingInfo = Utils.callingInfo();

        // then
        assertThat(callingInfo.className()).isEqualTo(expectedClassName);
        assertThat(callingInfo.methodName()).isEqualTo(expectedMethodName);
    }
}
