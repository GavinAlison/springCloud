package com.mtech.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StopWatch {
    private final String id;
    private boolean keepTaskList;
    private final List<TaskInfo> taskList;
    private long startTimeNanos;
    private String currentTaskName;
    private TaskInfo lastTaskInfo;
    private int taskCount;
    private long totalTimeNanos;

    public StopWatch() {
        this("");
    }

    public StopWatch(String id) {
        this.keepTaskList = true;
        this.taskList = new ArrayList(1);
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setKeepTaskList(boolean keepTaskList) {
        this.keepTaskList = keepTaskList;
    }

    public void start() throws IllegalStateException {
        this.start("");
    }

    public void start(String taskName) throws IllegalStateException {
        if (this.currentTaskName != null) {
            throw new IllegalStateException("Can't start StopWatch: it's already running");
        } else {
            this.currentTaskName = taskName;
            this.startTimeNanos = System.nanoTime();
        }
    }

    public void stop() throws IllegalStateException {
        if (this.currentTaskName == null) {
            throw new IllegalStateException("Can't stop StopWatch: it's not running");
        } else {
            long lastTime = System.nanoTime() - this.startTimeNanos;
            this.totalTimeNanos += lastTime;
            this.lastTaskInfo = new TaskInfo(this.currentTaskName, lastTime);
            if (this.keepTaskList) {
                this.taskList.add(this.lastTaskInfo);
            }

            ++this.taskCount;
            this.currentTaskName = null;
        }
    }


    public long getTaskTime(String taskName) {
        return taskList.stream().filter(x -> x.taskName.equals(taskName)).findFirst().get().getTimeMillis();
    }

    public boolean isRunning() {
        return this.currentTaskName != null;
    }

    public String currentTaskName() {
        return this.currentTaskName;
    }

    public long getLastTaskTimeNanos() throws IllegalStateException {
        if (this.lastTaskInfo == null) {
            throw new IllegalStateException("No tasks run: can't get last task interval");
        } else {
            return this.lastTaskInfo.getTimeNanos();
        }
    }

    public long getLastTaskTimeMillis() throws IllegalStateException {
        if (this.lastTaskInfo == null) {
            throw new IllegalStateException("No tasks run: can't get last task interval");
        } else {
            return this.lastTaskInfo.getTimeMillis();
        }
    }

    public String getLastTaskName() throws IllegalStateException {
        if (this.lastTaskInfo == null) {
            throw new IllegalStateException("No tasks run: can't get last task name");
        } else {
            return this.lastTaskInfo.getTaskName();
        }
    }

    public TaskInfo getLastTaskInfo() throws IllegalStateException {
        if (this.lastTaskInfo == null) {
            throw new IllegalStateException("No tasks run: can't get last task info");
        } else {
            return this.lastTaskInfo;
        }
    }

    public long getTotalTimeNanos() {
        return this.totalTimeNanos;
    }

    public long getTotalTimeMillis() {
        return nanosToMillis(this.totalTimeNanos);
    }

    public double getTotalTimeSeconds() {
        return nanosToSeconds(this.totalTimeNanos);
    }

    public int getTaskCount() {
        return this.taskCount;
    }

    public TaskInfo[] getTaskInfo() {
        if (!this.keepTaskList) {
            throw new UnsupportedOperationException("Task info is not being kept!");
        } else {
            return (TaskInfo[]) this.taskList.toArray(new TaskInfo[0]);
        }
    }

    public String shortSummary() {
        return "StopWatch '" + this.getId() + "': running time = " + this.getTotalTimeNanos() + " ns";
    }

    public String prettyPrint() {
        StringBuilder sb = new StringBuilder(this.shortSummary());
        sb.append('\n');
        if (!this.keepTaskList) {
            sb.append("No task info kept");
        } else {
            sb.append("---------------------------------------------\n");
            sb.append("ns         %     Task name\n");
            sb.append("---------------------------------------------\n");
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMinimumIntegerDigits(9);
            nf.setGroupingUsed(false);
            NumberFormat pf = NumberFormat.getPercentInstance();
            pf.setMinimumIntegerDigits(3);
            pf.setGroupingUsed(false);
            TaskInfo[] var4 = this.getTaskInfo();
            int var5 = var4.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                TaskInfo task = var4[var6];
                sb.append(nf.format(task.getTimeNanos())).append("  ");
                sb.append(pf.format((double) task.getTimeNanos() / (double) this.getTotalTimeNanos())).append("  ");
                sb.append(task.getTaskName()).append("\n");
            }
        }

        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.shortSummary());
        if (this.keepTaskList) {
            TaskInfo[] var2 = this.getTaskInfo();
            int var3 = var2.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                TaskInfo task = var2[var4];
                sb.append("; [").append(task.getTaskName()).append("] took ").append(task.getTimeNanos()).append(" ns");
                long percent = Math.round(100.0D * (double) task.getTimeNanos() / (double) this.getTotalTimeNanos());
                sb.append(" = ").append(percent).append("%");
            }
        } else {
            sb.append("; no task info kept");
        }

        return sb.toString();
    }

    private static long nanosToMillis(long duration) {
        return TimeUnit.NANOSECONDS.toMillis(duration);
    }

    private static double nanosToSeconds(long duration) {
        return (double) duration / 1.0E9D;
    }

    public static final class TaskInfo {
        private final String taskName;
        private final long timeNanos;

        TaskInfo(String taskName, long timeNanos) {
            this.taskName = taskName;
            this.timeNanos = timeNanos;
        }

        public String getTaskName() {
            return this.taskName;
        }

        public long getTimeNanos() {
            return this.timeNanos;
        }

        public long getTimeMillis() {
            return StopWatch.nanosToMillis(this.timeNanos);
        }

        public double getTimeSeconds() {
            return StopWatch.nanosToSeconds(this.timeNanos);
        }
    }
}