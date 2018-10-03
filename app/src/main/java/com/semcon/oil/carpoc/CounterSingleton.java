package com.semcon.oil.carpoc;

class CounterSingleton
{
    // static variable single_instance of type Singleton
    private static CounterSingleton single_instance = null;
    static Thread t2;
    // variable of type String
    public static int counter = 0;

    // private constructor restricted to this class itself
    public CounterSingleton()
    {


    }

    // static method to create instance of Singleton class
    public static CounterSingleton getInstance()
    {
        if (single_instance == null) {
            single_instance = new CounterSingleton();
            startCounter();
        }
        return single_instance;
    }
    public int getCounter(){

        return counter;
    }

    private static void startCounter(){

         t2= new Thread(){
            @Override
            public void run() {
                while (!isInterrupted()) {

                    try {
                        Thread.sleep(1000);


                        counter += 1;

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t2.start();

    }
}