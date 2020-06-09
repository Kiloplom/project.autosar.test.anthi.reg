package project.autosar.test.anthi.reg;
/********************************************************************************
*Licensed Materials - Property of IBM
*(c) Copyright IBM Corporation 2014, 2017. All Rights Reserved.
*
*Note to U.S. Government Users Restricted Rights:
*Use, duplication or disclosure restricted by GSA ADP Schedule
*Contract with IBM Corp.
********************************************************************************/


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;


public class ExcelResultWriter {

    protected WritableWorkbook workbook;
    protected HashMap<String, HashMap<String, String> > mapResult;

    public ExcelResultWriter(WritableWorkbook workbook, HashMap<String, HashMap<String, String> > mapResult) {
    	this.workbook = workbook;
    	this.mapResult = mapResult;
    }

    void createNewSheet() {
    	workbook.getSheet(2).getName();
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		LocalDate localDate = LocalDate.now();
    	workbook.copySheet(1, dtf.format(localDate), 1);
    	
    	WritableSheet sheet = workbook.getSheet(1);
    	Label label = new Label(1, 2, "Passed");
    	try {
			sheet.addCell(label);
		} catch (RowsExceededException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (WriteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	try {
			workbook.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }





}
