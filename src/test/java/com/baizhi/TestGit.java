package com.baizhi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGit {

	@Test
	public void contextLoads() {
		System.out.println("testgit" + new Date());
		System.out.println("第二次修改");
	}

}
