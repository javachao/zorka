/** 
 * Copyright 2012 Rafal Lewczuk <rafal.lewczuk@jitlogic.com>
 * 
 * ZORKA is free software. You can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * ZORKA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * ZORKA. If not, see <http://www.gnu.org/licenses/>.
 */

package com.jitlogic.zorka.agent.unittest;

import java.util.concurrent.Executor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.jitlogic.zorka.agent.ZorkaBshAgent;

public class BshAgentTest {

	private static class TrivialExecutor implements Executor {
		public void execute(Runnable command) {
			command.run();
		}
	}
	
	ZorkaBshAgent agent;
	
	@Before
	public void setUp() throws Exception {
		agent = new ZorkaBshAgent(new TrivialExecutor());
		agent.svcStart();
		agent.loadScript(getClass().getResource("/unittest/BshAgentTest.bsh"));
	}
	
	@After
	public void tearDown() throws Exception {
		agent.svcStop();
	}
	
	@Test
	public void testTrivialQuery() throws Exception {
		assertEquals("5", agent.query("2+3"));
	}

	@Test
	public void testJmxCalls() throws Exception {
		assertEquals("1.0", agent.query("zorka.jmx(\"java\",\"java.lang:type=Runtime\",\"SpecVersion\")"));
	}
	
	@Test
	public void testCreateMappedMBeanWithNoAttrs() throws Exception {
		assertEquals("ZorkaMappedMBean()", agent.query("zorka.mbean(\"java\", \"zorka:type=jvm,name=GCstats\")"));
		
		String rslt = agent.query("zorka.jmx(\"java\", \"zorka:type=jvm,name=GCstats\")");
		assertEquals("zorka:type=jvm,name=GCstats", rslt);
	}

	@Test
	public void testCreateMappedMBeanWithConstantAttr() throws Exception {
		assertEquals("OK", agent.query("createBean1()"));
		String rslt = agent.query("zorka.jmx(\"java\", \"zorka.test:name=Bean1\", \"test1\")");
		assertEquals("1", rslt);
	}
	
}