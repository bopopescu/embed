package utils.random;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by digvijaysharma on 05/02/17.
 */
public class RandomCodeGenerator {

    private org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(RandomGenerator.class);

    private static int MAX_LENGTH = 9;

    public int noOfAlphabets = 5;

    public int lengthOfCode = MAX_LENGTH;

    public int noOfDigits = 4;

    /**
     * <p>Public Interface for the random API</p>
     * @param noOfAlphabets
     * @param lengthOfCode
     * @return
     */
    public static String createRandomCode(int noOfAlphabets, int lengthOfCode) {
        return new RandomCodeGenerator().doCreateRandomCode(noOfAlphabets, lengthOfCode);
    }

    /**
     * <p>Creates a random AlphaNumeric Code</p>
     * @param noOfAlphabets = No of alphabets required in the code
     * @param lengthOfCode = Length of code required <= MAX_LENGTH
     * @return The Random Code
     */
    private String doCreateRandomCode(int noOfAlphabets, int lengthOfCode) {
        if(noOfAlphabets <= MAX_LENGTH && lengthOfCode <= MAX_LENGTH && noOfAlphabets <= lengthOfCode) {
            this.noOfAlphabets = noOfAlphabets;
            this.lengthOfCode = lengthOfCode;
            this.noOfDigits = lengthOfCode - noOfAlphabets;
        }
        else {
            LOG.error("Using default random code settings");
        }
        return generateRandomCode();
    }

    /**
     * <p>Catches the exceptions before submitting to the executor</p>
     * @return The Random Code
     */
    private String generateRandomCode() {
        try {
            return generateNextRandomCode();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>Opens a new executor for the random code generation so that main thread is not blocked</p>
     * @return The Random Code
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private String generateNextRandomCode() throws ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Future<String> nextCode = exec.submit(new RandomGenerator());
        if(nextCode != null) {
            while(nextCode.isDone());
            if(nextCode != null) {
                String code = nextCode.get();
                exec.shutdown();
                return code;
            }
        }
        return null;
    }

    private class RandomGenerator implements Callable<String> {
        private String code;
        
        public RandomGenerator() {
        }

        public String call() {
            this.code = getRandomCode();
            return code;
        }
    }

    /**
     * <p>The actual AlphaNumeric Random Code Builder</p>
     * @return The Random Code
     */
    private String getRandomCode() {
        LOG.info("Building Random Code");
        StringBuilder code = new StringBuilder("");
        while(code.length() < lengthOfCode) {
            code = code.append("x");
        }
        int position = 0;
        while(position <= lengthOfCode) {
            boolean alphaOrNum = false;
            if(noOfAlphabets == 0 && noOfDigits != 0) {
                alphaOrNum = false;
            }
            else if(noOfAlphabets != 0 && noOfDigits == 0) {
                alphaOrNum = true;
            }
            else if(noOfAlphabets == 0 && noOfDigits == 0) {
                break;
            }
            else {
                alphaOrNum = ThreadLocalRandom.current().nextLong(0, 2) == 0 ? true : false ;
            }
            if(alphaOrNum) {   /* Alphabet */
                noOfAlphabets--;
                boolean alphaCase = ThreadLocalRandom.current().nextLong(0, 2) == 0 ? true : false ;
                if(alphaCase) { /* Lower Case */
                    char alphabet = (char)('a' + (char)((int)ThreadLocalRandom.current().nextLong(0, 26)));
                    code.setCharAt(position, alphabet);
                }
                else {          /* Upper Case */
                    char alphabet = (char)('A' + (char)((int)ThreadLocalRandom.current().nextLong(0, 26)));
                    code.setCharAt(position, alphabet);
                }
            }
            else {                  /* Number */
                int digit = (int)ThreadLocalRandom.current().nextLong(0, 10);
                code.setCharAt(position, (char)(digit+48));
                noOfDigits--;
            }
            position++;
        }
        LOG.info(code.toString());
        return code.toString();
    }

    public static void main(String[] args) {
        System.out.println(new RandomCodeGenerator().createRandomCode(4,8));
    }
}
