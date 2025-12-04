import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import vanisimov.substringsearch.components.Controller;

public class TestNormalCase {
    @Test
    public void testTaskExample() {
        try {
            File file = FileGenerator.create("абракадабра");
            List<Integer> result = Controller.find(file.getPath(), "бра");

            Assertions.assertEquals(List.of(1, 8), result);

        } catch (Exception e) {

            assert (false);

        }
    }

    @Test
    public void testStartAndEnd() {
        try {
            String text = "Hello world! Hello";
            File file = FileGenerator.create(text);
            List<Integer> result = Controller.find(file.getPath(), "Hello");

            Assertions.assertEquals(List.of(0, 13), result);

        } catch (Exception e) {

            assert (false);

        }
    }

    @Test
    public void testMultipleOccurrences() {
        try {
            String text = "one one one";
            File file = FileGenerator.create(text);
            List<Integer> result = Controller.find(file.getPath(), "one");

            Assertions.assertEquals(List.of(0, 4, 8), result);

        } catch (Exception e) {

            assert (false);

        }
    }

    @Test
    public void testOverlappingStrings() {
        try {
            File file = FileGenerator.create("aaaaa");
            List<Integer> result = Controller.find(file.getPath(), "aa");

            Assertions.assertEquals(List.of(0, 1, 2, 3), result);

        } catch (IOException e) {

            assert (false);

        }
    }

    @Test
    public void testLoongFile() {
        try {
            StringBuilder sb = new StringBuilder();
            Collection<Integer> indexes = new ArrayList<Integer>();
            String subStr = "very loooooooooooooooooooooooooooooooong pattern";
            for (int i = 1; i <= 200000; ++i) {
                sb.append(subStr).append('\n');
                indexes.add((i - 1) * (subStr.length() + 1));
            }
            File file = FileGenerator.create(sb.toString());
            List<Integer> result = Controller.find(file.getPath(), subStr);

            Assertions.assertTrue(result.containsAll(indexes));

        } catch (Exception e) {

            assert (false);

        }
    }

    // original string - 😀😁😂😃😄 https://en.wikipedia.org/wiki/Emoticons_(Unicode_block) - источник код-понитов
    // substr - 😂😃
    @Test
    public void testSmile() {
        try {
            StringBuilder sb = new StringBuilder();
            int location = 0;
            for (int i = 0x1F600; i <= 0x1F604; ++i) {
                if (i == 0x1F602) { // нам нужно зафиксировать, на какой позиции в массиве char'ов появился нужный смайл
                    location = sb.length();
                }
                sb.append(Character.toChars(i)); // добавляем смайлики используя код-поинты из Unicod'а
            }
            StringBuilder pattern = new StringBuilder();
            pattern.append(Character.toChars(0x1F602));
            pattern.append(Character.toChars(0x1F603));
            File file = FileGenerator.create(sb.toString());
            List<Integer> result = Controller.find(file.getPath(), pattern.toString());

            Assertions.assertTrue(result.contains(location) &&
                    result.size() == 1);

        } catch (Exception e) {

            assert (false);

        }
    }

    // original string - ぁぃぅぇぉ (аиуэо) https://ru.wikipedia.org/wiki/Хирагана_(блок_Юникода) - источник
    // substr - ぅ (ищем u)
    @Test
    public void testJapanese() {
        try {
            StringBuilder sb = new StringBuilder();
            int location = 0;
            for (int i = 0x3041; i <= 0x3049; i += 2) { // ищем среди маленьких букв
                if (i == 0x3045) { // нам нужно зафиксировать, на какой позиции в массиве char'ов появился нужный смайл
                    location = sb.length();
                }
                sb.append(Character.toChars(i)); // добавляем смайлики используя код-поинты из Unicod'а
            }
            StringBuilder pattern = new StringBuilder();
            pattern.append(Character.toChars(0x3045));
            File file = FileGenerator.create(sb.toString());
            List<Integer> result = Controller.find(file.getPath(), pattern.toString());

            Assertions.assertTrue(result.contains(location) &&
                    result.size() == 1);

        } catch (Exception e) {

            assert (false);

        }
    }
}