package com.demo.core.simplification;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Trimmer {
    private Object target;
    private Set<BranchFilter> branchFilters = new HashSet();

    public Trimmer() {
    }

    public Trimmer(Object target) {
        this.target = target;
    }

    public static Trimmer newTrimmer() {
        return new Trimmer();
    }

    public static Trimmer newTrimmer(Object target) {
        return new Trimmer(target);
    }

    public Trimmer addBranchFilter(Class branchClass, int mode, String... branchFileds) {
        this.branchFilters.add(new BranchFilter(branchClass, mode, branchFileds));
        return this;
    }

    public Trimmer addBranchFilter(Class branchClass, String... branchFileds) {
        this.branchFilters.add(new BranchFilter(branchClass, branchFileds));
        return this;
    }

    public Trimmer addBranchFilters(BranchFilter... branchFilters) {
        BranchFilter[] var2 = branchFilters;
        int var3 = branchFilters.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            BranchFilter branchFilter = var2[var4];
            this.branchFilters.add(branchFilter);
        }

        return this;
    }

    public Trimmer addBranchFiltersAndIgnoreSystemFields(BranchFilter... branchFilters) {
        BranchFilter[] var2 = branchFilters;
        int var3 = branchFilters.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            BranchFilter branchFilter = var2[var4];
            this.branchFilters.add(branchFilter.ignoreSystemFields());
        }

        return this;
    }

    public Object getTarget() {
        return this.target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object trim() {
        BranchFilter[] branchFiltersArray = new BranchFilter[this.branchFilters.size()];
        String jsonStr = null;
        if (this.target instanceof Collection) {
            jsonStr = JSONArray.toJSONString(this.target, (SerializeFilter[])this.branchFilters.toArray(branchFiltersArray), new SerializerFeature[]{SerializerFeature.WriteDateUseDateFormat});
            return JSON.parseArray(jsonStr);
        } else {
            jsonStr = JSON.toJSONString(this.target, (SerializeFilter[])this.branchFilters.toArray(branchFiltersArray), new SerializerFeature[]{SerializerFeature.WriteDateUseDateFormat});
            return JSON.parse(jsonStr);
        }
    }
}
