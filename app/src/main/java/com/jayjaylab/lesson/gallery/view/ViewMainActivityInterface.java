package com.jayjaylab.lesson.gallery.view;

import com.jayjaylab.lesson.gallery.util.model.Image;

import java.util.List;
import java.util.Map;

/**
 * Created by jjkim on 2016. 6. 14..
 */
public interface ViewMainActivityInterface {
    void storeImageMap(Map<String, List<Image>> map);
    void showEveryImage(Map<String, List<Image>> map, List<Image> originalImages);
}
