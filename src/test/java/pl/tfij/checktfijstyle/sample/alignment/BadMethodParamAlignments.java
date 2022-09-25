package pl.tfij.checktfijstyle.sample.alignment;

public class BadMethodParamAlignments {
    void f1(int p1,
             int p2,
            int p3) {}

    void f2(int p1,
            @Ann("")
             int p2) {}
}
