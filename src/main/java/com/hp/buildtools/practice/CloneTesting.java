package com.hp.buildtools.practice;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CloneTesting extends Employee{

    public static void main(String args[]){

        List<String> hobbies = Arrays.asList("Chess", "Caroms", "Badminton" );
        Department d1 = new Department(1l, "Innovation", new Date());
        Employee e1 = new Employee("John", hobbies, new Date(), d1);

        Employee e2 = e1.makeCopy();
        e1.setEmployeeName("Bose");
        e1.setHobbies(Arrays.asList("Chess", "Caroms"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        e2.setDateOfJoining(new Date());
        e1.display();
        e2.display();
    }


}
