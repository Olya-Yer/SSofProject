import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Scanner;





import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonReader {

	public void start() throws IOException {

		JsonObject theWholeFile = readFile();
		if (theWholeFile != null) {
		
			//System.out.println(theWholeFile.toString());
			//System.out.print(getChildren(theWholeFile));			
						
			//JsonArray children = getChildren(theWholeFile);
			//JsonObject child0 =  (JsonObject) children.get(0);
			//System.out.println(getRight(child0).toString());
			//System.out.println(getName(getWhat(getRight(child0))).toString());
			//System.out.println(findExpression(theWholeFile));
			//System.out.println(findEnteryPoint(theWholeFile));
			System.out.println(determineSanitization(theWholeFile)+" and the entery point is "+findEnteryPoint(theWholeFile));
			
		
			
			
		} else {
			System.out.println("Could not read the file!");
		}

		
	}
	
	
	
	public String findExpression(JsonObject file) throws IOException{		
		
		JsonArray children = getChildren(file);
		String expression = "";
		int size= children.size();
		int currentChild = 0 ;
		do{
			JsonObject child =  (JsonObject) children.get(currentChild);
			if(getKind(child).equals("assign")){
				
				if (getKind(getRight(child)).equals("call")){
					expression =  getName(getWhat(getRight(child)));
					
				}
			}else if(getKind(child).equals("call")){
				expression =  getName(getWhat(child));
			}
			else if(getKind(child).equals("echo")){
				expression =  getKind(child);
			}
			
			currentChild++ ;
			
		}	while(currentChild<size);		
		
		return expression;
		
	}
	public String findEnteryPoint(JsonObject file) throws IOException{
		
		JsonArray children = getChildren(file);
		String entryPoint = "";
		int size= children.size();
		int currentChild = 0 ;
		do{
			JsonObject child =  (JsonObject) children.get(currentChild);
			if(getKind(child).equals("assign")){
				
				if (getKind(getRight(child)).equals("offsetlookup")){
					entryPoint =  getName(getWhat(getRight(child)));
					
				}
			}
			else if(getKind(child).equals("echo")){
				// long code to get arguments as array , travers objects , find server or something
			}
			
			currentChild++ ;
			
		}	while(currentChild<size);		
		
		return entryPoint;
		
		
	}
	
	public String determineSanitization(JsonObject file) throws IOException{
		String exeption = findExpression( file);
		String sanitization = "";
		String injectionType = "";
		
		// SQL 
		if(exeption.equals("mysql_query") || exeption.equals("mysql_unbuffered_query")||exeption.equals("mysql_db_query")){
			sanitization = "mysql_escape_string or mysql_real_escape_string";
			injectionType = "SQL Injection";
		}else if(exeption.equals("mysqli_query") || exeption.equals("mysqli_real_query")||exeption.equals("mysqli_master_query")||exeption.equals("mysqli_multi_query")){
			sanitization = "mysqli_escape_string or mysqli_real_escape_string";
			injectionType = "SQL Injection";
		}else if(exeption.equals("mysqli_stmt_execute") || exeption.equals("mysqli_execute")){
			sanitization = "mysqli_stmt_bind_param";
			injectionType = "SQL Injection";
		}else if(exeption.equals("mysqli::query") || exeption.equals("mysqli::multi_query")|| exeption.equals("mysqli::real_query")){
			sanitization = "mysqli::escape_string or mysqli::real_escape_string";
			injectionType = "SQL Injection";
		}
		//XSS
		else if(exeption.equals("echo") || exeption.equals("print") || exeption.equals("printf") || exeption.equals("die") || exeption.equals("die")){
			sanitization = "htmlentities or htmlspecialchars or strip_tags or urlencode";
			injectionType = "Cross site scripting";
		}
				
		return "Injection type is "+injectionType+" Functions for sanitizations are "+ sanitization ;
	}
	
	
	
	

	public JsonObject readFile() throws IOException {
				
		Scanner sc = new Scanner(System.in);
		String filename = sc.nextLine();
		
		//String content = new String(Files.readAllBytes(Paths.get(filename)));
		
		String content="";
		
		try
	    {
			content = new String(Files.readAllBytes(Paths.get("C:/Users/test/workspace/ReadJson/src/slice1.json"+filename)));
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }		
		
		JsonObject jo;
        JsonElement je = new JsonParser().parse(content);
        jo = je.getAsJsonObject();
		
		
        sc.close();
		return jo;
		
	}
	private  JsonArray getChildren(JsonObject j) {

		JsonArray children = j.get("children").getAsJsonArray();

		return children;
	}
	private  JsonArray getArguments(JsonObject j) {

		JsonArray arguments = j.get("arguments").getAsJsonArray();

		return arguments;
	}

	private  Object getLeft(JsonObject j) {

		JsonObject left = j.get("left").getAsJsonObject();

		return left;
	}

	private  JsonObject getRight(JsonObject j) {

		JsonObject right = j.get("right").getAsJsonObject();

		return right;
	}

	private  JsonObject getWhat(JsonObject j) {

		JsonObject what = j.get("what").getAsJsonObject();

		return what;
	}

	private  Object getOffset(JsonObject j) {

		JsonObject offset = j.get("offset").getAsJsonObject();

		return offset;
	}

	private  Object getValueObject(JsonObject j) {

		JsonObject offset = j.get("value").getAsJsonObject();

		return offset;
	}

	private  String getValue(JsonObject j) {

		String value = j.get("value").getAsString();

		return value;
	}

	private  String getOperator(JsonObject j) {

		String operator = j.get("operator").getAsString();

		return operator;
	}

	private  String getName(JsonObject j) {

		String name = j.get("name").getAsString();

		return name;
	}

	private  String getKind(JsonObject j) {

		String kind = j.get("kind").getAsString();

		return kind;
	}

	public static void main(String[] args) throws IOException {
		JsonReader myProgram = new JsonReader();

		myProgram.start();

	}

}
