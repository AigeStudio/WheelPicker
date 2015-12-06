package com.aigestudio.wheelpicker.view;

/**
 * 滚轮工具类
 * Utils of WheelView
 *
 * @author AigeStudio 2015-12-03
 * @version 1.0.0 preview
 */
final class WheelUtil {
    static int calculateRadius(int count, float length) {
        return (int) ((count + 1) * length / Math.PI);
    }

    static int calculateSpace(float degree, float radius) {
        return (int) (Math.sin(Math.toRadians(degree)) * radius);
    }

    static int calculateDegree(int count) {
        return (int) (180 * 1.0 / (count + 1));
    }

    static int calculateDegree(float dis, float radius) {
        return (int) Math.toDegrees(Math.asin(dis * 1.0 / radius));
    }

    static int calculateDepth(float degree, float radius) {
        return (int) (radius - Math.cos(Math.toRadians(degree)) * radius);
    }
}