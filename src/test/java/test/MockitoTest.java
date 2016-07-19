package test;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * @author myeongju.jung
 */
public class MockitoTest {

    @Test
    public void testDummy() throws Exception {
        List mockedList = mock(List.class);
        assertThat(mockedList.size(), is(0));
    }

    @Test
    public void testVerifyDummy() throws Exception {
        List mockedList = mock(List.class);

        // Mock 객체를 사용한다
        mockedList.add("item");
        mockedList.clear();

        // 검증
        verify(mockedList).add("item");
        verify(mockedList).clear();
    }

    @Test(expected = RuntimeException.class)
    public void testStub() throws Exception {
        List mockedList = mock(List.class);

        // Stub 만들기
        when(mockedList.size()).thenReturn(1);
        when(mockedList.get(0)).thenReturn("item");
        when(mockedList.get(1)).thenThrow(new RuntimeException("Not exist element"));

        assertThat(mockedList.size(), is(1));
        assertThat(mockedList.get(0), is("item"));
        assertThat(mockedList.get(2), is(nullValue()));
        mockedList.get(1);  // throw exception
        fail("Not occured exception");
    }

    @Test
    public void testVerifyStub() throws Exception {
        List mockedList = mock(List.class);
        Object box = new Object();
        Object car = new Object();

        mockedList.add("item");

        verify(mockedList).add("item");
        verify(mockedList, times(1)).add("item");

        mockedList.add(box);
        mockedList.add(box);

        verify(mockedList, times(2)).add(box);

        verify(mockedList, never()).add(car);

        mockedList.removeAll(Arrays.asList(box));
        mockedList.removeAll(null);

        verify(mockedList, atLeastOnce()).removeAll(anyCollection());

        mockedList.size();
        mockedList.size();

        verify(mockedList, atLeast(2)).size();

        verify(mockedList, atMost(5)).add(box);
    }

    @Test
    @Ignore
    public void testNotVerify() throws Exception {
        List mockedList = mock(List.class);
        verify(mockedList).size();  // 오류발생
    }

    @Test
    public void testVerifyOrdering() throws Exception {
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

        firstMock.add("item1");
        secondMock.add("item2");

        InOrder inOrder = inOrder(firstMock, secondMock);
        inOrder.verify(firstMock).add("item1");
        inOrder.verify(secondMock).add("item2");
    }

    @Test(expected = RuntimeException.class)
    public void testVoidToException() throws Exception {
        List mockedList = mock(List.class);
//        when(mockedList.clear()).thenThrow(new RuntimeException());   // void는 컴파일 오류 발생
        doThrow(new RuntimeException()).when(mockedList).clear();

        mockedList.clear();
    }

    @Test(expected = SQLException.class)
    public void testCallbackStub() throws Exception {
        Object[] currentRecord = null;
        ResultSet rs = mock(ResultSet.class);

        when(rs.getInt("no")).thenAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                if (currentRecord == null)
                    throw new SQLException("access fields is empty");
                return ((Integer)currentRecord[0]).intValue();
            }
        });
        rs.getInt("no");
        fail("Must occured exception");
    }

    /** spy 사용은 추천하지 않는다 */
    @Test
    public void testSpy() throws Exception {
        ArrayList<String> realList = new ArrayList<String>();
        realList.add("Hello");
        realList.add("Hello2");
        assertThat(realList.get(0), is("Hello"));
        assertThat(realList.get(1), is("Hello2"));

        List mockedList = spy(realList);

        when(mockedList.get(0)).thenReturn("item");
        assertThat(mockedList.get(0), is("item"));
        assertThat(mockedList.get(1), is("Hello2"));
    }

    @Test
    public void testMock_SmartNull() throws Exception {
        List mockedList = mock(List.class);
        List smartMockedList = mock(List.class, RETURNS_SMART_NULLS);

        assertThat(mockedList.toArray(), is(nullValue()));
        assertThat(smartMockedList.toArray(), is(any(Object[].class)));

        assertThat(mockedList.toString(), is(anyString()));
        assertThat(smartMockedList.toString(), is(""));
    }
}
