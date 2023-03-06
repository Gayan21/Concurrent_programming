public class PaperTechnician extends Thread{
    private String name;
    private LaserPrinter printer;


    public PaperTechnician(String name, LaserPrinter printer, ThreadGroup group) {
        super(group,name);
        this.name = name;
        this.printer = printer;

    }

    @Override
    public void run(){
        int refilled_Papers = 0;
        for (int i = 0; i <= 3; i++) {
            // call the method that tries to refill papers
            printer.refillPaper(name);
            if (printer.isPaperRefilled()){
                refilled_Papers ++;
            }

            int num = ((int) (Math.random() * 1000));

            try {
                // sleep the current thread for a random amount of time
                sleep(num);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        System.out.println("["+name+"]"+ ":: Refilled completed, packs of paper used:  " + refilled_Papers);
    }
}
