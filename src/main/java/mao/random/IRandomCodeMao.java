package main.java.mao.random;

import main.java.core.vo.RandomCodeVO;

/**
 * Created by digvijaysharma on 15/01/17.
 */
public interface IRandomCodeMao {

    RandomCodeVO getRandomCodeByCode(String code);

    void createRandomCode(RandomCodeVO randomCode);
}
