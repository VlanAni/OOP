import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import vanisimov.substringsearch.components.Controller;

public class TestNormalCase {
    @Test
    public void testTaskExample() {
        try {
            File file = FileGenerator.create("абракадабра");
            List<Long> result = Controller.find(file.getPath(), "бра");

            Assertions.assertNotNull(result);

            assert (result.contains((long) 1) &&
                    result.contains((long) 8));

            assert (result.size() == 2);

        } catch (Exception e) {

            assert (false);

        }
    }

    @Test
    public void testStartAndEnd() {
        try {
            String text = "Hello world! Hello";
            File file = FileGenerator.create(text);
            List<Long> result = Controller.find(file.getPath(), "Hello");

            Assertions.assertNotNull(result);

            assert (result.contains((long) 0) &&
                    result.contains((long) 13));

            assert (result.size() == 2);

        } catch (Exception e) {

            assert (false);

        }
    }

    @Test
    public void testMultipleOccurrences() {
        try {
            String text = "one one one";
            File file = FileGenerator.create(text);
            List<Long> result = Controller.find(file.getPath(), "one");

            Assertions.assertNotNull(result);

            for (long i = 0; i <= 8; i += 4) {
                assert (result.contains(i));
            }

            assert (result.size() == 3);

        } catch (Exception e) {

            assert (false);

        }
    }

    @Test
    public void testOverlappingStrings() {
        try {
            File file = FileGenerator.create("aaaaa");
            List<Long> result = Controller.find(file.getPath(), "aa");

            Assertions.assertNotNull(result);

            for (long i = 0; i <= 3; i++) {
                assert (result.contains(i));
            }

            assert (result.size() == 4);

        } catch (IOException e) {

            assert (false);

        }
    }

    @Test
    public void testLoongFile16GB() {
        try {
            String subStr = "ф".repeat(100);
            // топорно формируем содержимое файла
            StringBuilder arg = new StringBuilder();
            arg.append(subStr);
            arg.append("#".repeat(100));
            long size = 42_949_670; // размер = +- 16 ГБ
            File file = FileGenerator.createLargeChars(size, arg.toString());

            List<Long> result = Controller.find(file.getPath(), subStr);

            Assertions.assertNotNull(result);

            for (long i = 0; size - i >= 100 && i < size; i += arg.length()) {

                assert (result.contains(i));

            }

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
            for (int i = 0x1F600; i <= 0x1F604; ++i) {
                sb.append(Character.toChars(i)); // добавляем смайлики используя код-поинты из Unicod'а
            }
            StringBuilder pattern = new StringBuilder();
            pattern.append(Character.toChars(0x1F602));
            pattern.append(Character.toChars(0x1F603));
            File file = FileGenerator.create(sb.toString());
            List<Long> result = Controller.find(file.getPath(), pattern.toString());

            Assertions.assertNotNull(result);

            Assertions.assertTrue(result.contains((long) 2) &&
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
            for (int i = 0x3041; i <= 0x3049; i += 2) { // ищем среди маленьких букв
                sb.append(Character.toChars(i)); // добавляем смайлики используя код-поинты из Unicod'а
            }
            StringBuilder pattern = new StringBuilder();
            pattern.append(Character.toChars(0x3045));
            File file = FileGenerator.create(sb.toString());
            List<Long> result = Controller.find(file.getPath(), pattern.toString());

            Assertions.assertNotNull(result);

            Assertions.assertTrue(result.contains((long) 2) &&
                    result.size() == 1);

        } catch (Exception e) {

            assert (false);

        }
    }
}