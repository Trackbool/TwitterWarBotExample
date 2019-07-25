package timer;

import timer.models.EvaluableCondition;
import timer.models.TimeIntervalModel;
import timer.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskExecutor {
    private static final int CHECKER_RATE_MILLIS = 500;
    private Timer checker;
    private Timer taskTimerExecutor;
    private boolean isTaskExecuting;
    private Runnable task;
    private Runnable onFinish;
    private EvaluableCondition finishCondition;
    private long startDelayMillis;
    private long rateMillis;
    private List<TimeIntervalModel> timeIntervals;

    public TimerTaskExecutor(Runnable task, long startDelayMillis, long rateMillis) {
        checker = new Timer();
        isTaskExecuting = false;
        this.task = task;
        this.startDelayMillis = startDelayMillis;
        this.rateMillis = rateMillis;
        timeIntervals = new ArrayList<>();
        finishCondition = () -> false;
    }

    public TimerTaskExecutor(Runnable task, long startDelayMillis, long rateMillis, List<TimeIntervalModel> timeIntervals) {
        this(task, startDelayMillis, rateMillis);
        this.timeIntervals = timeIntervals;
    }

    public void setOnFinish(Runnable onFinish) {
        this.onFinish = onFinish;
    }

    public void setFinishCondition(EvaluableCondition condition) {
        finishCondition = condition;
    }

    public void setTimeIntervals(List<TimeIntervalModel> timeIntervals) {
        this.timeIntervals = timeIntervals;
    }

    public void start() {
        startChecker();
    }

    private void startChecker() {
        checker.schedule(new TimerTask() {
            @Override
            public void run() {
                if (finishCondition.evaluate()) {
                    finish();
                } else if (!isTaskExecuting && isAllowedToExecute()) {
                    startTask();
                } else if (isTaskExecuting && !isAllowedToExecute()) {
                    stopTask();
                }
            }
        }, startDelayMillis, CHECKER_RATE_MILLIS);
    }

    public void finish() {
        if (isTaskExecuting) {
            taskTimerExecutor.cancel();
        }
        if (onFinish != null) {
            onFinish.run();
        }
        checker.cancel();
    }

    private void startTask() {
        taskTimerExecutor = new Timer();
        taskTimerExecutor.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, 0, rateMillis);
        isTaskExecuting = true;
    }

    private void stopTask() {
        taskTimerExecutor.cancel();
        isTaskExecuting = false;
    }

    private boolean isAllowedToExecute() {
        boolean allowed = true;
        boolean exit = false;
        int cont = 0;
        while (cont < timeIntervals.size() && !exit) {
            exit = TimeUtils.isCurrentTimeBetweenTimeInterval(timeIntervals.get(cont));
            allowed = exit;
            cont++;
        }
        return allowed;
    }
}