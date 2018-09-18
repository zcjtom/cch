package com.uxsino.sets.implementation;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.uxsino.sets.ISet;
import com.uxsino.sets.ISetEngine;


public class SetEngineImplementation implements ISetEngine {

    private Collection<ISet> sets = new ArrayList<>();

    public void Load(Collection<ISet> sets) {
        this.sets = sets;
    }

    /**
     * @param values int数据。
     * @return 返回所有加载的Set中的m_Values包含set的m_ID数组。
     */
    public Integer[] FindSupersetsOf(Integer[] values) {
        List<Integer> ids = new ArrayList<>();

        for (ISet set : this.sets) {
            int foundNum = 0;
            for (Integer value : values) {
                for (Integer myValue : set.getValues()) {
                    if (value.equals(myValue)) {
                        foundNum++;
                        break;
                    }
                }
            }

            if (values.length == foundNum) {
                ids.add(set.getID());
            }
        }

        return ids.toArray(new Integer[ids.size()]);
    }

}
