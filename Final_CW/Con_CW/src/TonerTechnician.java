public class TonerTechnician extends Thread{
    private LaserPrinter printer;
    private String name;
 //Represents the toner technician, who replaces the cartridge of the printer
    public TonerTechnician(String name, LaserPrinter printer, ThreadGroup group) {
        super(group,name);
        this.name = name;
        this.printer = printer;
        //this.group = group;
    }

    @Override
    public void run(){
        int refilled_Toner = 0;
        for (int i = 0; i <= 3; i++) {
            // call the method that tries to refill toner
            printer.replaceTonerCartridge(name);
            if (printer.isTonerRefilled()){
                refilled_Toner ++;
            }

            int num = ((int) (Math.random() * 1000));

            try {
                // sleep the current thread for a random amount of time
                sleep(num);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        System.out.println("["+name+"]"+ ":: Replaced Toner Cartridge Completed,   Cartridge used:  " + refilled_Toner);
    }
}
