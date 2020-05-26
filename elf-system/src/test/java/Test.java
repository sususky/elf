import java.util.UUID;

/**
 * @Desc
 * @author surongyao
 * @date 2018/7/24 下午1:58
 * @version
 */
public class Test {
    public static void main(String [] args){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        System.out.println(uuid);
    }
}
