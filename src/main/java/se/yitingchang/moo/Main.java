package se.yitingchang.moo;


import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try{
            TextIO io= new SimpleWindow("Bulls and Cows");
            DatabaseManager dbManager=new DatabaseManager();
            MooController controller = new MooController(io, dbManager);
            controller.start();
        }catch (SQLException | InterruptedException e){
            e.printStackTrace();
        }
    }

}
