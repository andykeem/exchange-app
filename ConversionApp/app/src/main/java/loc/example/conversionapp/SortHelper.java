package loc.example.conversionapp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SortHelper {

    public void sortArray(Double[] nums) {
//        for (int i = 0; i < list.size() - 1; i++) {
//            int maxIdx = i;
//            for (int j = i + 1; j < list.size(); j++) {
//                if (list.get(j) > list.get(i)) {
//                    maxIdx = j;
//                }
//            }
//            int tmp = list.get(i);
//            list.set(i, list.get(j));
//
//        }
        Arrays.sort(nums, Collections.reverseOrder());
    }
}
