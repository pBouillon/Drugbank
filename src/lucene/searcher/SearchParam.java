package lucene.searcher;

public class SearchParam {

    public String fieldToQuery;
    public String fieldValue;
    public SearchParam(String p_fieldToQuery, String p_fieldValue){
        fieldToQuery =  p_fieldToQuery;
        fieldValue = p_fieldValue;
    }
}
