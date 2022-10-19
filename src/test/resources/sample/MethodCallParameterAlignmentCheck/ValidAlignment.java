package pl.tfij.checktfijstyle.sample.paramsLinesInCall;

import java.math.BigDecimal;
import java.util.Optional;

class SampleClass extends SuperClass {
    public static void main(String[] args) {
        SampleClass.foo(1, 2, 3);

        foo(1,
            2,

            3);

        foo(1, 2,
            3);

        new Bar();

        new Bar("a", "b", "c");

        Bar bar = new Bar(
                "a",
                "b",
                "c");

        Optional.of(1).map(BigDecimal::new);
        Optional.of(1).map(String::valueOf);

        fin12(
                new Class1(getOverrideId()),
                getActionTime().atZone(DB_ZONE).toInstant(),
                "sample-string"
        );

        fin12(
                new Class1(getOverrideId()),
                getActionTime()
                        .atZone(DB_ZONE).toInstant(),
                "sample-string"
        );
    }

    SampleClass() {
        super(1, 2, 3);
    }

    SampleClass(int a, int b, int c) {
        super(1, 2, 3);
    }

    SampleClass(int a, int b, int c, int d) {
        this(1, 2, 3);
    }

    static void foo(int a, int b, int c) { }

    void x() {
        this.y(1, 2, 3);

        y(1, 2, 3);
        y(1,
          2,
          3);

        abc();
        abc(1);
    }

    void y(int a, int b, int c) { }

}