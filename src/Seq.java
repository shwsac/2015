
public class Seq {

  int value;
  Seq next;
  

  
  @Override
  public boolean equals(Object s) {
    if(this == s) {
      return true;
    }
    if((this.value == s.value)|| (this.next.equals(s.next))){
      return true;
    }else {
      return false;
    }
    
  }
  
  
}
