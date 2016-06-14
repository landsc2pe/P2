package com.jayjaylab.lesson.gallery.util.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by HOMIN on 2016-06-14.
 */
public class MapInfo {
    private Map hashMap;

    private int mapSize;
    private List<String> folderName;
    private List<String> keyArray;

    public MapInfo(Map<String, List<Image>> map) {
        hashMap = map;
        mapSize = map.size();
        folderName = new ArrayList<>();
        keyArray = new ArrayList<>();

        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String keys = iter.next();
            keyArray.add(keys);
            String[] it = keys.split("/");
            int a = it.length - 1;
            folderName.add(it[a]);
        }

    }


    public int getMapSize() {
        return mapSize;
    }

    public List<String> getFolderName() {
        return folderName;
    }

    public List<String> getKeyArray() {
        return keyArray;
    }
}


