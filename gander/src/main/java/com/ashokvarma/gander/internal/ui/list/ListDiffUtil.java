package com.ashokvarma.gander.internal.ui.list;

import android.support.v7.util.DiffUtil;

import com.ashokvarma.gander.internal.data.HttpTransaction;

/**
 * Class description
 *
 * @author ashok
 * @version 1.0
 * @since 03/06/18
 */
public class ListDiffUtil extends DiffUtil.ItemCallback<HttpTransaction> {

    private String mSearchKey;

    public void setSearchKey(String searchKey) {
        this.mSearchKey = searchKey;
    }

    @Override
    public boolean areItemsTheSame(HttpTransaction oldItem, HttpTransaction newItem) {
        // even if both are null => items are not same (animation issues)
        if (newItem != null) {
            // might not work always due to async nature of Adapter.
            // fails in very rare race conditions
            // but increases pref.
            newItem.searchKey = mSearchKey;
        }
        return oldItem != null && newItem != null && oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(HttpTransaction oldItem, HttpTransaction newItem) {
        // both will non null. because of areItemsTheSame logic only non nulls come here
        // comparing only items shown in the list
        return areEqual(oldItem.getMethod(), newItem.getMethod()) &&
                areEqual(oldItem.getPath(), newItem.getPath()) &&
                areEqual(oldItem.getHost(), newItem.getHost()) &&
                areEqual(oldItem.getRequestStartTimeString(), newItem.getRequestStartTimeString()) &&
                (oldItem.isSsl() == newItem.isSsl()) &&
                oldItem.getStatus().equals(newItem.getStatus()) &&
                areEqual(oldItem.getResponseCode(), newItem.getResponseCode()) &&
                areEqual(oldItem.getDurationString(), newItem.getDurationString()) &&
                areEqual(oldItem.getTotalSizeString(), newItem.getTotalSizeString()) &&
                areEqual(oldItem.searchKey, newItem.searchKey);
    }

    private static boolean areEqual(Object oldItem, Object newItem) {
        if (oldItem == null && newItem == null) {
            return true;// both are null
        }
        // only one is null => return false
        return oldItem != null && newItem != null && oldItem.equals(newItem);
    }
}
