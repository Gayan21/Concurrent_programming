public class Student extends Thread{

    private String studentName;   //student name
    private LaserPrinter laserPrinter;  //students printer

    public Student(String studentName, LaserPrinter laserPrinter, ThreadGroup threadGroup) {//Represents a student who uses the printer to print documents
        super(threadGroup, studentName);
        this.studentName = studentName;
        this.laserPrinter = laserPrinter;
    }

    @Override
    public void run() {
        int numOfPages = 0;
        for (int i = 0; i < 5; i++) {
            //Generates document id with the given student name and the document name
            Document document = new Document(studentName,"doc"+i,((int) (Math.random() *10)+1));
            laserPrinter.printDocument(document);
            numOfPages += document.getNumberOfPages();
            try {
                sleep(((int) (Math.random() * 1000)+1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " Finished printing 5 documents " + numOfPages + " pages");
    }
}
