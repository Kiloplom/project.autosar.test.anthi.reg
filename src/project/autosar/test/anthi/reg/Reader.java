package project.autosar.test.anthi.reg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.autosar.test.anthi.reg.FileUtils;


public class Reader {
	private static final String START_OF_PROCESS = "Start of process:";
	private static final String MODEL_TO_MIGRATE = "ModelToMigrate";
	private static final String LUNCH_EXPORT_IMPORT = "lunchExportImport";
	private static final String NEWS_CHECKS = "NewsChecks";
	private static final String ECU_EXTRACT = "ECUExtract";
	private static final String RIA_PERFORMANCE_TEST = "RiA_Performance_Test";
	private static final String TOTAL_MILLISECONDS = "TotalMilliseconds";
	static Logger log = Logger.getLogger(Reader.class.getName());
	HashMap<String, HashMap<String, String> > mapResult  = new HashMap<>();
	public Reader() {
		
	}

	public static void main(String[] args) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy");
		LocalDate localDate = LocalDate.now();
		String pathDirResult = "G:\\Shared drives\\Projects\\Rhapsody\\AUTOSAR\\AnthiRegTest\\Result_lastest\\";
		Reader reader = getResultFromFile(pathDirResult);
        reader.writingTxtResult(pathDirResult + "ResultGlobal_" + dtf.format(localDate) + ".log");
        
        System.out.println("end");
	}

	private static Reader getResultFromFile(String pathDirResult) {
		String[] pathnames;
		File f = new File(pathDirResult);
		Reader reader = new Reader();
		String dirPath = f.getPath();
		pathnames = f.list();

        // For each pathname in the pathnames array
        for (String pathname : pathnames) {
            String fileExtansion = getValueWithPattern("\\.txt$", pathname);
			if (fileExtansion != null && !fileExtansion.isEmpty()) {
            	String rhpVersion = getValueWithPattern("[0-9]+", pathname);
            	String path = dirPath + "\\" + pathname;
				reader.mapResult.put(rhpVersion, reader.readFile(path));
            }
        }
		return reader;
	}
	
	
	void writingTxtResult(String path) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(path, "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<String> rhpVersionSet = mapResult.keySet();
		int sizeRhpVersion = rhpVersionSet.size();
		List<String> rhpVersions = new ArrayList<String>(sizeRhpVersion); 
		for (String version : rhpVersionSet) {
			rhpVersions.add(version); 
		}
		String lastVerison = rhpVersions.get(sizeRhpVersion - 1);
		HashMap<String, String> repportMap = new HashMap<String, String>();
		if (writer !=null) {
			writer.println("======================================================");
			for (int k = 0; k<sizeRhpVersion; k++) {
				String rhpVersion = rhpVersions.get(k);
				writer.println("RhpVersion: " + rhpVersion);
				HashMap<String, String> testReseult = mapResult.get(rhpVersion);
				for (String test : testReseult.keySet()) {
					writer.println(test + ": " + testReseult.get(test));
					
				}
				writer.println("======================================================");
			}
			
			writer.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			calculePercentage(writer, rhpVersions, lastVerison, 0);
			writer.println("------------------------------------------------------");
			calculePercentage(writer, rhpVersions, lastVerison, 1);
			writer.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			writer.close();
		}
	}

	private void calculePercentage(PrintWriter writer, List<String> rhpVersions, String lastVerison, int oldRhpVersion) {
		HashMap<String, String> testReseultLastVersion = mapResult.get(lastVerison);
		Set<String> keySetTests = mapResult.get(lastVerison).keySet();
		writer.println("Calcule to " + rhpVersions.get(oldRhpVersion) + " - " + lastVerison);
		for(String test : keySetTests) {
			int totalTime = Integer.parseInt(testReseultLastVersion.get(test));
			int value = ((getTheResult(rhpVersions.get(oldRhpVersion), test) - totalTime )*100)/totalTime;
			writer.println(test + ": " + Integer.toString(value) + "%");
		}
	}
	
	int getTheResult(String version, String test) {
		return Integer.parseInt(mapResult.get(version).get(test));
	}
	
	HashMap<String, String> readFile(String path) {
		 HashMap<String, String> mapResultRhpVersion = new HashMap<String, String>();
		 File batch = new File(path);
	        if (batch.exists()) {
	            BufferedReader br = null;
	            StringBuffer sb = new StringBuffer();
	            String textinLine;
	            try {
	                br = FileUtils.createBufferedReader(batch);
	
	                while (br.ready()) {
	                    textinLine = br.readLine();
						if (textinLine.contains(MODEL_TO_MIGRATE) && textinLine.contains(START_OF_PROCESS)) {
							String extractTimeMesurement = extractTimeMesurement(br);
	                    	mapResultRhpVersion.put(MODEL_TO_MIGRATE, extractTimeMesurement);
	                    } 
	                    if (textinLine.contains(LUNCH_EXPORT_IMPORT) && textinLine.contains(START_OF_PROCESS)) {
	                    	String extractTimeMesurement = extractTimeMesurement(br);
	                    	mapResultRhpVersion.put(LUNCH_EXPORT_IMPORT, extractTimeMesurement);
	                    } 
	                    if (textinLine.contains(NEWS_CHECKS) && textinLine.contains(START_OF_PROCESS)) {
	                    	String extractTimeMesurement = extractTimeMesurement(br);
	                    	mapResultRhpVersion.put(NEWS_CHECKS, extractTimeMesurement);
	                    } 
	                    if (textinLine.contains(ECU_EXTRACT) && textinLine.contains(START_OF_PROCESS)) {
	                    	String extractTimeMesurement = extractTimeMesurement(br);
	                    	mapResultRhpVersion.put(ECU_EXTRACT, extractTimeMesurement);
	                    } 
	                    if (textinLine.contains(RIA_PERFORMANCE_TEST) && textinLine.contains(START_OF_PROCESS)) {
	                    	String extractTimeMesurement = extractTimeMesurement(br);
	                    	mapResultRhpVersion.put(RIA_PERFORMANCE_TEST, extractTimeMesurement);
	                    } 
	                }
	            } catch (IOException e) {
					log.severe(e.toString());
	            } finally {
	                FileUtils.closeQuietly(br);
	            }
	        }
	        
	        return mapResultRhpVersion;
}

	private String extractTimeMesurement(BufferedReader br) throws IOException {
		String textinLine = "";;
		String mesurement = getValueWithPattern("[A-Za-z]+", textinLine);
		while (br.ready()) {
			textinLine = br.readLine();
			mesurement = getValueWithPattern("[A-Za-z]+", textinLine);
			if (mesurement != null && mesurement.equals(TOTAL_MILLISECONDS)) {
				mesurement = getValueWithPattern("[0-9]+", textinLine);
				break; 
			}
		}
		return mesurement;
	}
	
	public static String getValueWithPattern(String pattern, String text) {
        List<String> list = getListValueWithPattern(pattern, text);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    protected static List<String> getListValueWithPattern(String pattern, String text) {
        Pattern addressPattern = Pattern.compile(pattern);
        Matcher matcher = addressPattern.matcher(text);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

}
