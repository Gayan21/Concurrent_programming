public class LaserPrinter implements ServicePrinter { //Represents the laser printer resource, which is shared within the printing system

    private Document document;
    private final String printer_Id;
    private int paper_Level;
    private int toner_Level;
    private int count_Of_Documents_Printed;
    //private boolean waited = false;
    private boolean paper_Refilled = false;
    private boolean toner_Refilled = false;

    public LaserPrinter(String printerId) {
        this.printer_Id = printerId;
        this.paper_Level = Full_Paper_Tray;
        this.toner_Level = Full_Toner_Level;
        this.count_Of_Documents_Printed = 0;
    }

    @Override
    public synchronized void printDocument(Document document) {

        int numOfPages = document.getNumberOfPages();

        boolean insufficientPapers = numOfPages > paper_Level; //to identify if the printer has sufficient papers
        boolean insufficientTonerLevel = numOfPages > toner_Level; //to identify if the printer has sufficient toner level
        // When resources are insufficient, wait until resources become sufficient
        while ( insufficientPapers && insufficientTonerLevel){

            try {
                System.out.println("Out of resource.. Waiting");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        if ( paper_Level > numOfPages && toner_Level > numOfPages){
            //reduce the resources after printing
            this.paper_Level -= document.getNumberOfPages();
            this.toner_Level -= document.getNumberOfPages();
            this.count_Of_Documents_Printed ++;
            //toString();  //print the document if there are sufficient resources.
            System.out.println("---------------- "+document.getUserID()+ " printed the document "+" -------------------");
            System.out.println(toString()+"\n");
        }



        notifyAll(); //to notify all other threads that are in wait-set.

    }

    @Override
    public synchronized void replaceTonerCartridge(String techName) { //to replace the toner cartridge
        boolean tried = false;
        this.toner_Refilled = false;
        // Repeatedly check for the need to replace toner cartridge, in 5 seconds time interval
        while (toner_Level >= Minimum_Toner_Level){
            if(tried){ //to stop going to a  infinity loop
                break;
            }
            try {
                System.out.println(" ***** Toner technician is waiting for 5 seconds for refill toner*****");
                System.out.println("-----------------------------------------------------------------------");
                wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tried = true;
        }
      // Replace toner cartridge if necessary
        if(toner_Level<Minimum_Toner_Level) {
            toner_Level = Full_Toner_Level;
            this.toner_Refilled = true;
            System.out.println("\nToner Cartridge has been changed. New toner Level: " + toner_Level + ", technician:" + techName);
        }
        notifyAll();
    }

    public boolean isTonerRefilled(){
        return this.toner_Refilled;
    }

    @Override
    public synchronized void refillPaper(String techName) { //to refill the paper tray
        boolean tried = false;
        this.paper_Refilled = false;
        // Repeatedly check for the need to refill paper, in 5 seconds time interval
        boolean sufficientPapers = ( paper_Level + SheetsPerPack) > Full_Paper_Tray;
        while (sufficientPapers){
            if (tried == true) {
                break;
            }
            try {
                System.out.println(" ***** Paper technician is waiting for 5 seconds for refill paper***** ");

                wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tried = true;
        }

        // Refill paper if necessary
        if((paper_Level+SheetsPerPack)< Full_Paper_Tray){
            paper_Level += SheetsPerPack;
            this.paper_Refilled = true;
            System.out.println("Refilled paper. New  Level: " + paper_Level + ", technician:" + techName);
        }
        notifyAll();
    }

    public boolean isPaperRefilled(){
        return this.paper_Refilled;
    }


    public String toString() {
        return "[ "
                + "PrinterID: " + this.printer_Id
                + "\tPaper Level: " + this.paper_Level
                + "\tToner Level: " + this.toner_Level
                + "\tDocuments Printed: " + this.count_Of_Documents_Printed
                + " ]";
    }


}
