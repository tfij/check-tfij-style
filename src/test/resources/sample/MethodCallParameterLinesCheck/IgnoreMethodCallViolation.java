package pl.tfij.checktfijstyle.sample.paramsLinesInCall;

class SampleClass extends SuperClass {

    void x() {
        Map.of("a", 1,
            "b", 2);

        Map.of("a", 1, "b", 2);

        List.of("a", "b",
                "c", "d");

        of("a", 1,
                "b", 2);

        abc("a", 1,
                "b", 2);

        new Foo("a", 1,
                "b", 2);

        new Bar("a", 1,
                "b", 2);
    }

}