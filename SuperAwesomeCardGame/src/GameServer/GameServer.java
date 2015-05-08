package GameServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

import GameServer.Users.LogIn;

public class GameServer
{

   public static void main(String[] args)
   {
      GameManagement gm = null;
      try
      {
         gm = new GameManagement();
      }
      catch (RemoteException | SQLException e1)
      {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }

      // System.out.println(gm.getClass());
      // DEBUG
      System.out.println("Game server launched.");

      LogIn login = new LogIn();
      try
      {

         Registry r = LocateRegistry.createRegistry(1099);
         Naming.rebind("//localhost/auth", login);
         Naming.rebind("//localhost/game", (IGameManagement) gm);

         gm.createGame(2, "Default Game 1", false);
         gm.createGame(2, "Default Game 2", false);
      }
      catch (RemoteException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      catch (MalformedURLException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      // Start a new ChatServer on port 1444.
      ChatServer server = new ChatServer(1444);
      server.start();
      
      while (true)
      {
         try
         {
            Thread.sleep(60000);
            System.out.println("Games: " + gm.listJoinableGames().size());
         }
         catch (InterruptedException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      } // End while
   }
}
