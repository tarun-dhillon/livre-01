package uk.cbooksys.shared;


public class EVENT {
	public static int TYPE_SQUASH = 0;
	public static int TYPE_TENNIS = 1;
	public static int TYPE_BOWLS = 2;
	public static int TYPE_CLASS_SPINNING = 3;
	public static int TYPE_CLASS_ZUMBA = 4;

	String type; //
	String userIds; // one event needs to created for each user
	SLOT slot;
}
