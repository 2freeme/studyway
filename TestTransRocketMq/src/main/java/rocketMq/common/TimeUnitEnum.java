package rocketMq.common;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


public enum TimeUnitEnum {
    MON("m", 1),
    DAY("d", 2),
    HOUR("h", 3),
    MIN("M", 4),
    SEC("s", 4);

    private String name;
    private int index;

    private TimeUnitEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        TimeUnitEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            TimeUnitEnum c = var1[var3];
            if (c.getIndex() == index) {
                return c.name;
            }
        }

        return null;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
