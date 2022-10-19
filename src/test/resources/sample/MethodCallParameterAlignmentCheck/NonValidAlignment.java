package pl.tfij.checktfijstyle.sample.paramsLinesInCall;

class SampleClass extends SuperClass {
    public static void main(String[] args) {
        SampleClass.foo(1,

                2, 3);

        foo(1,
                 2,
                3);

        new Bar("a",
              "b", "c");
    }

    SampleClass() {
        super(1,
                2, 3);
    }

    SampleClass(int a, int b, int c) {
        super(1,
                2, 3);
    }

    SampleClass(int a, int b,
              int c, int d) {
        this(1,
                2,
                3);
    }

    static void foo(int a,
                 int b, int c) { }

    void x() {
        this.y(1,
                2);

        y(1,
                2);
    }

    void y(int a, int b) { }

}