package test.aop.bean;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public class WorldServiceImpl implements WorldService {
    private Integer number;

    @Override
    public void explode() {
        System.out.println("The Earth is going to explode");
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }
}
