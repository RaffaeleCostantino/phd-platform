/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.dottorato.phdProfile.missions;

import it.unisa.dottorato.account.PhdStudent;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tommaso
 */
public class MissionManagerTest {
    
    public MissionManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class MissionManager.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        MissionManager expResult = null;
        MissionManager result = MissionManager.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insert method, of class MissionManager.
     */
    @Test
    public void testInsert() throws Exception {
        System.out.println("insert");
        Mission pMission = null;
        MissionManager instance = null;
        instance.insert(pMission);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class MissionManager.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        int oldMissionID = 0;
        Mission pMission = null;
        MissionManager instance = null;
        instance.update(oldMissionID, pMission);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class MissionManager.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        String idMission = "";
        MissionManager instance = null;
        instance.delete(idMission);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMissionById method, of class MissionManager.
     */
    @Test
    public void testGetMissionById() throws Exception {
        System.out.println("getMissionById");
        int pMissionID = 0;
        MissionManager instance = null;
        Mission expResult = null;
        Mission result = instance.getMissionById(pMissionID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllMissionsOf method, of class MissionManager.
     */
    @Test
    public void testGetAllMissionsOf() throws Exception {
        System.out.println("getAllMissionsOf");
        PhdStudent phdStudent = null;
        MissionManager instance = null;
        List<Mission> expResult = null;
        List<Mission> result = instance.getAllMissionsOf(phdStudent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of testId method, of class MissionManager.
     */
    @Test
    public void testTestId() throws Exception {
        System.out.println("testId");
        int id = 0;
        MissionManager instance = null;
        int expResult = 0;
        int result = instance.testId(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of testDescription method, of class MissionManager.
     */
    @Test
    public void testTestDescription() throws Exception {
        System.out.println("testDescription");
        String description = "";
        MissionManager instance = null;
        String expResult = "";
        String result = instance.testDescription(description);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of testStartDate method, of class MissionManager.
     */
    @Test
    public void testTestStartDate() throws Exception {
        System.out.println("testStartDate");
        Date startDate = null;
        MissionManager instance = null;
        Date expResult = null;
        Date result = instance.testStartDate(startDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of testEndDate method, of class MissionManager.
     */
    @Test
    public void testTestEndDate() throws Exception {
        System.out.println("testEndDate");
        Date endDate = null;
        MissionManager instance = null;
        Date expResult = null;
        Date result = instance.testEndDate(endDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of testReference method, of class MissionManager.
     */
    @Test
    public void testTestReference() throws Exception {
        System.out.println("testReference");
        String reference = "";
        MissionManager instance = null;
        String expResult = "";
        String result = instance.testReference(reference);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of testPlace method, of class MissionManager.
     */
    @Test
    public void testTestPlace() throws Exception {
        System.out.println("testPlace");
        String place = "";
        MissionManager instance = null;
        String expResult = "";
        String result = instance.testPlace(place);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of testMission method, of class MissionManager.
     */
    @Test
    public void testTestMission() throws Exception {
        System.out.println("testMission");
        Mission m = null;
        MissionManager instance = null;
        Mission expResult = null;
        Mission result = instance.testMission(m);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of testfkPhdStudent method, of class MissionManager.
     */
    @Test
    public void testTestfkPhdStudent() throws Exception {
        System.out.println("testfkPhdStudent");
        String fkPhdstudent = "";
        MissionManager instance = null;
        String expResult = "";
        String result = instance.testfkPhdStudent(fkPhdstudent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nextNumber method, of class MissionManager.
     */
    @Test
    public void testNextNumber() throws Exception {
        System.out.println("nextNumber");
        MissionManager instance = null;
        int expResult = 0;
        int result = instance.nextNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}