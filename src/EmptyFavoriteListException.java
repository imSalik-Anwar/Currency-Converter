public class EmptyFavoriteListException extends RuntimeException {
    
    public EmptyFavoriteListException(){
        super();
    }   
    public EmptyFavoriteListException(String s){
        super(s);
    }
}
