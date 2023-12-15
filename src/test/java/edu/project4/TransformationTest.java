package edu.project4;

import edu.project4.components.Point;
import edu.project4.transformations.DiskTransformation;
import edu.project4.transformations.LinearTransformation;
import edu.project4.transformations.PolarTransformation;
import edu.project4.transformations.SinusoidalTransformation;
import edu.project4.transformations.SphericalTransformation;
import edu.project4.transformations.SwirlTransformation;
import edu.project4.transformations.Transformation;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TransformationTest {
    static Stream<Arguments> transformationProvider() {
        return Stream.of(
            Arguments.of(new SphericalTransformation()),
            Arguments.of(new PolarTransformation()),
            Arguments.of(new LinearTransformation()),
            Arguments.of(new SinusoidalTransformation()),
            Arguments.of(new SwirlTransformation()),
            Arguments.of(new DiskTransformation())
        );
    }

    @ParameterizedTest
    @MethodSource("transformationProvider")
    @DisplayName("Передача null в apply")
    void transformationNullInput(Transformation transformation) {
        // expect
        assertThatThrownBy(() -> transformation.apply(null))
            .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @MethodSource("transformationProvider")
    @DisplayName("Проверка что методы apply возвращают не пустой объект, и x y - инициализированы")
    void transformationReturnsNotNull(Transformation transformation) {
        // given
        Point point = new Point(1.0, 1.0);

        // when
        Point transformedPoint = transformation.apply(point);

        // then
        assertThat(transformedPoint).isNotNull();
        assertThat(transformedPoint.x()).isNotEqualTo(0.0);
        assertThat(transformedPoint.y()).isNotEqualTo(0.0);
    }

}
