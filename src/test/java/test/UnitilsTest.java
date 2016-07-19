package test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.unitils.reflectionassert.ReflectionAssert.*;
import static org.unitils.reflectionassert.ReflectionComparatorMode.*;

/**
 * @author myeongju.jung
 */
public class UnitilsTest {
    @Test
    public void testReflectionEquals() throws Exception {
        Book aBook = new Book("사람은 무엇으로 사는가", "톨스토이", 9000);
        Book otherBook = new Book("사람은 무엇으로 사는가", "톨스토이", 9000);

        assertEquals(aBook.getName(), otherBook.getName());
        assertEquals(aBook.getAuthor(), otherBook.getAuthor());
        assertEquals(aBook.getPrice(), otherBook.getPrice());

        // equals 메소드가 구현되어야지 동치성 비교 가능
//        assertEquals(aBook, otherBook);
//        assertThat(aBook, is(otherBook));
        assertReflectionEquals(aBook, otherBook);   // equals 메소드가 구현되지 않아도 동치성 비교
    }

    @Test
    public void testEquals_LenientOrder() throws Exception {
        List<Integer> myList = Arrays.asList(3, 2, 1);

//        assertReflectionEquals(Arrays.asList(1, 2, 3), myList);   // 오류
        assertReflectionEquals(Arrays.asList(1, 2, 3), myList, LENIENT_ORDER);
    }

    @Test
    public void testEquals_IgnoreDefaults() throws Exception {
        Item actualItem = new Item("IKH-001", "20040601", 24000);
        Item expectedItem = new Item("IKH-001", null, 24000);

//        assertReflectionEquals(expectedItem, actualItem); // 오류
        assertReflectionEquals(expectedItem, actualItem, IGNORE_DEFAULTS);
    }

    /** expected의 속성들이 default 값이 아니라 actual의 속성들이 default값이 면 해당 값이 존재해야한다. */
    @Test(expected = AssertionError.class)
    public void testEquals_IgnoreDefaultsFail() throws Exception {
        Item actualItem = new Item("IKH-001", null, 24000);
        Item expectedItem = new Item("IKH-001", "20040601", 24000);

//        assertReflectionEquals(expectedItem, actualItem); // 오류
        assertReflectionEquals(expectedItem, actualItem, IGNORE_DEFAULTS);
    }

    @Test
    public void testEquals_LenientDates() throws Exception {
        Item actualItem = new Item("IKH-001", null, 24000, new Date(System.currentTimeMillis()));
        Item expectedItem = new Item("IKH-001", null, 24000, new Date(System.currentTimeMillis() + 100));

//        assertReflectionEquals(expectedItem, actualItem);  // 오류
        assertReflectionEquals(expectedItem, actualItem, LENIENT_DATES);
    }

    /**
     * assertLenientEquals = assertReflectionEquals + LENIENT_ORDER + IGNORE_DEFAULTS
     * 단, LENIENT_DATES 는 포함 안 됨
     */
    @Test
    public void testLenientEquals() throws Exception {
        // LENIENT_ORDER
        assertReflectionEquals("ABC".toCharArray(), "CBA".toCharArray(), LENIENT_ORDER, IGNORE_DEFAULTS);
        assertLenientEquals("ABC".toCharArray(), "CBA".toCharArray());  // 위와 같다

        // IGNORE_DEFAULTS
        Item actualItem = new Item("IKH-001", "20040601", 24000);
        Item expectedItem = new Item("IKH-001", null, 24000);
        assertReflectionEquals(expectedItem, actualItem, LENIENT_ORDER, IGNORE_DEFAULTS);
        assertLenientEquals(expectedItem, actualItem);  // 위와 같다
    }

    /** assertLenientEquals 에 LENIENT_DATES 는 포함 안 됨 */
    @Test(expected = AssertionError.class)
    public void testLenientEquals_NotLenientDates() throws Exception {
        // LENIENT_DATES
        Item actualItem = new Item("IKH-001", null, 24000, new Date(System.currentTimeMillis()));
        Item expectedItem = new Item("IKH-001", null, 24000, new Date(System.currentTimeMillis() + 100));
        assertLenientEquals(expectedItem, actualItem);
    }

    @Test
    public void testAssertPropertyLenientEquals() throws Exception {
        Player player = new Player("선수", 31, 15);
        assertPropertyLenientEquals("age", 31, player);
        assertPropertyLenientEquals("experienceYear", 15, player);
    }
}

@Getter
@AllArgsConstructor
class Book {
    private String name;
    private String author;
    private int price;
}

@Getter
@AllArgsConstructor
class Item {
    private String code;
    private String ymd;
    private int price;
    private Date regDate;

    public Item(String code, String ymd, int price) {
        this.code = code;
        this.ymd = ymd;
        this.price = price;
    }
}

class Player {
    private String name;
    private int age;
    private int experienceYear;

    public Player(String name, int age, int experienceYear) {
        this.name = name;
        this.age = age;
        this.experienceYear = experienceYear;
    }

    public String getName() {
        return name;
    }

    public int getAbilityPoint() {
        return (30 - this.age) + experienceYear;
    }
}