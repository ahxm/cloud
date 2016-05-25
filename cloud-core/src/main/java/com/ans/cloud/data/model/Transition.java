package com.ans.cloud.data.model;

import java.io.Serializable;

/**
 * 状态转换
 * Created by anzhen on 15-2-28.
 */
public class Transition<M> implements Serializable {
    private M model;
    private int from;
    private int to;

    public Transition() {
    }

    public Transition(M model, int from, int to) {
        this.model = model;
        this.from = from;
        this.to = to;
    }

    public M getModel() {
        return model;
    }

    public void setModel(M model) {
        this.model = model;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
