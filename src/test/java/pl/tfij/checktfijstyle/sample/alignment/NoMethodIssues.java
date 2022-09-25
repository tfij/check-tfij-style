package pl.tfij.checktfijstyle.sample.alignment;

public class NoMethodIssues {

    public static void f1(int p1, int p2, int p3,
                          int p4, int p5,
                          int p6) {}

    static void f2(int p1,
                   int p2,
                   int p3,
                   int p4) {}

    private static void f3(
            int p1,
            int p2,
            int p3,
            int p4
    ) {}

    void f4(@Ann("first param") int p1,
            int p2) {}

    void f5(
            @Ann("p1") int p1,
            @Ann("p2 p2") int p2,
            @Ann("p3 on new line")
            int p3
    ) {}

    void f6(
            int p1
            , int p2,
            @Ann int p3
    ) {}

}
