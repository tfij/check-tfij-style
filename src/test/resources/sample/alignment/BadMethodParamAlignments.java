package pl.tfij.checktfijstyle.sample.alignment;

public class BadMethodParamAlignments {
    BadMethodParamAlignments(int p1,
                              int p2,
                             int p3) { }

    void f1(int p1,
             int p2,
            int p3) {}

    void f2(int p1,
            @Ann("")
             int p2) {}

    void f3(int p1,
             int p2) {}

    public record ValidRecordClass(String a,
                                    String b,
                                   String c) {}
}
