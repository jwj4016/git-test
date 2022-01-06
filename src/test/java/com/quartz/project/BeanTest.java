package com.quartz.project;

import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
public class BeanTest {

	@Autowired
	DataSource ds;
	@Autowired
	SqlSessionTemplate st;
	
	@Test
	public void dsTest() {
		log.trace(ds.toString());
		assertNotNull(ds);
	}
	
	@Test
	public void templateTest() {
		log.info(st.toString());
		log.warn(st.toString());
		log.error(st.toString());
		assertNotNull(st);
	}
	
}
