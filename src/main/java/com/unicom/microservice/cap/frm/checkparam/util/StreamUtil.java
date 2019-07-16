package com.unicom.microservice.cap.frm.checkparam.util;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * <div style="font-weight: 900">Description:</div>
 * <p>
 * <div style="font-weight: 900">date:2019/6/17</div>
 *
 * @author caojun
 */
public class StreamUtil {
    public static <T> Stream<T> streamOf(T[] array) {
        return ArrayUtils.isEmpty(array) ? Stream.empty() : Arrays.asList(array).stream();
    }
}
