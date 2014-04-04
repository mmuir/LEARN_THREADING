package pac;

import java.util.Random;

public class ThreadExperiments {

    public static void main( String[] args ) {

        Object lock1 = new Object();
        Object lock2 = lock1; // new Object();

        MyRunnable r1 = new MyRunnable( lock1, '1' );
        MyRunnable r2 = new MyRunnable( lock2, '2' );

        Thread thread1 = new Thread( r1 );
        Thread thread2 = new Thread( r2 );

        thread1.start();
        thread2.start();

    }

}

class MyRunnable implements Runnable {

    private final Object      lock;
    private final char out;

    public MyRunnable( Object lock, char out ) {
        this.lock = lock;
        this.out = out;
    }

    @Override
    public void run() {
        String id = this.getClass().getSimpleName() + '@'
                    + Long.toHexString( this.hashCode() );
        System.out.println( String.format( "%s Waiting for lock...", id ) );

        synchronized ( this.lock ) {
        	System.out.println( String.format( "%s THIS CODE IS SYNCHRONIZED", id ) );
            Random ra = new Random();
            int seconds = ra.nextInt( 60 );
            System.out.println( String.format( "Waiting for %s seconds", seconds ) );
            try {
                for ( int i = 0; i < seconds; i++ ) {
                    Thread.sleep( 1000 );
                    System.out.print( out );
                    System.out.flush();
                }
                System.out.println();
            } catch ( InterruptedException e ) {
                e.printStackTrace( System.err );
            }
        }
        System.out.println( String.format( "%s relinquished lock", id ) );
    }

}
