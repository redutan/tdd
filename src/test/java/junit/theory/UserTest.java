package junit.theory;

import lombok.Value;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

/**
 * @author myeongju.jung
 */
@RunWith(Theories.class)
public class UserTest {
    @DataPoint
    public static String GOOD_USERNAME = "optimus";
    @DataPoint
    public static String USERNAME_WITH_SLASH = "optimus/prime";

    @Theory
    public void filenameIncludesUsername(String username) {
        System.out.println("before assume : " + username);
        // assumeThat를 통해서 해당 조건을 만족하는 경우에만 하위 검증이 실행된다. - 인자나 값 필터링용
        assumeThat(username, not(containsString("/")));
        System.out.println("after assume" + username);
        assertThat(new User(username).configFileName(), containsString(username));
    }
}

@Value
class User {
    private String username;

    public String configFileName() {
        return "file://path/" + username + ".config";
    }
}
