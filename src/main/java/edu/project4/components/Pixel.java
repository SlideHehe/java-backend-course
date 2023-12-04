package edu.project4.components;

import java.util.concurrent.atomic.AtomicInteger;

public class Pixel {
    AtomicInteger r;
    AtomicInteger g;
    AtomicInteger b;
    AtomicInteger hitCount;

    public Pixel(int r, int g, int b, int hitCount) {
        this.r = new AtomicInteger(r);
        this.g = new AtomicInteger(g);
        this.b = new AtomicInteger(b);
        this.hitCount = new AtomicInteger(hitCount);
    }

    public int getR() {
        return r.get();
    }

    public void setR(int r) {
        this.r.set(r);
    }

    public int getG() {
        return g.get();
    }

    public void setG(int g) {
        this.g.set(g);
    }

    public int getB() {
        return b.get();
    }

    public void setB(int b) {
        this.b.set(b);
    }

    public int getHitCount() {
        return hitCount.get();
    }

    public void incrementHitCount() {
        hitCount.incrementAndGet();
    }
}
