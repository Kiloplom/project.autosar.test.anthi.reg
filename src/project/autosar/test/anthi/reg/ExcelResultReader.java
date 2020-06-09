package project.autosar.test.anthi.reg;
/********************************************************************************
*Licensed Materials - Property of IBM
*(c) Copyright IBM Corporation 2014, 2017. All Rights Reserved.
*
*Note to U.S. Government Users Restricted Rights:  
*Use, duplication or disclosure restricted by GSA ADP Schedule 
*Contract with IBM Corp.
********************************************************************************/


import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;

import java.io.File;
import java.io.IOException;

public class ExcelResultReader {

    private Workbook workbook = null;
	private WritableWorkbook writableWorkbook;

    public ExcelResultReader(String path) {
    	read(path);
    }

    public void read(String path) {
    	try {
			this.workbook = Workbook.getWorkbook(new File(path));
			writableWorkbook = Workbook.createWorkbook(new File("Z:\\JB\\git\\project.autosar.test.anthi.reg\\inputFile\\temp.xls"), workbook);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public WritableWorkbook getWorkbook() {
        return writableWorkbook;
    }

    public void setWorkbook(WritableWorkbook writableWorkbook) {
        this.writableWorkbook = writableWorkbook;
    }
}
