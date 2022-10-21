package pl.tfij.checktfijstyle.checks;

import org.junit.jupiter.api.Test;
import pl.tfij.checktfijstyle.fixture.TestCheckstyle;

class NameLengthCheckTest {
    private final TestCheckstyle checkstyle = new TestCheckstyle(NameLengthCheck.class);

    @Test
    void noIdentifierLengthIssuesInClass() {
        checkstyle.check("NameLengthCheck/ValidClass.java");

        checkstyle.assertNoViolations();
    }

    @Test
    void noIdentifierLengthIssuesInInterface() {
        checkstyle.check("NameLengthCheck/ValidInterface.java");

        checkstyle.assertNoViolations();
    }

    @Test
    void toLongIdentifiersInClass() {
        checkstyle.check("NameLengthCheck/NonValidClass.java");

        checkstyle.assertViolationCount(16);
        checkstyle.assertViolation(3, 1, "Class name `A51CharLengthClass_________________________________` is 51 length, max accepted length is 50.");
        checkstyle.assertViolation(4, 57, "Parameter name `a31CharsLengthArgument_________` is 31 length, max accepted length is 30.");
        checkstyle.assertViolation(7, 5, "Variable name `a31CharsLengthStaticField______` is 31 length, max accepted length is 30.");
        checkstyle.assertViolation(8, 5, "Variable name `a31CharsLengthField____________` is 31 length, max accepted length is 30.");
        checkstyle.assertViolation(10, 5, "Method name `a51CharsLengthStaticMethod_________________________` is 51 length, max accepted length is 50.");
        checkstyle.assertViolation(13, 5, "Method name `a51CharsLengthMethod_______________________________` is 51 length, max accepted length is 50.");
        checkstyle.assertViolation(17, 28, "Parameter name `a31CharsLengthArgument_________` is 31 length, max accepted length is 30.");
        checkstyle.assertViolation(18, 9, "Variable name `a31charLengthVariable__________` is 31 length, max accepted length is 30.");
        checkstyle.assertViolation(22, 28, "Parameter name `a31CharsLengthArgument_________` is 31 length, max accepted length is 30.");
        checkstyle.assertViolation(23, 9, "Variable name `a31charLengthVariable__________` is 31 length, max accepted length is 30.");
        checkstyle.assertViolation(27, 46, "Parameter name `a31CharsLengthArgument_________` is 31 length, max accepted length is 30.");
        checkstyle.assertViolation(31, 28, "Parameter name `a31CharsLengthArgument_________` is 31 length, max accepted length is 30.");
        checkstyle.assertViolation(32, 9, "Variable name `a31charLengthVariable__________` is 31 length, max accepted length is 30.");
        checkstyle.assertViolation(36, 5, "Class name `A51CharLengthInnerStaticClass______________________` is 51 length, max accepted length is 50.");
        checkstyle.assertViolation(39, 5, "Class name `A51CharLengthInnerClass____________________________` is 51 length, max accepted length is 50.");
        checkstyle.assertViolation(42, 5, "Record name `A51CharLengthInnerRecordClass______________________` is 51 length, max accepted length is 50.");
    }

    @Test
    void toLongIdentifiersInInterface() {
        checkstyle.check("NameLengthCheck/NonValidInterface.java");

        checkstyle.assertViolationCount(4);
        checkstyle.assertViolation(1, 1, "Package name `A21CharLengthPackage_` is 21 length, max accepted length is 20.");
        checkstyle.assertViolation(3, 1, "Interface name `A51CharLengthInterfaceName_________________________` is 51 length, max accepted length is 50.");
        checkstyle.assertViolation(4, 5, "Enum class name `A51CharLengthEnum__________________________________` is 51 length, max accepted length is 50.");
        checkstyle.assertViolation(5, 9, "Enum constant name `A51CharLengthEnumProperty__________________________` is 51 length, max accepted length is 50.");
    }

    @Test
    void toUseLongIdentifiersWithCustomMaxLength() {
        TestCheckstyle checkstyle = new TestCheckstyle(
                NameLengthCheck.class,
                c -> {
                    c.addProperty("maxClassNameLength", "10");
                    c.addProperty("maxMethodNameLength", "11");
                    c.addProperty("maxRecordNameLength", "12");
                    c.addProperty("maxParameterNameLength", "13");
                    c.addProperty("maxVariableNameLength", "14");
                    c.addProperty("maxInterfaceNameLength", "15");
                    c.addProperty("maxPackageNameLength", "16");
                    c.addProperty("maxEnumClassNameLength", "17");
                    c.addProperty("maxEnumConstNameLength", "18");
                });

        checkstyle.check("NameLengthCheck/CustomSettingsClass.java");

        checkstyle.assertViolationCount(11);
        checkstyle.assertViolation(1, 1, "Package name `a17package_______` is 17 length, max accepted length is 16.");
        checkstyle.assertViolation(3, 1, "Class name `A11Class___` is 11 length, max accepted length is 10.");
        checkstyle.assertViolation(7, 5, "Class name `A11Class2__` is 11 length, max accepted length is 10.");
        checkstyle.assertViolation(13, 5, "Record name `A13Record____` is 13 length, max accepted length is 12.");
        checkstyle.assertViolation(19, 5, "Interface name `A16Interface____` is 16 length, max accepted length is 15.");
        checkstyle.assertViolation(27, 5, "Method name `a12Method___` is 12 length, max accepted length is 11.");
        checkstyle.assertViolation(27,32, "Parameter name `a14Parameter__` is 14 length, max accepted length is 13.");
        checkstyle.assertViolation(28, 9, "Variable name `a15var_________` is 15 length, max accepted length is 14.");
        checkstyle.assertViolation(33, 5, "Variable name `A_15_FIELD_____` is 15 length, max accepted length is 14.");
        checkstyle.assertViolation(37, 9, "Enum constant name `A19EnumConst_______` is 19 length, max accepted length is 18.");
        checkstyle.assertViolation(40, 5, "Enum class name `A18Enum___________` is 18 length, max accepted length is 17.");
    }
}