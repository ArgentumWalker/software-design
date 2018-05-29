import ru.spbau.cli.CLI;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CLITest {
    private final static String NEWLINE = System.lineSeparator();
    private final static char INVITATION = '>';
    private final static String INVITATION_NEWLINE = INVITATION + NEWLINE;

    private String execute(String input) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new CLI(inputStream, outputStream).run();
        return new String(outputStream.toByteArray());
    }

    @Test
    public void wcCommandTest() throws IOException {
        //Windows and Unix newliners
        String expected1 = INVITATION + "172 709 5299" + NEWLINE + INVITATION_NEWLINE;
        String expected2 = INVITATION + "172 709 5471" + NEWLINE + INVITATION_NEWLINE;
        String pipedActual = execute("cat gradlew | wc\nexit");
        assertTrue(expected1.equals(pipedActual) || expected2.equals(pipedActual));

        String noPipeActual = execute("wc gradlew\nexit");
        assertTrue(expected1.equals(noPipeActual) || expected2.equals(noPipeActual));

        String expected = INVITATION + "1 1 3" + NEWLINE + INVITATION_NEWLINE;
        String actual = execute("echo 123 | wc\nexit");
        assertEquals(expected, actual);
    }

    @Test
    public void identifierTest() throws IOException {

        String expected = INVITATION_NEWLINE + INVITATION + "5" + NEWLINE + INVITATION_NEWLINE;
        String actual = execute("x=5\necho $x\nexit");
        assertEquals(expected, actual);

        expected = INVITATION_NEWLINE + INVITATION_NEWLINE;
        actual = execute("x=exit\n$x");
        assertEquals(expected, actual);
    }

    @Test
    public void weakQuotingTest() throws IOException {
        String expected = INVITATION_NEWLINE + INVITATION + "$x" + NEWLINE + INVITATION_NEWLINE;
        String actual = execute("x=5\necho '$x'\nexit");
        assertEquals(expected, actual);
    }

    @Test
    public void multilineCommandTest() throws IOException {
        String expected = INVITATION + "ab\ncd" + NEWLINE + INVITATION_NEWLINE;
        String actual = execute("echo \"ab\ncd\"\nexit");
        assertEquals(expected, actual);
    }

    @Test
    public void echoPipeCatTest() throws IOException {
        String expected = INVITATION + "123" + NEWLINE + INVITATION_NEWLINE;
        String actual = execute("echo 123 | cat\nexit");
        assertEquals(expected, actual);
    }

    @Test
    public void lsCommandTest() throws IOException {
        String actual = execute("ls\nexit");
        assertTrue(actual.contains("build.gradle"));
        assertTrue(actual.contains("src"));
    }

    @Test
    public void cdCommandTest() throws IOException {
        String actual = execute("cd src\nls\nexit");
        assertTrue(actual.contains("main"));
        assertTrue(actual.contains("test"));

        actual = execute("cd src\\test\nls\nexit");
        assertTrue(actual.contains("java"));

        actual = execute("cd src\\test\\..\\..\\.\nls\nexit");
        assertTrue(actual.contains("build.gradle"));
        assertTrue(actual.contains("src"));
    }
}
