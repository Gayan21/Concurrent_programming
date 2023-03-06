public class PrintingSystem {
    public static void main(String[] args) {

        LaserPrinter printer = new LaserPrinter("RICOH Aficio CL3500N PS");

        // creating two different thread groups for students and technicians
        ThreadGroup studentGroup = new ThreadGroup("Student Thread Group");
        ThreadGroup technicianGroup = new ThreadGroup("Technician Thread Group");

        //creating 4student threads
        Student student1 = new Student("Noah(student_1)",printer,studentGroup);
        Student student2 = new Student("Oliver(student_2)",printer,studentGroup);
        Student student3 = new Student("George(student_3)",printer,studentGroup);
        Student student4 = new Student("Leo(student_4)",printer,studentGroup);

        //creating a thread for paper technician
        PaperTechnician paperTechnician = new PaperTechnician("Jack(Paper_Technician)",printer,technicianGroup);

        //creating a thread for paper technician
        TonerTechnician tonerTechnician = new TonerTechnician("Charlie(Toner_Technician)",printer,technicianGroup);

        student1.start();
        student2.start();
        student3.start();
        student4.start();
        paperTechnician.start();
        tonerTechnician.start();

        try {
            student1.join();
            student2.join();
            student3.join();
            student4.join();
            paperTechnician.join();
            tonerTechnician.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All documents have been printed");
        System.out.print("===================================================================\n" +
                "                PRINTER SUMMARY                  \n" +
                "===================================================================\n");
        System.out.println(printer.toString());
    }
}
