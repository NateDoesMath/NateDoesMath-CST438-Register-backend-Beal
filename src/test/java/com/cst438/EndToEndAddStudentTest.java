package com.cst438;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

@SpringBootTest
public class EndToEndAddStudentTest {

	 public static final String CHROME_DRIVER_FILE_LOCATION = "C:/Users/Ass/Desktop/chromedriver.exe";

    public static final String URL = "https://cst438-register-fe.herokuapp.com/";
    
    public static final String TEST_USER_EMAIL = "test@csumb.edu";
    
    public static final String TEST_USERNAME = "test";
    
    public static final int TEST_ID = 2;
    
    public static final String TEST_STATUS = "0";
    
    public static final int TEST_COURSE_ID = 40443;
    
    public static final int SLEEP_DURATION = 1000; // 1 second.
    
    @Autowired
    EnrollmentRepository enrollmentRepository;

    @Autowired
    CourseRepository courseRepository;
    
    @Autowired
    StudentRepository studentRepository;

    @Test
    public void createStudent() throws Exception {
   	 
   	 //Deletes existing students in the database.
   	 Student x = null;
 		 do {
 			 x = studentRepository.findByEmail(TEST_USER_EMAIL);
 			 if (x != null)
 				 studentRepository.delete(x);
 		 } while (x != null);
 		 
 		 
        // set the driver location and start driver
        //@formatter:off
        // browser    property name                Java Driver Class
        // edge       webdriver.edge.driver        EdgeDriver
        // FireFox    webdriver.firefox.driver     FirefoxDriver
        // IE         webdriver.ie.driver          InternetExplorerDriver
        //@formatter:on

        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();
        // Puts an Implicit wait for 10 seconds before throwing exception
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        
        try {
      	  
      	   driver.get(URL);
      	   Thread.sleep(SLEEP_DURATION);

            
            // Locate and click New Assignment button
            driver.findElement(By.xpath("//a[last()]")).click();
            Thread.sleep(SLEEP_DURATION);
            
            // Locate text fields and enter the data
            WebElement we = driver.findElement(By.xpath("//input[@name='studentName']"));
            we.sendKeys("test");
            we = driver.findElement(By.xpath("//input[@name='studentEmail']"));
            we.sendKeys(TEST_USER_EMAIL);
            we = driver.findElement(By.xpath("//input[@name='statusCode']"));
            we.sendKeys(TEST_STATUS);
            
            
            driver.findElement(By.xpath("//input[@name='submitButton']")).click();
            Thread.sleep(SLEEP_DURATION);

            //Verify to see if student is in database
            Student s = studentRepository.findByEmail(TEST_USER_EMAIL);
            we = driver.findElement(By.xpath("//div[@data-field='name' and @data-value='test'" + s.getName() + "']"));
            assertNotNull(we, "student found");

        } catch (Exception ex) {
            throw ex;
        } finally {

      	  // clean up database.
  				Student s = studentRepository.findByEmail(TEST_USER_EMAIL);
  				if (s != null)
  					studentRepository.delete(s);

            driver.quit();
        }

    }
	
}
