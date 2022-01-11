package generalTests;

import edu.touro.mco152.bm.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;

public class UtilTest {
    /**
     * This test method tests for Range in CORRECT
     */
    @ParameterizedTest
    @ValueSource(ints = {5,6,7,8,9,10})
    public void testRandIntBoundaries(int num){
        int min = num;
        int max = num + 5;

        int actual = Util.randInt(min, max);
        Assertions.assertTrue(actual <= max && actual >= min);

    }

    /**
     * This test method test for conformance in CORRECT. Does the return value conform to the proper format?
     */
    @Test
    public void testDisplayString(){
        double d = 500.7789;

        String actual = Util.displayString(d);
        String expected = "500.78";

        Assertions.assertEquals(actual, expected);

    }

    /**
     * This method tests for RIGHT in RIGHT-BICEP. Is the result right?
     */

    @Test
    public void testDeleteDirectoryExists(){
        String path = "C:\\Users\\Akiva Jacobson\\OneDrive - student.touro.edu\\Documents\\Computer Methodology\\TestFile";
        File f = new File(path);
        f.mkdir();

        boolean actual = Util.deleteDirectory(f);

        Assertions.assertTrue(actual);

    }

    /**
     * This test forces an Error condition through Boundary Checking. Satisfying conditions of E in BICEP and B in BICEP.
     */
    @Test
    public void testGetDiskInfoNullValue(){

        Assertions.assertThrows(java.lang.NullPointerException.class, () -> Util.getDeviceFromPath(null));

    }
}
