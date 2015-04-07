
public class Seq {

  int value;
  Seq next;
  

  
  @Override
  public boolean equals(Object s) {
    if(this == s) {
      return true;
    }
    Seq sSeq = (Seq) s;
    if((this.value == sSeq.value)|| (this.next.equals(sSeq.next))){
      return true;
    }else {
      return false;
    }
    
  }
  
  
}
