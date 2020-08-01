import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class test {

    @Test
    public void s(){
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        format.format(date);
        System.out.println(format.format(date));
    }
}
