package edu.hw2.task4;

public class Utils {
    private static final int CALLING_METHOD_INDEX = 1;

    private Utils() {
    }

    public static CallingInfo callingInfo() {
        try {
            throw new Throwable();
        } catch (Throwable e) {
            StackTraceElement[] stackTraceElements = e.getStackTrace();
            StackTraceElement stackTraceElement = stackTraceElements[CALLING_METHOD_INDEX];

            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();

            return new CallingInfo(className, methodName);
        }
    }
}
