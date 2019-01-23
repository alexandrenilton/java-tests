package br.ce.wcaquino;

import org.junit.Assert;
import org.junit.Test;

import com.edu.abelem.entity.User;

public class AssertTest {
	
	@Test
	public void test() {
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		Assert.assertEquals(1,  1); // verifica se um valor é igual ao outro
//		Assert.assertEquals(0.51, 0.51); // vai dar erro, por conta do ponto flutuante
		Assert.assertEquals(0.51, 0.51, 0.01); // vai dar erro, por conta do ponto flutuante o 0.01 é o Delta, configura margem de erro
		
		int i = 5;
		Integer i2 = 5;
		Assert.assertEquals(Integer.valueOf(i),  i2);
//		Assert.assertEquals(i,  i2); // Vai apresentar erro
		
		Assert.assertEquals("bola", "bola");
		Assert.assertTrue("bola".equalsIgnoreCase("Bola") );
		Assert.assertTrue("bola".startsWith("bo"));
		
		User u1 = new User("Usuário 1");
		User u2 = new User("Usuário 1");
		User u3 = u2;
		User u4 = null;
		
		Assert.assertEquals(u1,  u2); // se o Usuario nao tiver o .equals() implementado, nao vai passar // tem tbm o asserNotEquals
		
		Assert.assertSame(u3, u2); // tem tbm o assertNotSame
		
		Assert.assertNull(u4);
	}
}
