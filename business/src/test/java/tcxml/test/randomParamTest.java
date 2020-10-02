package tcxml.test;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.Test;

public class randomParamTest {

	@Test
	public void test() {
		Random therandom = new Random();
		int randomNumberOrigin = new Integer(1);
		int randomNumberBound= new Integer(150);		
		IntStream intstream = therandom.ints(randomNumberOrigin, randomNumberBound);
		String format ="%08lu" ;

		int res = intstream.findAny().getAsInt();
		
		
		
		
		String ret = String.format(format.replace("lu", "d").replace("u", "d"), new Integer(res));
		System.out.println("random intergerr :" + ret);
		
		
	}

}
