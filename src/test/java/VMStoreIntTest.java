import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class VMStoreIntTest {

    int off = 0;

    @BeforeTest
    void init(){
        VM.heap = new byte[100];
    }

    @Test
    void Store0Test() {
        int n = 0;
        VM.storeInt(n, off);
        byte[] expected = {0, 0, 0, 0};
        for (int i = off; i < off + 4; i++)
            Assert.assertEquals(VM.heap[i], expected[i - off]);
        off += 4;
    }

    @Test
    void Store256Test() {
        int n = 256;
        VM.storeInt(n, off);
        byte[] expected = {0, 0, 1, 0};
        for (int i = off; i < off + 4; i++)
            Assert.assertEquals(VM.heap[i], expected[i - off]);
        off += 4;
    }

    @Test
    void Store1Test() {
        int n = 1;
        VM.storeInt(n, off);
        byte[] expected = {0, 0, 0, 1};
        for (int i = off; i < off + 4; i++)
            Assert.assertEquals(VM.heap[i], expected[i - off]);
        off += 4;
    }

    @Test
    void Store2147483647Test() {
        int n = 2147483647;
        VM.storeInt(n, off);
        byte[] expected = {127, -1, -1, -1};
        for (int i = off; i < off + 4; i++)
            Assert.assertEquals(VM.heap[i], expected[i - off]);
        off += 4;
    }

    @Test
    void aStoreMinus2147483648Test() {
        int n = -2147483648;
        VM.storeInt(n, off);
        byte[] expected = {-128, 0, 0, 0};
        for (int i = off; i < off + 4; i++)
            Assert.assertEquals(VM.heap[i], expected[i - off]);
        off += 4;
    }





}
