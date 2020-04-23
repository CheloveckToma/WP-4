package sample.interfaces;

import sample.classes.Database;

public interface DATABASE_CONSTANTS {
    public static final String DATABASE_NAME = "wp-4";
    public static final String TABLE_SUBJECT_NAME = "предметы";
    public static final String TABLE_APPLICATNSTS_NAME = "абитуриенты";
    public static final String TABLE_GROUPS_NAME = "группы";
    public static final String TABLE_ENTRANCE_TESTS_NAME = "вступительные_испытания";
    public static final String TABLE_SPECIALTY_NAME = "специальности";
    public static final Database DATABASE = new Database();
}
