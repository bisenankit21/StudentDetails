package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class ApplicationExampleTest {
    private static int count=0;

    @Value("${info.app.name}")
    private String appInfo;

    @Value("${info.app.description}")
    private String appDescription;

    @Value("${info.app.version}")
    private String appVersion;

    @Value("${info.school.name}")
    private String appSchoolName;


    @Autowired
    CollegeStudent student;

    @Autowired
    StudentGrades studentGrades;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    public void beforeEach(){
        count=count+1;
        System.out.println("Testing: "+appInfo + " which is " + appDescription + " Version: "+appVersion +"Execution of test method " +count );
        student.setFirstname("Ankit");
        student.setLastname("Bisen");
        student.setEmailAddress("test@gmail.com");
        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0,85.0,76.50,91.75)));
        student.setStudentGrades(studentGrades);
    }

   @DisplayName("Add grade result for student grades")
    @Test
    public void addGradeResultForStudentGrades(){
        assertEquals(353.25,studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
   }

    @DisplayName("Add grade result for student grades not equal")
    @Test
    public void addGradeResultForStudentGradesNotEqual(){
        assertNotEquals(0,studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
    }

    @DisplayName("is grade greater")
    @Test
    public void aisGradeGreaterStudentGrades(){
        assertTrue(studentGrades.isGradeGreater(90,75),"failure should be true");
    }

    @DisplayName("is grade greater false")
    @Test
    public void aisGradeGreaterStudentGradesFalse(){
        assertFalse(studentGrades.isGradeGreater(89,92),"failure should be true");
    }

    @DisplayName("Check null values")
    @Test
    public void checkNullForStudentGrades(){
        assertNotNull(studentGrades.checkNull(student.getStudentGrades().getMathGradeResults()),"Objcect should not be null");

    }
    @DisplayName("Create student without grade init")
    @Test
    public void createStudentWithoutGradesInit(){
        CollegeStudent collegeStudent = context.getBean("collegeStudent" , CollegeStudent.class);
        collegeStudent.setFirstname("Anna");
        collegeStudent.setLastname("Bisen");
        collegeStudent.setEmailAddress("ANna@gmail.com");
        assertNotNull(collegeStudent.getFirstname());
        assertNotNull(collegeStudent.getLastname());
        assertNotNull(collegeStudent.getEmailAddress());
        assertNull(studentGrades.checkNull(collegeStudent.getStudentGrades()));
    }

    @DisplayName("Verify stduent are prototypes")
    @Test
    public void verifyStudentAreProtopty(){
        CollegeStudent collegeStudent = context.getBean("collegeStudent" , CollegeStudent.class);
        assertNotSame(student,collegeStudent);
    }

    @DisplayName("Find grade point average")
    @Test
    public void findGradepointAVeargae(){
        assertAll("Testing All AssertEquals",
                ()->assertEquals(353.25,studentGrades.addGradeResultsForSingleClass(
                        student.getStudentGrades().getMathGradeResults())),
                ()->assertEquals(88.31,studentGrades.findGradePointAverage(
                        student.getStudentGrades().getMathGradeResults())));
    }

}
