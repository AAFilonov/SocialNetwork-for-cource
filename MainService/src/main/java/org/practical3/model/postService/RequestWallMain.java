
package org.practical3.model.postService;

        import java.util.ArrayList;
        import java.util.Date;
        import java.util.Map;

public class RequestWallMain {
    public String UserLogin;
    public ArrayList<PostField> PostFields;
    public Date DateAfter;
    public Date DateBefore;
    public Integer Count;
    public Integer Offset;

    public RequestWallMain(String userLogin, ArrayList<PostField> postFields, Date dateAfter, Date dateBefore, Integer count, Integer offset){
        UserLogin = userLogin;
        PostFields =postFields;
        DateAfter = dateAfter;
        DateBefore = dateBefore;
        Count = count;
        Offset = offset;
    }

    public RequestWallMain(Map<String, String[]> args) {
        UserLogin = args.get("login")[0];
        PostFields = parseFields(args);
        DateAfter = new Date(args.get("dateAfter")[0]);
        DateBefore = new Date(args.get("dateBefore")[0]);
        Count = new Integer(args.get("count")[0]);
        Offset = new Integer(args.get("offset")[0]);
    }





    private  ArrayList<PostField> parseFields(Map<String,String[]> args ){
        String fields = args.get("fields")[0];
        String[] fieldsArray = fields.split(",");
        ArrayList<PostField> finalPostFields = new ArrayList<>();


        for (String fieldString:fieldsArray) {
            finalPostFields.add(PostField.parse(fieldString));
        }
        return finalPostFields;
    }


}
