import org.testng.Assert;
import org.testng.annotations.Test;


public class LexSwitchToIDStateTest {

    @Test
    public void SwitchToIDState_Test1_DefaultStateTest(){
        int state = Lex.currState;
        Assert.assertEquals(state, 1);
    }

    @Test
    public void SwitchToIDState_Test2_CurrentStateTest(){
        Lex.switchToIDState();
        int state = Lex.currState;
        Assert.assertEquals(state, 42);
    }
}
