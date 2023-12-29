package com.yzp.designpatterns.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * 建造者模式
 *
 * @author:YangZhiPeng
 * @date: 2023/12/29 14:07
 */
public class BuilderTest {

	private final List<String> list;
	private final int param2;
	private final double param3;

	private BuilderTest(Builder builder) {
		this.list = builder.list;
		this.param2 = builder.param2;
		this.param3 = builder.param3;
	}

	public static class Builder {
		private final List<String> list = new ArrayList<>();
		private int param2;
		private double param3;

		public Builder addParam1(String param1) {
			this.list.add(param1);
			return this;
		}

		public Builder addParam2(int param2) {
			this.param2 = param2;
			return this;
		}

		public Builder addParam3(double param3) {
			this.param3 = param3;
			return this;
		}

		public BuilderTest build() {
			return new BuilderTest(this);
		}
	}

	public void execute() {
		System.out.println("Param1: " + String.join("、", list));
		System.out.println("Param2: " + param2);
		System.out.println("Param3: " + param3);
	}

	public static void main(String[] args) {
		BuilderTest builderTest = new BuilderTest.Builder()
			.addParam1("Parameter 1")
			.addParam1("1111")
			.addParam2(2)
			.addParam3(3.0)
			.build();

		builderTest.execute();
	}
}
