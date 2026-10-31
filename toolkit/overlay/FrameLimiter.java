// PlayFDroid Frame Limiter Helper
// Caps frame-rate to 60 FPS (16.6ms) to prevent game physics running at 2x/2.4x speed
// on modern 90Hz, 120Hz, or 144Hz high refresh-rate screens.
//
// Usage inside GLSurfaceView.Renderer.onDrawFrame() or SurfaceView game loop:
//     private final FrameLimiter limiter = new FrameLimiter(60);
//     public void onDrawFrame(GL10 gl) {
//         limiter.sync();
//         // game render logic
//     }
//
package org.playfdroid.compat;

import android.os.SystemClock;

public class FrameLimiter {
    private final long targetFrameTimeNs;
    private long lastFrameTimeNs;

    public FrameLimiter(int targetFps) {
        if (targetFps <= 0) targetFps = 60;
        this.targetFrameTimeNs = 1_000_000_000L / targetFps;
        this.lastFrameTimeNs = System.nanoTime();
    }

    public void sync() {
        long now = System.nanoTime();
        long elapsed = now - lastFrameTimeNs;
        long sleepTimeNs = targetFrameTimeNs - elapsed;

        if (sleepTimeNs > 0) {
            long sleepMillis = sleepTimeNs / 1_000_000L;
            int sleepNanos = (int) (sleepTimeNs % 1_000_000L);
            try {
                Thread.sleep(sleepMillis, sleepNanos);
            } catch (InterruptedException ignored) {
            }
        }
        lastFrameTimeNs = System.nanoTime();
    }
}
