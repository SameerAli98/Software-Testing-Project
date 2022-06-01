import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class VMGetIntTest {

    int off = 0;

    @BeforeTest
    void init(){
        VM.heap = new byte[100];
    }

    @Test
    void get0Test(){
        VM.getInt(off);
        byte[] bytes = {0, 0, 0, 0};
        System.arraycopy(bytes, 0, VM.heap, off, 4);
        int n = VM.getInt(off);
        off += 4;
        Assert.assertEquals(n, 0);
    }

    @Test
    void get256Test(){
        VM.getInt(off);
        byte[] bytes = {0, 0, 1, 0};
        System.arraycopy(bytes, 0, VM.heap, off, 4);
        int n = VM.getInt(off);
        off += 4;
        Assert.assertEquals(n, 256);
    }

    @Test
    void get1Test(){
        VM.getInt(off);
        byte[] bytes = {0, 0, 0, 1};
        System.arraycopy(bytes, 0, VM.heap, off, 4);
        int n = VM.getInt(off);
        off += 4;
        Assert.assertEquals(n, 1);
    }

    @Test
    void get2147483647Test(){
        VM.getInt(off);
        byte[] bytes = {127, -1, -1, -1};
        System.arraycopy(bytes, 0, VM.heap, off, 4);
        int n = VM.getInt(off);
        off += 4;
        Assert.assertEquals(n, 2147483647);
    }

    @Test
    void getMinus2147483648Test(){
        VM.getInt(off);
        byte[] bytes = {-128, 0, 0, 0};
        System.arraycopy(bytes, 0, VM.heap, off, 4);
        int n = VM.getInt(off);
        off += 4;
        Assert.assertEquals(n, -2147483648);
    }
}
