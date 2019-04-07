package sjsu.cmpe277.sudha.smartscan;

import java.io.Serializable;
 
 public class Controller implements Serializable {
 
	private static int id;
	private String fileName;
	private String filePath;
	private String imagePath;
	
	public static int getId() {

	    return id;
    }

    public void setId(int id) {
        this.id = id;
    }
	
	public String getFileName() {
        return fileName;
    }

    public void setFileName(String name) {
        fileName = name;
    }
	
	public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String fPath) {
        filePath = fPath;
    }
	
	public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String iPath) {
        imagePath = iPath;
    }

    public Controller() { }
    
	public Controller(int id, String iPath, String fPath, String fName) {
		this.id = id;
        fileName = fName;
        filePath = fPath;
        imagePath = iPath;
    }
	
}