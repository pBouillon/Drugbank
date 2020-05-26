package util.tree;


import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    public String text;
    public List<TreeNode> sons;
    public TreeNode(String ptext){
        text = ptext;
        sons = new ArrayList<>();
    }
    public List<StringBuilder> reduce(){
        List<StringBuilder> res = new ArrayList<>();
        if (sons.size()==0){
            if(text!=null){
                res.add(new StringBuilder(text));
            }
        }else{
            for (TreeNode son: sons) {
                for (StringBuilder s: son.reduce()) {
                    s.append(" AND ").append(text);
                    res.add(s);
                }
            }
        }
        return res;
    }
}
