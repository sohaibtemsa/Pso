public class Threadd implements Runnable {
    public int[] cnt;
    private boolean done = false;
    public Threadd(int[] cnt) {
        this.cnt = cnt;
    }

    volatile int tmp = 0;

    public void run() {
        for (int start = 0; start < (cnt.length); start++) {
            if (tmp < cnt[start]) {
                tmp = cnt[start];
            }
                System.out.println("Im the thread " + Thread.currentThread().getName() + " : ---> i = " + start +
                        " : --> tmp = " + tmp + " : --> cnt[i] = " + cnt[start]);
            synchronized (this) {
                for (int start2 = 0; start2 < 2; start2++){
                    System.out.println(start2);
                }
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        done = true;
        synchronized (this) {
            this.notifyAll();
        }
    }

    public int getValue() throws InterruptedException {
        synchronized (this) {
            if (!done) {
                System.out.println(Thread.currentThread().getName()+ " is waiting");
                this.wait();
            }
        }
        return this.tmp;
    }
}