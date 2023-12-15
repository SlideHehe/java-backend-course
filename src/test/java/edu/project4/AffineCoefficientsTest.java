package edu.project4;

import edu.project4.components.AffineCoefficients;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AffineCoefficientsTest {
    @Test
    @DisplayName("Создание коэффициентов с null вместо random")
    void createNullArg() {
        assertThatThrownBy(() -> AffineCoefficients.create(null))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Создание массива коэффициентов с null вместо random")
    void createArrayNullArg() {
        assertThatThrownBy(() -> AffineCoefficients.createArray(null, 5))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Создание массива коэффициентов с недопустимым количеством элементов")
    void createTestArrayIllegalSize() {
        assertThatThrownBy(() -> AffineCoefficients.createArray(new Random(), -1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Num of elements can't be less than 1");
    }
}
