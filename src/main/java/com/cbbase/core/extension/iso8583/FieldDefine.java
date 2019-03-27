package com.cbbase.core.extension.iso8583;


public class FieldDefine {

	public Type type;
	public int length;
	public String decribe;
	public char fixChar;
	public FixType fixType;
	
	public static enum Type { BCD, BINRARY, ASCII }

	public static enum FixType { LEFT, RIGHT }
	
	public FieldDefine(Type type, int length, String decribe) {
		this.type = type;
		this.length = length;
		this.decribe = decribe;
	}

	public FieldDefine(Type type, int length, String decribe, char fixChar, FixType fixType) {
		this.type = type;
		this.length = length;
		this.decribe = decribe;
		this.fixChar = fixChar;
		this.fixType = fixType;
	}

}
