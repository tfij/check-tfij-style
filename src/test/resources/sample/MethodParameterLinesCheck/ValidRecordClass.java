package pl.tfij.checktfijstyle.sample.lines;

public record ValidRecordClass(String a, String b, String c) {
    public record AnotherValidRecord(
            int foo,
            long bar) { }
}
