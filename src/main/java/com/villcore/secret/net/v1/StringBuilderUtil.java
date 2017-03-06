package com.villcore.secret.net.v1;

public class StringBuilderUtil {
	private static final ThreadLocal<StringBuilderHelper> threadLocalStringBuilderHelper = new ThreadLocal<StringBuilderHelper>() {
		@Override
		protected StringBuilderHelper initialValue() {
			return new StringBuilderHelper();
		}
	};


	final static class StringBuilderHelper {
		private final StringBuilder sb;

		public StringBuilderHelper() {
			this.sb = new StringBuilder();
		}

		public StringBuilder getStringBuilder() {
			this.sb.setLength(0);
			return this.sb;
		}
	}

	public static StringBuilder getStringBuilder() {
		return threadLocalStringBuilderHelper.get().getStringBuilder();
	}
	
	public static void main(String[] args) {
		StringBuilder sb = getStringBuilder();
		sb.append("123");
		System.out.println(sb.toString());
		
		StringBuilder sb2 = getStringBuilder();
		sb2.append("456");
		System.out.println(sb2.toString());
		
		System.out.println(sb == sb2);
	}
}
