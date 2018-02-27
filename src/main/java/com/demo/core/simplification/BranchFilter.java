package com.demo.core.simplification;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

public class BranchFilter extends SimplePropertyPreFilter {
    public static final int MODE_INCLUDE = 1;
    public static final int MODE_EXCLUDE = 0;
    private static String[] emptyStrArray = new String[0];
    private int mode = 0;

    public BranchFilter(String... properties) {
        super(emptyStrArray);
        this.init(properties);
    }

    public BranchFilter(Class<?> clazz, String... properties) {
        super(clazz, emptyStrArray);
        this.init(properties);
    }

    public BranchFilter(Class<?> clazz, int mode, String... properties) {
        super(clazz, (String[])null);
        this.mode = mode;
        this.init(properties);
    }

    public static BranchFilter newBranchFilter(Class<?> clazz, String... properties) {
        return new BranchFilter(clazz, properties);
    }

    public static BranchFilter newBranchFilter(Class<?> clazz) {
        return new BranchFilter(clazz, new String[0]);
    }

    public static BranchFilter newBranchFilter(Class<?> clazz, int mode, String... properties) {
        return new BranchFilter(clazz, mode, properties);
    }

    public BranchFilter ignoreStandardWho() {
        return this.exclude("creationDate", "createdBy", "lastUpdateDate", "lastUpdatedBy", "lastUpdatedLogin", "versions");
    }

    public BranchFilter ignoreAdditionAttribute() {
        return this.exclude("attributeCatrgory", "attribute1", "attribute2", "attribute3", "attribute4", "attribute5", "attribute6", "attribute7", "attribute8", "attribute9", "attribute10", "attribute11", "attribute12", "attribute13", "attribute14", "attribute15");
    }

    public BranchFilter ignoreSystemFields() {
        return this.ignoreStandardWho().ignoreAdditionAttribute();
    }

    private void init(String... properties) {
        if (this.mode == 0) {
            this.exclude(properties);
        } else {
            this.include(properties);
        }

    }

    public BranchFilter include(String... properties) {
        String[] var2 = properties;
        int var3 = properties.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String property = var2[var4];
            this.getIncludes().add(property);
        }

        return this;
    }

    public BranchFilter exclude(String... properties) {
        String[] var2 = properties;
        int var3 = properties.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String property = var2[var4];
            this.getExcludes().add(property);
        }

        return this;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
