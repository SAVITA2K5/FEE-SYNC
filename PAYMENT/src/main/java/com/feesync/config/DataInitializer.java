package com.feesync.config;

import com.feesync.model.Student;
import com.feesync.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Data Initializer - Populates sample students on application startup
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;

    @Override
    public void run(String... args) {
        // Check if data already exists
        if (studentRepository.count() > 0) {
            log.info("Sample data already exists. Skipping initialization.");
            return;
        }

        log.info("Initializing sample student data...");

        // Create sample students
        Student student1 = new Student();
        student1.setRollNumber("22CS001");
        student1.setName("Rahul Sharma");
        student1.setTotalFee(50000.0);
        student1.setPaidAmount(0.0);
        student1.setDueAmount(50000.0);
        student1.setStatus(Student.FeeStatus.UNPAID);

        Student student2 = new Student();
        student2.setRollNumber("22CS002");
        student2.setName("Priya Patel");
        student2.setTotalFee(50000.0);
        student2.setPaidAmount(25000.0);
        student2.setDueAmount(25000.0);
        student2.setStatus(Student.FeeStatus.PARTIAL);

        Student student3 = new Student();
        student3.setRollNumber("22CS003");
        student3.setName("Amit Kumar");
        student3.setTotalFee(50000.0);
        student3.setPaidAmount(50000.0);
        student3.setDueAmount(0.0);
        student3.setStatus(Student.FeeStatus.PAID);

        Student student4 = new Student();
        student4.setRollNumber("22CS004");
        student4.setName("Sneha Reddy");
        student4.setTotalFee(50000.0);
        student4.setPaidAmount(0.0);
        student4.setDueAmount(50000.0);
        student4.setStatus(Student.FeeStatus.UNPAID);

        Student student5 = new Student();
        student5.setRollNumber("22CS005");
        student5.setName("Vikram Singh");
        student5.setTotalFee(50000.0);
        student5.setPaidAmount(10000.0);
        student5.setDueAmount(40000.0);
        student5.setStatus(Student.FeeStatus.PARTIAL);

        Student student6 = new Student();
        student6.setRollNumber("22CS006");
        student6.setName("Anjali Gupta");
        student6.setTotalFee(50000.0);
        student6.setPaidAmount(0.0);
        student6.setDueAmount(50000.0);
        student6.setStatus(Student.FeeStatus.UNPAID);

        Student student7 = new Student();
        student7.setRollNumber("22CS007");
        student7.setName("Karan Mehta");
        student7.setTotalFee(50000.0);
        student7.setPaidAmount(50000.0);
        student7.setDueAmount(0.0);
        student7.setStatus(Student.FeeStatus.PAID);

        Student student8 = new Student();
        student8.setRollNumber("22CS008");
        student8.setName("Pooja Verma");
        student8.setTotalFee(50000.0);
        student8.setPaidAmount(30000.0);
        student8.setDueAmount(20000.0);
        student8.setStatus(Student.FeeStatus.PARTIAL);

        // Save all students
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);
        studentRepository.save(student5);
        studentRepository.save(student6);
        studentRepository.save(student7);
        studentRepository.save(student8);

        log.info("Sample data initialized successfully! 8 students added.");
    }
}
