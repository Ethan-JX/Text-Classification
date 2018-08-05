package com.ethan.basic.util;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/** 提供mysql 建表的时候默认用utf8
 * Created by kyoka on 17/1/20.
 */

public class MySQL5DialectUTF8 extends MySQL5InnoDBDialect{
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
